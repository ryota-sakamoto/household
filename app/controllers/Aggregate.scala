package controllers

import javax.inject._

import com.mohiva.play.silhouette.api.Silhouette
import models.ChartData
import models.service.AggregateDailyService
import play.api.http.ContentTypes
import play.api.mvc.InjectedController
import silhouette.CookieEnv
import util.Check
import org.json4s.NoTypeHints
import org.json4s.native.Serialization
import org.json4s.native.Serialization.write

@Singleton
class Aggregate @Inject()(silhouette: Silhouette[CookieEnv], aggregateDailyService: AggregateDailyService) extends InjectedController {
    implicit val formats = Serialization.formats(NoTypeHints)

    def date(day: Option[String]) = silhouette.SecuredAction { implicit request =>
        day match {
            case Some(d) if Check.isDate(d) =>
                val daily_data = aggregateDailyService.aggregate(d)
                if (daily_data.isEmpty) {
                    // TODO
                    Ok("").as(ContentTypes.JSON)
                } else {
                    Ok(write(daily_data)).as(ContentTypes.JSON)
                }
            case None => BadRequest("")
        }
    }

    def month(type_id_opt: Option[String]) = silhouette.SecuredAction { implicit request =>
        type_id_opt match {
            case Some(type_id) if Check.isNumber(type_id) => Ok("")
            case _ => Ok(ChartData.getData.toJson).as(ContentTypes.JSON)
        }
    }
}