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
    private val LoginIsNeeded = Future(Redirect(routes.Account.loginIndex()).flashing("message" -> "Login is needed"))

    def index = silhouette.UserAwareAction.async { implicit request =>
        request.identity match {
            case Some(user) =>
                // TODO
                categoryService.list(1).flatMap { list =>
                    Future(Ok(views.html.category.index(list)))
                }
            case None => LoginIsNeeded
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
            case Some(_) =>
                // TODO
                categoryService.find(1, id).flatMap {
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

    // TODO
    def list = silhouette.SecuredAction.async { implicit request =>
        categoryService.list(1).map(l => write[Seq[models.Category]](l)).map { json =>
            Ok(json).as(ContentTypes.JSON)
        }
    }
}