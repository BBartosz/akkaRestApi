package dao

import dao.CommentsDao._
import models._
import slick.driver.PostgresDriver.api._
import scala.concurrent.Future

object CommentsDao extends BaseDao{
  def findAll(userId: UserId, postId: PostId): Future[Seq[Comment]] = {
    (for {
      user <-usersTable.filter(_.id === userId)
      post <-postsTable.filter(_.id === postId)
      comment <- commentsTable.filter(comment => comment.postId === postId && comment.userId === userId)
    } yield comment).result
  }

  def findById(userId: UserId, postId: PostId, commentId: CommentId): Future[Comment] = {
    (for{
      user <- usersTable.filter(_.id === userId)
      post <- postsTable.filter(_.id === postId)
      comment <- commentsTable.filter(comment => comment.id === commentId)
    } yield comment).result.head
  }
  def create(comment: Comment): Future[CommentId] = commentsTable returning commentsTable.map(_.id) += comment
  def update(newComment: Comment, commentId: CommentId): Future[Int] = commentsTable.filter(_.id === commentId)
    .map(comment => comment.content)
    .update(newComment.content)

  def delete(commentId: CommentId): Future[Int] = commentsTable.filter(_.id === commentId).delete
}
