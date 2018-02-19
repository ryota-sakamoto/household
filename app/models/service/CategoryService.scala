package models.service

import models.Category
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

trait CategoryService extends HasDatabaseConfigProvider[JdbcProfile] {
    def register: Future[Int]
    def find(id: Int): Future[Option[Category]]
    def list: Future[List[Category]]
}