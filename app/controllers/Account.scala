package controllers

import javax.inject._

import com.mohiva.play.silhouette.api.{LoginInfo, Silhouette}
import play.api.mvc.{InjectedController, MessagesActionBuilder}

import scala.concurrent.{ExecutionContext, Future}
import play.api.i18n.I18nSupport
import silhouette.CookieEnv
import models.{Login, Register}

@Singleton
class Account @Inject()(silhouette: Silhouette[CookieEnv], implicit val ec: ExecutionContext, messagesAction: MessagesActionBuilder) extends InjectedController with I18nSupport {
    def loginIndex = silhouette.UserAwareAction { implicit request =>
        val m = request.flash.get("message")
        Ok(views.html.account.login(Login.login_form, m))
    }

    def registerIndex = silhouette.UserAwareAction { implicit request =>
        Ok(views.html.account.register(Register.form, None))
    }

    def login = Action.async { implicit request =>
        val service = silhouette.env.identityService
        val authenticator_service = silhouette.env.authenticatorService

        val login = Login.login_form.bindFromRequest.get

        val info = LoginInfo("id", login.email) // TODO

        service.retrieve(info).flatMap {
            case Some(user) =>
                for {
                    authenticator <- authenticator_service.create(user.loginInfo)
                    value <- authenticator_service.init(authenticator)
                    result <- authenticator_service.embed(value, Redirect(routes.Home.index()))
                } yield result
            case None => Future(Redirect(routes.Account.loginIndex()).flashing("message" -> "Login Failed"))
        }
    }

    def register = Action.async { implicit request =>
        val form = Register.form.bindFromRequest
        if (form.hasErrors) {
            // TODO error
            Future(Redirect(routes.Account.registerIndex()))
        } else {
            // TODO register
            val register = form.get
            Future(Ok(""))
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