package service

import cats.effect.IO
import entity.Meal
import entity.error.MealError

trait MealServiceTrait {
  def create(meal: Meal): IO[Either[MealError, Meal]]
  def getMealById(id: Option[Long]): IO[Option[Meal]]
  def update(meal: Meal): IO[Option[Meal]]
  def delete(id: Option[Long]): IO[Either[MealError, Meal]]
  def getMealsByRestaurantId(id: Option[Long]): IO[Either[MealError, List[Meal]]]
}

class MealService extends MealServiceTrait{
  override def create(meal: Meal): IO[Either[MealError, Meal]] = ???

  override def getMealById(id: Option[Long]): IO[Option[Meal]] = ???

  override def update(meal: Meal): IO[Option[Meal]] = ???

  override def delete(id: Option[Long]): IO[Either[MealError, Meal]] = ???

  override def getMealsByRestaurantId(id: Option[Long]): IO[Either[MealError, List[Meal]]] = ???
}