package controllers

import javax.inject._

import com.mohiva.play.silhouette.api.Silhouette
import models.service.UserService
import play.api.mvc.{InjectedController, MessagesActionBuilder}

import scala.concurrent.{ExecutionContext, Future}
import play.api.i18n.I18nSupport
import silhouette.CookieEnv
import models.{Login, Register, User}
import util.{ConvertError, Security}

@Singleton
class Account @Inject()(silhouette: Silhouette[CookieEnv], implicit val ec: ExecutionContext, messagesAction: MessagesActionBuilder, userService: UserService) extends InjectedController with I18nSupport {
    def loginIndex = silhouette.UserAwareAction { implicit request =>
        val m = request.flash.get("message")
        Ok(views.html.account.login(Login.login_form, m))
    }

    def registerIndex = silhouette.UserAwareAction { implicit request =>
        val m = request.flash.get("message").map {_.split(",").map { e =>
            val Array(k, v) = e.trim.split(" ")
            ConvertError.convert(k, v)
        }.toList }
        Ok(views.html.account.register(Register.form, m))
    }

    def login = Action.async { implicit request =>
//        val service = silhouette.env.identityService
        val authenticator_service = silhouette.env.authenticatorService

        val login = Login.login_form.bindFromRequest.get

        userService.retrieve(login.email, Security.sha256Hash(login.password)).flatMap {
            case Some(user) =>
                for {
                    authenticator <- authenticator_service.create(user.loginInfo)
                    value <- authenticator_service.init(authenticator)
                    result <- authenticator_service.embed(value, Redirect(routes.Home.index()))
                } yield result
            case None => Future(Redirect(routes.Account.loginIndex()).flashing("message" -> "Login Failed"))
        }
    }

    def register = Action { implicit request =>
        val form = Register.form.bindFromRequest
        if (form.hasErrors) {
            val errors = form.errors.map { e => s"${e.key} ${e.message}" }.mkString(",")
            Redirect(routes.Account.registerIndex()).flashing("message" -> errors)
        } else {
            val register = form.get
            userService.save(User(register.email, Security.sha256Hash(register.password), true))
            Redirect(routes.Account.loginIndex())
        }
    }

    def logout = silhouette.SecuredAction.async { implicit request =>
        val service = silhouette.env.authenticatorService
        for {
            authenticator <- service.create(request.identity.loginInfo)
            result <- service.discard(authenticator, Redirect(routes.Account.loginIndex()))
        } yield result
    }
}