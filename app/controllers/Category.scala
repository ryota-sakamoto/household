package controllers

import java.sql.Date
import javax.inject._

import com.mohiva.play.silhouette.api.Silhouette
import models.CategoryRegister
import models.service.CategoryService
import play.api.mvc.InjectedController
import silhouette.CookieEnv
import play.api.http.ContentTypes

import scala.concurrent.ExecutionContext.Implicits.global
import org.json4s.NoTypeHints
import org.json4s.native.Serialization
import org.json4s.native.Serialization.write
import play.api.i18n.I18nSupport

import scala.concurrent.Future

@Singleton
class Category @Inject()(silhouette: Silhouette[CookieEnv], categoryService: CategoryService) extends InjectedController with I18nSupport {
    implicit val formats = Serialization.formats(NoTypeHints)
    private val LoginIsNeeded = Future(Redirect(routes.Account.loginIndex()).flashing("message" -> "Login is needed"))

    def index = silhouette.UserAwareAction.async { implicit request =>
        val message = request.flash.get("message")
        request.identity match {
            case Some(u) =>
                categoryService.list(u.id).flatMap { list =>
                    Future(Ok(views.html.category.index(list, message)))
                }
            case None => LoginIsNeeded
        }
    }

    def registerIndex = silhouette.UserAwareAction.async { implicit request =>
        request.identity match {
            case Some(_) => Future(Ok(views.html.category.register(CategoryRegister.form)))
            case None => LoginIsNeeded
        }
    }

    def register = silhouette.SecuredAction.async { implicit request =>
        val form = CategoryRegister.form.bindFromRequest
        if (form.hasErrors) {
            Future(Ok(""))
        } else {
            val c = form.get
            val date = new Date(System.currentTimeMillis)
            val new_id = categoryService.getLastId(request.identity.id).map {
                case Some(n) => n + 1
                case None => 1
            }

            new_id.flatMap { n =>
                val category = models.Category(n, request.identity.id, c.name, c.memo, c.color, date, date, None)
                categoryService.register(category).flatMap {
                    case 0 => Future(BadRequest(""))
                    case 1 => Future(Redirect(routes.Category.index()).flashing("message" -> "register success"))
                }
            }
        }
    }

    def edit(id: Int) = silhouette.UserAwareAction.async { implicit request =>
        request.identity match {
            case Some(_) => Future(Ok(""))
            case None => LoginIsNeeded
        }
    }

    def show(id: Int) = silhouette.UserAwareAction.async { implicit request =>
        request.identity match {
            case Some(u) =>
                categoryService.find(u.id, id).flatMap {
                    case Some(category) =>
                        Future(Ok(views.html.category.show(category)))
                    case None => Future(NotFound(""))
                }
            case None => LoginIsNeeded
        }
    }

    def update(id: Int) = Action { implicit request =>
        Ok("")
    }

    def remove(id: Int) = silhouette.SecuredAction.async { implicit request =>
        for {
            n <- categoryService.remove(request.identity.id, id)
        } yield {
            n match {
                case 0 => BadRequest("")
                case 1 => Redirect(routes.Category.index()).flashing("message" -> s"$id was removed")
            }
        }
    }

    def list = silhouette.SecuredAction.async { implicit request =>
        categoryService.list(request.identity.id).map(l => write[Seq[models.Category]](l)).map { json =>
            Ok(json).as(ContentTypes.JSON)
        }
    }
}