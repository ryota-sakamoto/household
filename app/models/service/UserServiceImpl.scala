package models.service

import com.mohiva.play.silhouette.api.LoginInfo
import javax.inject.Inject
import play.api.cache.AsyncCacheApi
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.concurrent.duration._
import models.User

class UserServiceImpl @Inject()(val dbConfigProvider: DatabaseConfigProvider)(implicit val ec: ExecutionContext, cache: AsyncCacheApi) extends UserService {
    import profile.api._

    private val users = TableQuery[Users]

    override def save(user: User): Future[Int] = {
        db.run(users += user)
    }

    def retrieve(email: String, password: String): Future[Option[User]] = {
        db.run(users.filter(u => u.email === email && u.password === password).result.headOption).map {
            case Some(user) =>
                cache.set(user.info_key, user, 30.minutes)
                Some(user)
            case None => None
        }
    }

    override def retrieve(loginInfo: LoginInfo): Future[Option[User]] = {
        cache.get[User](loginInfo.providerKey).flatMap {
            case Some(user) =>
                for {
//                    _ <- cache.remove(loginInfo.providerKey)
                    _ <- cache.set(loginInfo.providerKey, user, 30.minutes)
                } yield Some(user)
            case None => Future(None)
        }
    }

    private[this] class Users(tag: Tag) extends Table[User](tag: Tag, "user") {
        def email = column[String]("email", O.Unique)
        def password = column[String]("password")
        def is_admin = column[Boolean]("is_admin")

        def * = (email, password, is_admin) <> (User.tupled, User.unapply)
    }
}