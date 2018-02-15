package models.service

import javax.inject._
import models.Category
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class CategoryServiceImpl @Inject()(val dbConfigProvider: DatabaseConfigProvider ) extends CategoryService {
    override def register: Future[Int] = ???

    // TODO
    override def list: Future[List[Category]] = {
        Future(List[Category](
            Category(1, "name1", "memo1"),
            Category(2, "name2", "memo2"),
            Category(3, "name3", "memo3")
        ))
    }
}