package models

import com.mohiva.play.silhouette.api.{Identity, LoginInfo}

case class User(id: Int, email: String) extends Identity {
    def loginInfo: LoginInfo = LoginInfo("", email)
}