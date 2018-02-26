package models.service

import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.Future

trait ScenarioService extends HasDatabaseConfigProvider[JdbcProfile] {
    def list(user_id: Int): Future[Seq[models.Scenario]]
    def find(id: Int, user_id: Int): Future[Option[models.Scenario]]
    def register(scenario: models.Scenario): Future[Int]
    def remove(id: Int, user_id: Int): Future[Int]
}