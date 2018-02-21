package models

import java.sql.Date

case class Category(id: Int, user_id: Int, name: String, memo: String, created_at: Date, updated_at: Date, deleted_at: Option[Date])