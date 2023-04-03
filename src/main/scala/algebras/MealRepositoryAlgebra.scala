package algebras

import domain.entity.{Meal, MealWithId}

trait MealRepositoryAlgebra[F[_]] {
  def create(meal: Meal): F[Meal]

  def update(meal: MealWithId): F[Option[MealWithId]]

  def get(id: Long): F[Option[MealWithId]]

  def delete(id: Long): F[Option[MealWithId]]
}
