package service

import cats.effect.IO
import entity.{RegularUser, Restaurant}
import entity.error.UserError

trait RestaurantServiceTrait {
  def create(restaurant: Restaurant): IO[Either[UserError, Restaurant]]
  def get(username: String): IO[Option[RegularUser]]
}

class RestaurantService extends RestaurantServiceTrait{
  override def create(user: Restaurant): IO[Either[UserError, Restaurant]] = ???

  override def get(username: String): IO[Option[RegularUser]] = ???
}