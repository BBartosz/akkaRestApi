package models.definitions

import models.{PostId, UserId, Post}
import slick.driver.PostgresDriver.api._

class PostsTable(tag: Tag) extends Table[Post](tag, "posts"){
  def id = column[PostId]("id", O.PrimaryKey, O.AutoInc)
  def userId = column[UserId]("user_id")
  def title = column[String]("title")
  def content = column[String]("content")
  def * = (id.?, userId, title, content) <> ((Post.apply _).tupled, Post.unapply)

  def author = foreignKey("user_fk", userId, TableQuery[UsersTable])(_.id)
}
