package repository.doobie

import algebras.OrderRepositoryAlgebra
import cats.effect.Sync
import cats.implicits.catsSyntaxOptionId
import domain.entity.Orders
import doobie._
import doobie.implicits._

private object OrderSQL {
  /* We require type StatusMeta to handle our ADT Status */

  // Meta[String].timap(fromString-toSet[Long])(fromSet[Long]toString)
  implicit val SetLongMeta: Meta[Set[Long]] = Meta[String].imap(_.split(',').toList.map(el => el.toLong).toSet)(_.mkString(","))

  def select(orderId: Long): Query0[Orders] = sql"""
    SELECT ID, USER_ID, RESTAURANT_ID, MEAL_IDS, TOTAL_PRICE
    FROM ORDERS
    WHERE ID = $orderId
  """.query[Orders]

  def insert(order: Orders): Update0 = sql"""
    INSERT INTO ORDERS (USER_ID, RESTAURANT_ID, MEAL_IDS, TOTAL_PRICE)
    VALUES (${order.userId}, ${order.restaurantId}, ${order.mealIds}, ${order.totalPrice})
  """.update

  def delete(orderId: Long): Update0 = sql"""
    DELETE FROM ORDERS
    WHERE ID = $orderId
  """.update
}

class DoobieOrderRepository[F[_]: Sync](val xa: Transactor[F])
    extends OrderRepositoryAlgebra[F] {

  override def create(order: Orders): F[Orders] = OrderSQL
    .insert(order)
    .withUniqueGeneratedKeys[Long]("ID")
    .map(id => order.copy(id = id.some))
    .transact(xa);

  override def get(orderId: Long): F[Option[Orders]] = OrderSQL.select(orderId).option.transact(xa)

}

object DoobieOrderRepository {
  def apply[F[_]: Sync](
                         xa: Transactor[F],
                       ): DoobieOrderRepository[F] =
    new DoobieOrderRepository(xa)
}
