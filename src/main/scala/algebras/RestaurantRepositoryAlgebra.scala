package algebras

import domain.entity.{Restaurant,RestaurantWithId}

trait RestaurantRepositoryAlgebra[F[_]] {
  def create(restaurant: Restaurant): F[RestaurantWithId]

  def get(restaurantId: Long): F[Option[RestaurantWithId]]

  def delete(restaurantId: Long): F[Option[RestaurantWithId]]

  def update(restaurant: RestaurantWithId): F[RestaurantWithId]
}
