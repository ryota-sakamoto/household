package models.service

import java.sql.Date
import javax.inject._
import models.Category
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.Future

class CategoryServiceImpl @Inject()(val dbConfigProvider: DatabaseConfigProvider ) extends CategoryService {
    import profile.api._
    private val categories = TableQuery[Categories]

    override def register(c: Category): Future[Int] = {
        db.run(categories += c)
    }

    override def find(user_id: Int, category_id: Int): Future[Option[Category]] = {
        db.run(categories.filter(c => c.user_id === user_id && c.category_id === category_id && c.deleted_at.isEmpty).result.headOption)
    }

    override def list(user_id: Int): Future[Seq[Category]] = {
        db.run(categories.filter(c => c.user_id === user_id && c.deleted_at.isEmpty).result)
    }

    override def remove(user_id: Int, category_id: Int): Future[Int] = {
        val date = new Date(System.currentTimeMillis)
        db.run(categories
            .filter(c => c.user_id === user_id && c.category_id === category_id)
            .map(c => (c.updated_at, c.deleted_at))
            .update(date, Some(date))
        )
    }

    private[this] class Categories(tag: Tag) extends Table[Category](tag: Tag, "category") {
        def category_id = column[Int]("category_id")
        def user_id = column[Int]("user_id")
        def name = column[String]("name")
        def memo = column[String]("memo")
        def created_at = column[Date]("created_at")
        def updated_at = column[Date]("updated_at")
        def deleted_at = column[Option[Date]]("deleted_at")

        def * = (category_id, user_id, name, memo, created_at, updated_at, deleted_at) <> (Category.tupled, Category.unapply)
    }
}