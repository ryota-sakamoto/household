package controllers

import javax.inject._

import com.mohiva.play.silhouette.api.Silhouette
import models.ChartData
import play.api.http.ContentTypes
import play.api.mvc.InjectedController
import silhouette.CookieEnv

@Singleton
class Aggregate @Inject()(silhouette: Silhouette[CookieEnv]) extends InjectedController {
    def month = silhouette.SecuredAction { implicit request =>
        Ok(ChartData.getData.toJson).as(ContentTypes.JSON)
    }
}