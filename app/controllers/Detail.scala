package controllers

import javax.inject._
import play.api.mvc.InjectedController
import com.mohiva.play.silhouette.api.Silhouette
import silhouette.CookieEnv
import util.Check

@Singleton
class Detail @Inject()(silhouette: Silhouette[CookieEnv]) extends InjectedController {
    def index = silhouette.UserAwareAction { implicit request =>
        request.identity match {
            case Some(_) => Ok(views.html.detail.index())
            case None => Redirect(routes.Account.loginIndex()).flashing("message" -> "Login is needed")
        }
    }

    def show(date: String) = TODO
}