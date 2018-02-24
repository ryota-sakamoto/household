package models

import play.api.data.Form
import play.api.data.Forms._

case class CategoryRegister(name: String, memo: String, color: String)

object CategoryRegister {
    val form = Form[CategoryRegister](
        mapping(
            "name" -> nonEmptyText,
            "memo" -> text,
            "color" -> nonEmptyText
        )(CategoryRegister.apply)(CategoryRegister.unapply)
    )
}