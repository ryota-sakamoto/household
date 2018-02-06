package models

import com.mohiva.play.silhouette.api.{Identity, LoginInfo}
import util.Security

case class User(email: String, password: String, is_admin: Boolean) extends Identity {
    val info_key: String = Security.getUUID
    def loginInfo: LoginInfo = LoginInfo("id", info_key)
}