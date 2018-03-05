package models.service

import javax.inject.Inject
import models.{DailyData, DailyItem, Item}
import play.api.db.slick._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class AggregateServiceImpl @Inject()(override protected val dbConfigProvider: DatabaseConfigProvider) extends AggregateService {
    import profile.api._

    private val items = TableQuery[Items]

    // TODO
    def aggregate(date: String): List[DailyData] = {
        List(
            DailyData("昼ごはん", 500, "#a4eff2"),
            DailyData("夜ごはん", 800, "#74b9ff"),
            DailyData("朝ごはん", 300, "#fab1a0"),
            DailyData("雑貨", 250, "#b2bec3")
        )
    }

    def range(start: String, end: String): Future[Seq[DailyItem]] = {
//        List(
//            DailyItem(20, 100, 3500, 9800, "2018-02-28"),
//            DailyItem(20, 100, 3500, 9800, "2018-03-01"),
//            DailyItem(20, 100, 3500, 9800, "2018-03-02")
//        )
        Future(Nil)
//        db.run(items.filter(_.user_id === user_id).result)
    }

    def find(user_id: Int, date: String): Future[Seq[Item]] = {
        db.run(items.filter(v => v.user_id === user_id && v.date === date).result)
    }

    private[this] class Items(tag: Tag) extends Table[Item](tag, "item") {
        def user_id = column[Int]("user_id")
        def category_id = column[Int]("category_id")
        def name = column[String]("name")
        def value = column[Int]("value")
        def date = column[String]("date")

        def * = (user_id, category_id, name, value, date) <> (Item.tupled, Item.unapply)
    }
}