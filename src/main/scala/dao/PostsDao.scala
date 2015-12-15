package dao

import models.{UserId, Post, PostId}
import slick.driver.PostgresDriver.api._
import scala.concurrent.Future

object PostsDao extends BaseDao{
  def findUserPosts(userId: UserId): Future[Seq[Post]] = {
    (for{
      user <- usersTable.filter(_.id === userId)
      posts <- postsTable.filter(_.userId === user.id)
    } yield posts).result
  }
  def findByUserIdAndId(userId: UserId, postId: PostId): Future[Post] = {
    (for{
      user <- usersTable.filter(_.id === userId)
      post <- postsTable.filter(_.id === postId)
    } yield post).result.head
  }
  def create(post: Post): Future[PostId] = postsTable returning postsTable.map(_.id) += post
  def update(newPost: Post, postId: PostId): Future[Int] = postsTable.filter(_.id === postId)
    .map(post => (post.title, post.content))
    .update((newPost.title, newPost.content))

  def delete(postId: PostId): Future[Int] = postsTable.filter(_.id === postId).delete

}
