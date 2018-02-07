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
            Category("name1"),
            Category("name2"),
            Category("name3")
        ))
    }
}