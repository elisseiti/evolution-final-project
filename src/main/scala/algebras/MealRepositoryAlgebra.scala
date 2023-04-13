package algebras

import entity.Meal

trait MealRepositoryAlgebra[F[_]] {
  def create(meal: Meal): F[Meal]

  def update(meal: Meal): F[Option[Meal]]

  def get(id: Long): F[Option[Meal]]

  def getMealsByRestaurantId(id: Long): F[List[Meal]]

  def delete(id: Long): F[Option[Meal]]
}
