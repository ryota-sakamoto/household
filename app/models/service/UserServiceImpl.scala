package models.service

import com.mohiva.play.silhouette.api.LoginInfo
import javax.inject.Inject
import models.User

import scala.concurrent.Future

class UserServiceImpl @Inject()() extends UserService {
    override def save(user: User): Option[User] = ???

    override def retrieve(loginInfo: LoginInfo): Future[Option[User]] = ???
}