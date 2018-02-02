package controllers

import javax.inject._

import com.mohiva.play.silhouette.api.Silhouette
import models.ChartData
import play.api.http.ContentTypes
import play.api.mvc.InjectedController
import silhouette.CookieEnv
import util.Check

@Singleton
class Aggregate @Inject()(silhouette: Silhouette[CookieEnv]) extends InjectedController {
    def date = TODO

    def month(type_id_opt: Option[String]) = silhouette.SecuredAction { implicit request =>
        type_id_opt match {
            case Some(type_id) if Check.isNumber(type_id) => Ok("")
            case _ => Ok(ChartData.getData.toJson).as(ContentTypes.JSON)
        }
    }
}