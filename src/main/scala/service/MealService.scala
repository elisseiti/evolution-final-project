package service

import algebras.validation.{MealValidationAlgebra, RestaurantValidationAlgebra}
import algebras.{MealRepositoryAlgebra, RestaurantRepositoryAlgebra}
import cats.Monad
import entity.Meal
import entity.error.ValidationError


trait MealServiceTrait[F[_]] {
  def create(meal: Meal): F[Either[ValidationError, Meal]]
  def getMealById(id: Option[Long]): F[Option[Meal]]
  def update(meal: Meal): F[Option[Meal]]
  def delete(id: Option[Long]): F[Either[ValidationError, Meal]]
  def getMealsByRestaurantId(id: Option[Long]): F[Either[ValidationError, List[Meal]]]
}

class MealService [F[_]: Monad](
                          mealRepository: MealRepositoryAlgebra[F],
                          restaurantRepository: RestaurantRepositoryAlgebra[F],
                          restaurantValidationAlgebra: RestaurantValidationAlgebra[F],
                          mealValidationAlgebra: MealValidationAlgebra[F])
  extends MealServiceTrait[F]{

  def updateMeal(dummmyMeal: Meal): F[Meal] = ???

  override def create(meal: Meal): F[Either[ValidationError, Meal]] = ???



  override def getMealById(id: Option[Long]): F[Option[Meal]] = ???

//    for {
//    _ <- mealValidationAlgebra.exists(id)
//    saved <- mealRepository.get(id)
//  } yield saved


  override def update(meal: Meal): F[Option[Meal]] = ???

  override def delete(id: Option[Long]): F[Either[ValidationError, Meal]] = ???

  override def getMealsByRestaurantId(id: Option[Long]): F[Either[ValidationError, List[Meal]]] = ???
}

object MealService {
  object MealService {
    def apply[F[_]: Monad](
                     mealRepository: MealRepositoryAlgebra[F],
                     restaurantRepository: RestaurantRepositoryAlgebra[F],
                     restaurantRepositoryAlgebra: RestaurantValidationAlgebra[F],
                     mealValidationAlgebra: MealValidationAlgebra[F]):
    MealService[F] =
      new MealService[F](mealRepository, restaurantRepository, restaurantRepositoryAlgebra, mealValidationAlgebra)
  }
}