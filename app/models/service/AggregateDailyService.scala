package models.service

import models.DailyData
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

trait AggregateDailyService extends HasDatabaseConfigProvider[JdbcProfile] {
    def aggregate(date: String): List[DailyData]
}