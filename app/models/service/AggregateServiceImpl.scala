package models.service

import javax.inject.Inject

import models.{DailyData, DailyItem}
import play.api.db.slick._

class AggregateServiceImpl @Inject()(override protected val dbConfigProvider: DatabaseConfigProvider) extends AggregateService {
    // TODO
    def aggregate(date: String): List[DailyData] = {
        List(
            DailyData("昼ごはん", 500, "#a4eff2"),
            DailyData("夜ごはん", 800, "#74b9ff"),
            DailyData("朝ごはん", 300, "#fab1a0"),
            DailyData("雑貨", 250, "#b2bec3")
        )
    }

    // TODO
    def range(start: String, end: String): List[DailyItem] = {
        List(
            DailyItem(20, 100, 3500, 9800, "2018-02-28"),
            DailyItem(20, 100, 3500, 9800, "2018-03-01"),
            DailyItem(20, 100, 3500, 9800, "2018-03-02")
        )
    }
}