package api

import dao.CommentsDao

import scala.concurrent.ExecutionContext.Implicits.global
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import mappings.JsonMappings
import models.{CommentId, Comment}
import akka.http.scaladsl.server.Directives._
import spray.json._

trait CommentsApi extends JsonMappings{
  val commentsApi =
    (path("users"/IntNumber/"posts"/IntNumber/"comments") & get ) {(userId, postId) =>
       complete (CommentsDao.findAll(userId, postId).map(_.toJson))
    }~
      (path("users"/IntNumber/"posts"/IntNumber/"comments"/IntNumber) & get) { (userId, postId, commentId) =>
        complete (CommentsDao.findById(userId, postId, commentId).map(_.toJson))
    }~
      (path("comments") & post) { entity(as[Comment]) { comment =>
        complete (CommentsDao.create(comment).map(_.toJson))
      }
    }~
      (path("users"/IntNumber/"posts"/IntNumber/"comments"/IntNumber) & put) { (userId, postId, commentId) => entity(as[Comment]) { comment =>
        complete (CommentsDao.update(comment, commentId).map(_.toJson))
      }
    }~
      (path("comments"/IntNumber) & delete) { commentId =>
        complete (CommentsDao.delete(commentId).map(_.toJson))
    }
}
