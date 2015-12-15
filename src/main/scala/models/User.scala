package models

case class User(id: Option[UserId], userName: String, password: String, age: Int, gender: Int)
