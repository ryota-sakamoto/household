package controllers

import javax.inject._

import com.mohiva.play.silhouette.api.Silhouette
import models.ChartData
import models.service.AggregateService
import play.api.http.ContentTypes
import play.api.mvc.InjectedController
import silhouette.CookieEnv
import util.Check
import org.json4s.NoTypeHints
import org.json4s.native.Serialization
import org.json4s.native.Serialization.write

@Singleton
class Aggregate @Inject()(silhouette: Silhouette[CookieEnv], aggregateService: AggregateService) extends InjectedController {
    implicit val formats = Serialization.formats(NoTypeHints)

    def date(day: Option[String]) = silhouette.SecuredAction { implicit request =>
        day match {
            case Some(d) if Check.isDate(d) =>
                val daily_data = aggregateService.aggregate(d)
                if (daily_data.isEmpty) {
                    NotFound(write(models.Error("Not Found"))).as(ContentTypes.JSON)
                } else {
                    Ok(write(daily_data)).as(ContentTypes.JSON)
                }
            case _ => BadRequest("")
        }
    }

    def dateRange(start: Option[String], end: Option[String]) = silhouette.SecuredAction { implicit request =>
        (start, end) match {
            case (Some(s), Some(e)) =>
                val data = aggregateService.range(s, e)
                Ok(write(data)).as(ContentTypes.JSON)
            case _ => BadRequest("")
        }
    }

    def month(type_id_opt: Option[String]) = silhouette.SecuredAction { implicit request =>
        type_id_opt match {
            case Some(type_id) if Check.isNumber(type_id) => Ok("")
            case _ => Ok(ChartData.getData.toJson).as(ContentTypes.JSON)
        }
    }
}