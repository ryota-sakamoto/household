package controllers

import javax.inject._

import com.mohiva.play.silhouette.api.Silhouette
import models.service.CategoryService
import play.api.mvc.InjectedController
import silhouette.CookieEnv
import play.api.http.ContentTypes

import scala.concurrent.ExecutionContext.Implicits.global
import org.json4s.NoTypeHints
import org.json4s.native.Serialization
import org.json4s.native.Serialization.write

import scala.concurrent.Future

@Singleton
class Category @Inject()(silhouette: Silhouette[CookieEnv], categoryService: CategoryService) extends InjectedController {
    implicit val formats = Serialization.formats(NoTypeHints)

    def index = silhouette.UserAwareAction { implicit request =>
        request.identity match {
            case Some(_) => Ok(views.html.category.index())
            case None => Redirect(routes.Account.loginIndex()).flashing("message" -> "Login is needed")
        }
    }

    def edit(id: Int) = silhouette.UserAwareAction { implicit request =>
        request.identity match {
            case Some(_) => Ok(views.html.category.index())
            case None => Redirect(routes.Account.loginIndex()).flashing("message" -> "Login is needed")
        }
    }

    def show(id: Int) = silhouette.SecuredAction.async { implicit request =>
        categoryService.find(id).flatMap {
            case Some(category) =>
                Future(Ok(write[models.Category](category)).as(ContentTypes.JSON))
            case None => Future(NotFound(""))
        }
    }

    def update(id: Int) = Action { implicit request =>
        Ok("")
    }

    def list = silhouette.SecuredAction.async { implicit request =>
        categoryService.list.map(l => write[List[models.Category]](l)).map { json =>
            Ok(json).as(ContentTypes.JSON)
        }
    }
}