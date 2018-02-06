package models

import play.api.data.Form
import play.api.data.Forms._

case class Register(email: String, password: String, confirm_password: String)

object Register {
    val form = Form[Register](
        mapping(
            "email" -> email,
            "password" -> nonEmptyText(6),
            "confirm_password" -> nonEmptyText(6)
        )(Register.apply)(Register.unapply).verifying("confirm_password invalid", d => d.password == d.confirm_password)
    )
}