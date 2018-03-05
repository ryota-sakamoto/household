package models.service

import models.{DailyData, DailyItem, Item}
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

trait AggregateService extends HasDatabaseConfigProvider[JdbcProfile] {
    def aggregate(date: String): List[DailyData]
    def range(start: String, end: String): Future[Seq[DailyItem]]
    def find(user_id: Int, date: String): Future[Seq[Item]]
}