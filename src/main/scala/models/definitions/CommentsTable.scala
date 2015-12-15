package models.definitions

import models.{Comment, CommentId}
import slick.driver.PostgresDriver.api._

class CommentsTable(tag: Tag) extends Table[Comment](tag, "comments"){
  def id = column[CommentId]("id", O.PrimaryKey, O.AutoInc)
  def userId = column[Long]("user_id")
  def postId = column[Long]("post_id")
  def content = column[String]("content")
  def * = (id.?, userId, postId, content) <> ((Comment.apply _).tupled, Comment.unapply)

  def author = foreignKey("comment_user_fk", userId, TableQuery[UsersTable])(_.id)
  def post = foreignKey("comment_post_fk", postId, TableQuery[PostsTable])(_.id)
}

