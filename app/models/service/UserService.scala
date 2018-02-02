package models.service

import com.mohiva.play.silhouette.api.services.IdentityService
import models.User
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.Future

trait UserService extends IdentityService[User] with HasDatabaseConfigProvider[JdbcProfile] {
    def retrieve(email: String, password: String): Future[Option[User]]
    def save(user: User): Unit
}