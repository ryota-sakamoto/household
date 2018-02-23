package models.service

import javax.inject.Inject

import models.DailyData
import play.api.db.slick._

class AggregateDailyServiceImpl @Inject()(override protected val dbConfigProvider: DatabaseConfigProvider) extends AggregateDailyService {
    // TODO
    def aggregate(date: String): List[DailyData] = {
        List(
            DailyData("昼ごはん", 500),
            DailyData("夜ごはん", 800),
            DailyData("朝ごはん", 300),
            DailyData("雑貨", 250)
        )
    }
}