package models.service

import com.mohiva.play.silhouette.api.LoginInfo
import javax.inject.Inject
import play.api.cache.AsyncCacheApi
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import models.User

class UserServiceImpl @Inject()(val dbConfigProvider: DatabaseConfigProvider)(implicit val ec: ExecutionContext, cache: AsyncCacheApi) extends UserService {
    import profile.api._

    private val users = TableQuery[Users]

    override def save(user: User): Unit = {
        db.run(users += user)
    }

    def retrieve(email: String, password: String): Future[Option[User]] = {
        db.run(users.filter(_.email === email).result.headOption)
    }

    override def retrieve(loginInfo: LoginInfo): Future[Option[User]] = {
        cache.get[User](loginInfo.providerKey).flatMap {
            case Some(user) => Future(Some(user))
            case None => Future {
                None
            }
        }
    }

    private[this] class Users(tag: Tag) extends Table[User](tag: Tag, "user") {
        def id = column[Int]("id", O.PrimaryKey)
        def email = column[String]("email", O.Unique)
        def is_admin = column[Boolean]("is_admin")

        def * = (id, email, is_admin) <> (User.tupled, User.unapply)
    }
}