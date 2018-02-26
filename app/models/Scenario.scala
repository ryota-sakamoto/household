package models

import java.sql.Date

case class Scenario(id: Int, name: String, description: String, status: ScenarioStatus, created_at: Date, updated_at: Date, deleted_at: Option[Date])

sealed abstract class ScenarioStatus(status_id: Int)
case object Stop extends ScenarioStatus(0)
case object Running extends ScenarioStatus(1)
case object Error extends ScenarioStatus(2)