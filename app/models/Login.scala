package models

import play.api.data.Form
import play.api.data.Forms._

case class Login(email: String, password: String)

object Login {
    val login_form = Form[Login](
        mapping(
            "email" -> email,
            "password" -> nonEmptyText
        )(Login.apply)(Login.unapply)
    )
}