import dao.BaseDao
import models.User

import akka.event.{ NoLogging, LoggingAdapter }
import akka.http.scaladsl.testkit.ScalatestRouteTest
import utils.MigrationConfig
import org.scalatest._
import scala.concurrent.Await
import scala.concurrent.duration._

trait BaseServiceSpec extends WordSpec with Matchers with ScalatestRouteTest with Routes with MigrationConfig with BaseDao{
  protected val log: LoggingAdapter = NoLogging

  import driver.api._

  val testUsers = Seq(
    User(Some(1), "userName1", "password1", 21, 1),
    User(Some(2), "userName2", "password2", 22, 2),
    User(Some(3), "userName3", "password3", 24, 1)
  )

  val testPosts = Seq(
    models.Post(Some(1), 1, "title1", "content1"),
    models.Post(Some(2), 2, "title2", "content2"),
    models.Post(Some(3), 3, "title3", "content3")
  )

  val testComments = Seq(
    models.Comment(Some(1), 1, 1, "content1"),
    models.Comment(Some(2), 2, 2, "content2"),
    models.Comment(Some(3), 3, 3, "content3")
  )

  reloadSchema()
  Await.result(usersTable ++= testUsers, 10.seconds)
  Await.result(postsTable ++= testPosts, 10.seconds)
  Await.result(commentsTable ++= testComments, 10.seconds)
}
