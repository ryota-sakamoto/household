package controllers

import javax.inject._
import com.mohiva.play.silhouette.api.Silhouette
import play.api.mvc.InjectedController
import silhouette.CookieEnv

@Singleton
class Admin @Inject()(silhouette: Silhouette[CookieEnv]) extends InjectedController {
    def index = silhouette.UserAwareAction { implicit request =>
        request.identity match {
            case Some(user) if user.is_admin => InternalServerError
            case None => NotFound
        }
    }
}