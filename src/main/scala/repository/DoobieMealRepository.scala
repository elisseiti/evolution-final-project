package repository

import algebras.MealRepositoryAlgebra
import cats.data.OptionT
import cats.effect.Sync
import cats.implicits.{catsSyntaxOptionId, toFunctorOps}
import doobie._
import doobie.implicits._
import entity.Meal
private object MealSQL {
  /* We require type StatusMeta to handle our ADT Status */

  /* This is used to marshal our sets of strings */
  implicit val SetStringMeta: Meta[Set[String]] =
    Meta[String].imap(_.split(',').toSet)(_.mkString(","))

  def insert(meal: Meal): Update0 = sql"""
    INSERT INTO MEAL (NAME, DESCRIPTION, PRICE, RESTAURANT_ID)
    VALUES (${meal.name}, ${meal.description}, ${meal.price}, ${meal.restaurantId})
  """.update

  def update(meal: Meal, id: Long): Update0 = sql"""
    UPDATE MEAL
    SET NAME = ${meal.name}, DESCRIPTION = ${meal.description}, PRICE = ${meal.price}, RESTAURANT_ID = ${meal.restaurantId}
    WHERE id = ${id}
  """.update

  def select(id: Long): Query0[Meal] = sql"""
    SELECT ID,NAME, DESCRIPTION, PRICE, RESTAURANT_ID
    FROM MEAL
    WHERE ID = $id
  """.query

  def delete(id: Long): Update0 = sql"""
    DELETE FROM MEAL WHERE ID = $id
  """.update

}

class DoobieMealRepository[F[_]: Sync](xa: Transactor[F]) extends MealRepositoryAlgebra[F] {
  override def create(meal: Meal): F[Meal] = MealSQL
    .insert(meal)
    .withUniqueGeneratedKeys[Long]("ID")
    .map(id => meal.copy(id = id.some))
    .transact(xa);

  override def update(meal: Meal): F[Option[Meal]] = OptionT
    .fromOption[ConnectionIO](meal.id)
    .semiflatMap(id => MealSQL.update(meal,id).run.as(meal))
    .value
    .transact(xa)

  override def get(id: Long): F[Option[Meal]] = MealSQL.select(id).option.transact(xa)

  override def delete(id: Long): F[Option[Meal]] = OptionT(get(id))
    .semiflatMap(order => MealSQL.delete(id).run.transact(xa).as(order))
    .value
}

object DoobieMealRepository {
  def apply[F[_]: Sync](xa: Transactor[F]): DoobieMealRepository[F] =
    new DoobieMealRepository(xa)
}
