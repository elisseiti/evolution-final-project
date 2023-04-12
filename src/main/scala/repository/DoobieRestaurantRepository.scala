package repository

import algebras.RestaurantRepositoryAlgebra
import cats.data.OptionT
import cats.effect.Sync
import cats.implicits.{catsSyntaxOptionId, toFunctorOps}
import doobie._
import doobie.implicits._
import entity.Restaurant
private object RestaurantSQL {
  /* We require type StatusMeta to handle our ADT Status */

  /* This is used to marshal our sets of strings */
  implicit val SetStringMeta: Meta[Set[String]] =
    Meta[String].imap(_.split(',').toSet)(_.mkString(","))

  def insert(restaurant: Restaurant): Update0 = sql"""
    INSERT INTO RESTAURANT (NAME, DESCRIPTION, OWNER_ID)
    VALUES (${restaurant.name}, ${restaurant.description}, ${restaurant.ownerId})
  """.update

  def update(restaurant: Restaurant, id: Long): Update0 = sql"""
    UPDATE RESTAURANT
    SET NAME = ${restaurant.name}, DESCRIPTION = ${restaurant.description}, OWNER_ID = ${restaurant.ownerId}
    WHERE id = ${id}
  """.update

  def select(id: Long): Query0[Restaurant] = sql"""
    SELECT ID, NAME, DESCRIPTION, OWNER_ID
    FROM RESTAURANT
    WHERE ID = $id
  """.query

  def delete(id: Long): Update0 = sql"""
    DELETE FROM RESTAURANT WHERE ID = $id
  """.update

}

class DoobieRestaurantRepository[F[_]: Sync](xa: Transactor[F]) extends RestaurantRepositoryAlgebra[F] {
  override def create(restaurant: Restaurant): F[Restaurant] = RestaurantSQL
    .insert(restaurant)
    .withUniqueGeneratedKeys[Long]("ID")
    .map(id => restaurant.copy(id = id.some))
    .transact(xa)

  override def get(restaurantId: Long): F[Option[Restaurant]] = RestaurantSQL.select(restaurantId).option.transact(xa)

  override def delete(restaurantId: Long): F[Option[Restaurant]] = OptionT(get(restaurantId))
    .semiflatMap(order => RestaurantSQL.delete(restaurantId).run.transact(xa).as(order))
    .value

  override def update(restaurant: Restaurant): F[Option[Restaurant]] = OptionT
    .fromOption[ConnectionIO](restaurant.id)
    .semiflatMap(id => RestaurantSQL.update(restaurant,id).run.as(restaurant))
    .value
    .transact(xa)

}


object DoobieRestaurantRepository {
  def apply[F[_]: Sync](xa: Transactor[F]): DoobieRestaurantRepository[F] =
    new DoobieRestaurantRepository(xa)
}
