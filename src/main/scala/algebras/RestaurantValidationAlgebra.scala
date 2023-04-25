package algebras

import cats.data.EitherT
import entity.Restaurant
import entity.error.Errors.{RestaurantAlreadyExistsError, RestaurantDoesNotExist}

trait RestaurantValidationAlgebra[F[_]] {
  /* Fails with a PetAlreadyExistsError */
  /* Fails with a RestaurantNotFound if the pet id does not exist or if it is none */
  def exists(petId: Option[Long]): EitherT[F, RestaurantDoesNotExist.type, Unit]
  def doesNotExist(restaurant: Restaurant): EitherT[F, RestaurantAlreadyExistsError.type, Unit]
}
