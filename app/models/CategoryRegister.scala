package models

import play.api.data.Form
import play.api.data.Forms._

case class CategoryRegister(name: String, memo: String)

object CategoryRegister {
    val form = Form[CategoryRegister](
        mapping(
            "name" -> nonEmptyText,
            "memo" -> text
        )(CategoryRegister.apply)(CategoryRegister.unapply)
    )
}