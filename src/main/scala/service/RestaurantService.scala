package service

import algebras.RestaurantRepositoryAlgebra
import algebras.validation.RestaurantValidationAlgebra
import cats.Monad
import entity.{RegularUser, Restaurant}

trait RestaurantServiceTrait[F[_]] {
  def create(restaurant: Restaurant): F[Either[Error, Restaurant]]
  def get(username: String): F[Option[RegularUser]]
}

class RestaurantService[F[_]: Monad](restaurantRepository: RestaurantRepositoryAlgebra[F],
                                      restaurantValidationAlgebra: RestaurantValidationAlgebra[F]
                                     )
  extends RestaurantServiceTrait[F]{
  override def create(user: Restaurant): F[Either[Error, Restaurant]] = ???

  override def get(username: String): F[Option[RegularUser]] = ???
}