import akka.http.scaladsl.model.{StatusCode, MediaTypes, HttpEntity}
import dao.CommentsDao
import models.{Comment, Post}
import org.scalatest.concurrent.ScalaFutures
import spray.json._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

class CommentsApiSpec extends BaseServiceSpec with ScalaFutures{
  "Comments api" should {
    "retrieve comments list" in {
      Get("/users/1/posts/1/comments") ~> commentsApi ~> check {
        responseAs[JsArray] should be(List(testComments.head).toJson)
      }
    }
    "retrieve comment by id" in {
      Get("/users/1/posts/1/comments/1") ~> commentsApi ~> check {
        responseAs[JsObject] should be(testComments.head.toJson)
      }
    }
    "create comment properly" in {
      val newContent = "newContent"
      val requestEntity = HttpEntity(MediaTypes.`application/json`,
        JsObject(
          "postId" -> JsNumber(testPosts.head.id.get),
          "userId" -> JsNumber(testUsers.head.id.get),
          "content" -> JsString(newContent)
        ).toString())
      Post("/comments", requestEntity) ~> commentsApi ~> check {
        response.status should be(StatusCode.int2StatusCode(200))
        Get("/users/1/posts/1/comments") ~> commentsApi ~> check {
          responseAs[Seq[Comment]] should have length 2
        }
      }
    }
    "update comment by id" in {
      val newContent = "UpdatedContent"
      val requestEntity = HttpEntity(MediaTypes.`application/json`,
        JsObject(
          "postId" -> JsNumber(testPosts.head.id.get),
          "userId" -> JsNumber(testUsers.head.id.get),
          "content" -> JsString(newContent)
        ).toString())
      Put("/users/1/posts/1/comments/1", requestEntity) ~> commentsApi ~> check {
        response.status should be(StatusCode.int2StatusCode(200))
        whenReady(CommentsDao.findById(1,1, 1)) { result =>
          result.content should be(newContent)
        }
      }
    }
    "delete comment by id" in {
      Delete("/comments/1") ~> commentsApi ~> check {
        response.status should be(StatusCode.int2StatusCode(200))
        Get("/users/1/posts/1/comments") ~> commentsApi ~> check {
          responseAs[Seq[Comment]] should have length 1
        }
      }
    }
  }
}
