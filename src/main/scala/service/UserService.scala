package service

import cats.effect.IO
import entity.RegularUser

trait UserServiceTrait {
  def create(user: RegularUser): IO[Either[Error, RegularUser]]
  def get(username: String): IO[Option[RegularUser]]
}

class UserService extends UserServiceTrait{
  override def create(user: RegularUser): IO[Either[Error, RegularUser]] = ???

  override def get(username: String): IO[Option[RegularUser]] = ???
}