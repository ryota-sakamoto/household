package controllers

import javax.inject._

import play.api.mvc.InjectedController
import com.mohiva.play.silhouette.api.Silhouette
import models.service.AggregateService
import silhouette.CookieEnv
import util.Check

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class Detail @Inject()(silhouette: Silhouette[CookieEnv], aggregateService: AggregateService) extends InjectedController {
    def index = silhouette.UserAwareAction { implicit request =>
        request.identity match {
            case Some(_) => Ok(views.html.detail.index())
            case None => Redirect(routes.Account.loginIndex()).flashing("message" -> "Login is needed")
        }
    }

    def show(date: String) = silhouette.UserAwareAction.async { implicit request =>
        request.identity match {
            case Some(u) if Check.isDate(date)  =>
                aggregateService.find(u.id, date).map {l =>
                    Ok(views.html.detail.show(date, l))
                }
            case _ => Future(Redirect(routes.Account.loginIndex()).flashing("message" -> "Login is needed"))
        }
    }
}