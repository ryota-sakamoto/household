package models

import java.sql.Date

case class Scenario(id: Int, name: String, description: String, status: ScenarioStatus.Status, created_at: Date, updated_at: Date, deleted_at: Option[Date])

object ScenarioStatus {
    sealed abstract class Status(status_id: Int)
    case object Stop extends Status(0)
    case object Running extends Status(1)
    case object Error extends Status(2)
}