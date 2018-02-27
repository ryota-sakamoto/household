package models.service

import java.sql.Date
import javax.inject._

import models.{Scenario, ScenarioStatus}
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class ScenarioServiceImpl @Inject()(val dbConfigProvider: DatabaseConfigProvider) extends ScenarioService {
    // TODO
    override def list(user_id: Int): Future[Seq[Scenario]] = {
        Future(Seq(
            Scenario(1, "name1", "description1", ScenarioStatus.Stop, new Date(System.currentTimeMillis), new Date(System.currentTimeMillis), None),
            Scenario(2, "name2", "description2", ScenarioStatus.Running, new Date(System.currentTimeMillis), new Date(System.currentTimeMillis), None),
            Scenario(3, "name3", "description3", ScenarioStatus.Error, new Date(System.currentTimeMillis), new Date(System.currentTimeMillis), None)
        ))
    }

    override def find(id: Int, user_id: Int): Future[Option[Scenario]] = ???

    override def register(scenario: Scenario): Future[Int] = ???

    override def remove(id: Int, user_id: Int): Future[Int] = ???
}