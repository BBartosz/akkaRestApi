package models

case class Post(id: Option[PostId], userId: UserId, title: String, content: String)
