package controllers

import javax.inject._

import com.mohiva.play.silhouette.api.Silhouette
import models.service.ScenarioService
import silhouette.CookieEnv
import play.api.mvc.InjectedController
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class Scenario @Inject()(silhouette: Silhouette[CookieEnv], scenarioService: ScenarioService) extends InjectedController {
    def index = silhouette.UserAwareAction.async { implicit request =>
        request.identity match {
            case Some(u) =>
                scenarioService.list(u.id).map { l =>
                    Ok(views.html.scenario.index(l))
                }
            case None => Future(Redirect(routes.Account.loginIndex()).flashing("message" -> "Login is needed"))
        }
    }

    def registerIndex = TODO

    def register = TODO

    def edit(id: Int) = TODO

    def update(id: Int) = TODO

    def remove(id: Int) = TODO
}