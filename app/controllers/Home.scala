package controllers

import javax.inject._
import com.mohiva.play.silhouette.api.Silhouette
import play.api.mvc.InjectedController

import silhouette.CookieEnv

@Singleton
class Home @Inject()(silhouette: Silhouette[CookieEnv]) extends InjectedController {
    def index() = silhouette.UserAwareAction  { implicit request =>
        request.identity match {
            case Some(_) => Ok(views.html.home.index())
            case None => Ok(views.html.template.household())
        }
    }
}
