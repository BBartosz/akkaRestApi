package dao

import models.definitions.{CommentsTable, PostsTable, UsersTable}
import slick.dbio.{Effect, NoStream}
import slick.lifted.TableQuery
import slick.profile.{FixedSqlAction, SqlAction, FixedSqlStreamingAction}
import utils.DatabaseConfig
import scala.concurrent.Future

trait BaseDao extends DatabaseConfig {
  val usersTable = TableQuery[UsersTable]
  val postsTable = TableQuery[PostsTable]
  val commentsTable = TableQuery[CommentsTable]

  protected implicit def executeFromDb[A](action: SqlAction[A, NoStream, _ <: slick.dbio.Effect]): Future[A] = {
    db.run(action)
  }
  protected implicit def executeReadStreamFromDb[A](action: FixedSqlStreamingAction[Seq[A], A, _ <: slick.dbio.Effect]): Future[Seq[A]] = {
    db.run(action)
  }
}
