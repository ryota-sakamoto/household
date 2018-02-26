package controllers

import javax.inject._
import com.mohiva.play.silhouette.api.Silhouette
import silhouette.CookieEnv
import play.api.mvc.InjectedController

@Singleton
class Scenario @Inject()(silhouette: Silhouette[CookieEnv]) extends InjectedController {
    def index = silhouette.UserAwareAction { implicit request =>
        request.identity match {
            case Some(_) => Ok(views.html.scenario.index())
            case None => Redirect(routes.Account.loginIndex()).flashing("message" -> "Login is needed")
        }
    }
}