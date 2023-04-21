package service

import cats.effect.IO
import entity.RegularUser
import entity.error.UserError

trait UserServiceT {
  def create(user: RegularUser): IO[Either[UserError, RegularUser]]
  def get(username: String): IO[Option[RegularUser]]
}

class UserService extends UserServiceT{
  override def create(user: RegularUser): IO[Either[UserError, RegularUser]] = ???

  override def get(username: String): IO[Option[RegularUser]] = ???
}