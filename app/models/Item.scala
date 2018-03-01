package models

import java.sql.Date

case class Item(id: Int, user_id: Int, category_id: Int, name: String, value: Int, date: Date)