package models.service

import models.{DailyData, DailyItem}
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

trait AggregateService extends HasDatabaseConfigProvider[JdbcProfile] {
    def aggregate(date: String): List[DailyData]
    def range(start: String, end: String): List[DailyItem]
}