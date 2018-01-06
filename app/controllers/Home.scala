package controllers

import javax.inject._
import com.mohiva.play.silhouette.api.Silhouette
import play.api.mvc.InjectedController

import silhouette.CookieEnv

@Singleton
class Home @Inject()(silhouette: Silhouette[CookieEnv]) extends InjectedController {
    def index() = silhouette.SecuredAction  { implicit request =>
        Ok(views.html.home.index("Hello World"))
    }
}
