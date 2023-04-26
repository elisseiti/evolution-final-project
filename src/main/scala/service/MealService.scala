package service

import algebras.validation.{MealValidationAlgebra, RestaurantValidationAlgebra}
import algebras.{MealRepositoryAlgebra, RestaurantRepositoryAlgebra}
import cats.Monad
import entity.Meal
import entity.error.Errors


trait MealServiceTrait[F[_]] {
  def create(meal: Meal): F[Either[Errors, Meal]]
  def getMealById(id: Option[Long]): F[Option[Meal]]
  def update(meal: Meal): F[Option[Meal]]
  def delete(id: Option[Long]): F[Either[Errors, Meal]]
  def getMealsByRestaurantId(id: Option[Long]): F[Either[Errors, List[Meal]]]
}

class MealService [F[_]: Monad](
                          mealRepository: MealRepositoryAlgebra[F],
                          restaurantRepository: RestaurantRepositoryAlgebra[F],
                          restaurantValidationAlgebra: RestaurantValidationAlgebra[F],
                          mealValidationAlgebra: MealValidationAlgebra[F])
  extends MealServiceTrait[F]{


//  override def create(meal: Meal): F[Either[Errors,[Meal]]] = {
//    if (restaurantRepository.get(meal.restaurantId)) Errors.MealAlreadyExists.asLeft.pure[IO]
//    else mealRepository.create(meal).asRight.pure[IO]
//
//    if (meal.restaurantId == 1) Errors.MealAlreadyExists.asLeft.pure[IO]
//    else mealRepository.create(meal).asRight.pure[IO]
//  }
//
//  override def create(meal: Meal): IO[Either[Errors, Meal]] =
//    for {
//      _ <- restaurantValidationAlgebra.exists(meal.restaurantId)
//      saved <- EitherT.liftF(repository.create(pet))
//    } yield saved


  def updateMeal(dummmyMeal: Meal): F[Meal] = ???

  override def create(meal: Meal): F[Either[Errors, Meal]] = ???

  //  for {
//    _ <- restaurantValidationAlgebra.doesNotExist(Some(meal.restaurantId))
//    saved <- Right(mealRepository.create(meal))
//  } yield saved
//    val exists: F[Boolean] =
//      restaurantRepository.get(meal.restaurantId).map {
//          case Some(value) => true
//          case None => false
//      }

//    val updatedMeal: F[Meal] =
//      getMeal(id = "42").flatMap { meal =>
//        updateMeal(meal)
//      }

//    val updatedMealFor: F[Meal] =
//      for {
//        meal <- getMeal(id = "42")
//        updatedMeal <- updateMeal(meal)
//      } yield updatedMeal


  override def getMealById(id: Option[Long]): F[Option[Meal]] = ???

//    for {
//    _ <- mealValidationAlgebra.exists(id)
//    saved <- mealRepository.get(id)
//  } yield saved


  override def update(meal: Meal): F[Option[Meal]] = ???

  override def delete(id: Option[Long]): F[Either[Errors, Meal]] = ???

  override def getMealsByRestaurantId(id: Option[Long]): F[Either[Errors, List[Meal]]] = ???
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