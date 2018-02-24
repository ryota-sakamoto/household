package models.service

import models.Category
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

trait CategoryService extends HasDatabaseConfigProvider[JdbcProfile] {
    def register(c: Category): Future[Int]
    def find(user_id: Int, category_id: Int): Future[Option[Category]]
    def list(user_id: Int): Future[Seq[Category]]
    def getLastId(user_id: Int): Future[Option[Int]]
    def remove(user_id: Int, category_id: Int): Future[Int]
}