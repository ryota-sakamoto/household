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

@Singleton
class Category @Inject()(silhouette: Silhouette[CookieEnv], categoryService: CategoryService) extends InjectedController {
    def list = silhouette.SecuredAction.async { implicit request =>
        implicit val formats = Serialization.formats(NoTypeHints)

        categoryService.list.map(l => write[List[models.Category]](l)).map { json =>
            Ok(json).as(ContentTypes.JSON)
        }
    }
}