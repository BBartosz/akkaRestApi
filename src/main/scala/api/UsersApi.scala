package api

import dao.UsersDao

import scala.concurrent.ExecutionContext.Implicits.global
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import mappings.JsonMappings
import models.User
import akka.http.scaladsl.server.Directives._
import spray.json._

trait UsersApi extends JsonMappings{
  val usersApi =
    (path("users") & get ) {
       complete (UsersDao.findAll.map(_.toJson))
    }~
    (path("users"/IntNumber) & get) { id =>
        complete (UsersDao.findById(id).map(_.toJson))
    }~
    (path("users") & post) { entity(as[User]) { user =>
        complete (UsersDao.create(user).map(_.toJson))
      }
    }~
    (path("users"/IntNumber) & put) { id => entity(as[User]) { user =>
        complete (UsersDao.update(user, id).map(_.toJson))
      }
    }~
    (path("users"/IntNumber) & delete) { userId =>
      complete (UsersDao.delete(userId).map(_.toJson))
    }
}
