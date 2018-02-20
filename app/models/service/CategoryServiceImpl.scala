package models.service

import javax.inject._

import models.Category
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.Future

class CategoryServiceImpl @Inject()(val dbConfigProvider: DatabaseConfigProvider ) extends CategoryService {
    import profile.api._
    private val categories = TableQuery[Categories]

    override def register: Future[Int] = ???

    override def find(user_id: Int, category_id: Int): Future[Option[Category]] = {
        db.run(categories.filter(c => c.user_id === user_id && c.category_id === category_id).result.headOption)
    }

    override def list(user_id: Int): Future[Seq[Category]] = {
        db.run(categories.filter(_.user_id === user_id).result)
    }

    private[this] class Categories(tag: Tag) extends Table[Category](tag: Tag, "category") {
        def category_id = column[Int]("category_id")
        def user_id = column[Int]("user_id")
        def name = column[String]("name")
        def memo = column[String]("memo")

        def * = (category_id, name, memo) <> (Category.tupled, Category.unapply)
    }
}