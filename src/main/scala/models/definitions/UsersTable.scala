package models.definitions

import models.{UserId, User}
import slick.driver.PostgresDriver.api._

class UsersTable(tag: Tag) extends Table[User](tag, "users"){
  def id = column[UserId]("id", O.PrimaryKey, O.AutoInc)
  def username = column[String]("username")
  def password = column[String]("password")
  def age = column[Int]("age")
  def gender = column[Int]("gender")
  def * = (id.?, username, password, age, gender) <> ((User.apply _).tupled, User.unapply)
}

