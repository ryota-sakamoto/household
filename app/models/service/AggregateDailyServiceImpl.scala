package models.service

import javax.inject.Inject

import models.DailyData
import play.api.db.slick._

class AggregateDailyServiceImpl @Inject()(override protected val dbConfigProvider: DatabaseConfigProvider) extends AggregateDailyService {
    // TODO
    def aggregate(date: String): List[DailyData] = {
        List(
            DailyData("昼ごはん", 500, "#a4eff2"),
            DailyData("夜ごはん", 800, "#74b9ff"),
            DailyData("朝ごはん", 300, "#fab1a0"),
            DailyData("雑貨", 250, "#b2bec3")
        )
    }
}