package models

case class Comment(id: Option[CommentId], userId: UserId, postId: PostId, content: String)
