package models

case class Item(user_id: Int, category_id: Int, name: String, value: Int, date: String)
case class DailyItem(count: Int, min: Int, max: Int, sum: Int, date: String)