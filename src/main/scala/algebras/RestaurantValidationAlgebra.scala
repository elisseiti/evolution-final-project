package algebras

import entity.error.Errors
import entity.error.Errors.RestaurantDoesNotExist

trait RestaurantValidationAlgebra[F[_]] {
  /* Fails with a PetAlreadyExistsError */
  /* Fails with a RestaurantNotFound if the pet id does not exist or if it is none */
  def exists(restaurantId: Option[Long]): F[Either[ RestaurantDoesNotExist.type, Unit]]
  def doesNotExist(restaurantId: Option[Long]): F[Either[Errors.RestaurantAlreadyExistsError.type, Unit]]
}
