package service

import cats.effect.IO
import cats.implicits.{catsSyntaxApplicativeId, catsSyntaxEitherId}
import entity.Meal
import entity.error.MealError
import repository.DoobieMealRepository

trait MealServiceTrait {
  def create(meal: Meal): IO[Either[MealError, Meal]]
  def getMealById(id: Option[Long]): IO[Option[Meal]]
  def update(meal: Meal): IO[Option[Meal]]
  def delete(id: Option[Long]): IO[Either[MealError, Meal]]
  def getMealsByRestaurantId(id: Option[Long]): IO[Either[MealError, List[Meal]]]
}

class PetService[F[_]](
                        repository: DoobieMealRepository[F],
                      ) extends MealServiceTrait{


  override def create(meal: Meal): IO[Either[MealError, Meal]] = {
    MealError.MealAlreadyExists.asLeft.pure
    else repository.create(meal).pure[IO]
  }
  override def getMealById(id: Option[Long]): IO[Option[Meal]] = ???

  override def update(meal: Meal): IO[Option[Meal]] = ???

  override def delete(id: Option[Long]): IO[Either[MealError, Meal]] = ???

  override def getMealsByRestaurantId(id: Option[Long]): IO[Either[MealError, List[Meal]]] = ???
}