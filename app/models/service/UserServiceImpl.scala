package models.service

import com.mohiva.play.silhouette.api.LoginInfo
import javax.inject.Inject

import com.mohiva.play.silhouette.impl.User
import play.api.cache.AsyncCacheApi

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

class UserServiceImpl @Inject()(implicit val ec: ExecutionContext, cache: AsyncCacheApi) extends UserService {
    override def save(user: User): Option[User] = ???

    override def retrieve(email: String, password: String): Option[User] = {
        Some(User(
            loginInfo = LoginInfo("id", email),
            firstName = None,
            lastName = None,
            fullName = None,
            email = None,
            avatarURL = None
        )) // TODO
    }

    override def retrieve(loginInfo: LoginInfo): Future[Option[User]] = {
        cache.get[User](loginInfo.providerKey).flatMap {
            case Some(user) => Future(Some(user))
            case None => Future {
                val user_opt: Option[User] = Some(User(
                    loginInfo = loginInfo,
                    firstName = None,
                    lastName = None,
                    fullName = None,
                    email = None,
                    avatarURL = None
                )) // TODO

                user_opt match {
                    case Some(user) => cache.set(loginInfo.providerID, user)
                    case None =>
                }

                user_opt
            }
        }
    }
}