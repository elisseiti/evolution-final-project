package algebras

import entity.Restaurant

trait RestaurantRepositoryAlgebra[F[_]] {
  def create(restaurant: Restaurant): F[Restaurant]

  def get(restaurantId: Long): F[Option[Restaurant]]

  def delete(restaurantId: Long): F[Option[Restaurant]]

  def update(restaurant: Restaurant): F[Option[Restaurant]]

  def getRestaurantsByOwnerId(id: Long): F[List[Restaurant]]

  def getRestaurants(): F[List[Restaurant]]
}
