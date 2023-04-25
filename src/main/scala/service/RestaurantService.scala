package service

import cats.effect.IO
import entity.{RegularUser, Restaurant}

trait RestaurantServiceTrait {
  def create(restaurant: Restaurant): IO[Either[Error, Restaurant]]
  def get(username: String): IO[Option[RegularUser]]
}

class RestaurantService extends RestaurantServiceTrait{
  override def create(user: Restaurant): IO[Either[Error, Restaurant]] = ???

  override def get(username: String): IO[Option[RegularUser]] = ???
}