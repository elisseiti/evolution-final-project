package algebras.validation

import entity.error.{MealAlreadyExistsError, MealDoesNotExist}


trait MealValidationAlgebra[F[_]] {
  /* Fails with a PetAlreadyExistsError */
  /* Fails with a RestaurantNotFound if the pet id does not exist or if it is none */
  def exists(mealId: Option[Long]): F[Either[MealDoesNotExist.type , Unit]]

  def doesNotExist(mealId: Option[Long]): F[Either[MealAlreadyExistsError, Unit]]
}

