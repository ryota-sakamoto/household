package models

case class ResultJson[T](success: Boolean, result: Option[T], message: Option[String] = None)