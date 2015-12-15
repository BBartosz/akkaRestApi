import akka.http.scaladsl.model.{StatusCode, MediaTypes, HttpEntity}
import dao.PostsDao
import models.{Post, User}
import org.scalatest.concurrent.ScalaFutures
import spray.json._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

class PostsApiSpec extends BaseServiceSpec with ScalaFutures{
  "Users posts api" should {
    "retrieve users posts list" in {
      Get("/users/1/posts") ~> postsApi ~> check {
        responseAs[JsArray] should be(List(testPosts.head).toJson)
      }
    }
    "retrieve post by id" in {
      Get("/users/1/posts/1") ~> postsApi ~> check {
        responseAs[JsObject] should be(testPosts.head.toJson)
      }
    }
    "create post properly" in {
      val newTitle = "newTitle"
      val newContent = "newContent"
      val requestEntity = HttpEntity(MediaTypes.`application/json`,
        JsObject(
          "title" -> JsString(newTitle),
          "userId" -> JsNumber(testUsers.head.id.get),
          "content" -> JsString(newContent)
        ).toString())
      Post("/users/1/posts", requestEntity) ~> postsApi ~> check {
        response.status should be(StatusCode.int2StatusCode(200))
        Get("/users/1/posts") ~> postsApi ~> check {
          responseAs[Seq[Post]] should have length 2
        }
      }
    }
    "update post by id" in {
      val newTitle = "UpdatedTitle"
      val requestEntity = HttpEntity(MediaTypes.`application/json`,
        JsObject(
          "title" -> JsString(newTitle),
          "userId" -> JsNumber(testUsers.head.id.get),
          "content" -> JsString(testPosts.head.content)
        ).toString())
      Put("/users/1/posts/1", requestEntity) ~> postsApi ~> check {
        response.status should be(StatusCode.int2StatusCode(200))
        whenReady(PostsDao.findByUserIdAndId(1,1)) { result =>
          result.title should be(newTitle)
        }
      }
    }
    "delete post by id" in {
      Delete("/users/1/posts/1") ~> postsApi ~> check {
        response.status should be(StatusCode.int2StatusCode(200))
        Get("/users/1/posts") ~> postsApi ~> check {
          responseAs[Seq[Post]] should have length 1
        }
      }
    }
  }
}
