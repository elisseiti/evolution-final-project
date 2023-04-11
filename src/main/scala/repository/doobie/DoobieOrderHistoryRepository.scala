package repository.doobie

import algebras.OrderHistoryRepositoryAlgebra
import cats.data.OptionT
import cats.effect._
import cats.implicits.{catsSyntaxOptionId, toFunctorOps}
import domain.entity.OrderStatus.OrderStatus
import domain.entity.{OrderHistory, OrderStatus}
import doobie._
import doobie.implicits._

import doobie.postgres.implicits._

private object OrderHistorySql {
  implicit val StatusMeta: Meta[OrderStatus] =
    Meta[String].imap(OrderStatus.withName)(_.toString)

  def insert(orderHistory: OrderHistory): Update0 = sql"""
      INSERT INTO ORDER_HISTORY (ORDER_ID, STATUS, ORDERED_TIME)
    VALUES (${orderHistory.orderId}, ${orderHistory.status}, ${orderHistory.orderedTime})
  """.update

  def update(orderHistory: OrderHistory, id: Long): Update0 = sql"""
    UPDATE ORDER_HISTORY
    SET ORDER_ID = ${orderHistory.orderId}, STATUS = ${orderHistory.status}, ORDERED_TIME = ${orderHistory.orderedTime}
    WHERE id = ${id}
  """.update

  def select(id: Long): Query0[OrderHistory] = sql"""
    SELECT ID, ORDER_ID, STATUS, ORDERED_TIME
    FROM ORDER_HISTORY
    WHERE ID = $id
  """.query

  def delete(id: Long): Update0 = sql"""
    DELETE FROM MEAL WHERE ID = $id
  """.update

}

class DoobieOrderHistoryRepository[F[_]: Sync](xa: Transactor[F]) extends OrderHistoryRepositoryAlgebra[F] {
  override def create(order: OrderHistory): F[OrderHistory] = OrderHistorySql
    .insert(order)
    .withUniqueGeneratedKeys[Long]("ID")
    .map(id => order.copy(id = id.some))
    .transact(xa);

  override def get(orderId: Long): F[Option[OrderHistory]] = OrderHistorySql.select(orderId).option.transact(xa)

  override def update(orderHistory: OrderHistory): F[Option[OrderHistory]] = OptionT
    .fromOption[ConnectionIO](orderHistory.id)
    .semiflatMap(id => OrderHistorySql.update(orderHistory,id).run.as(orderHistory))
    .value
    .transact(xa)
}

object DoobieOrderHistoryRepository {
  def apply[F[_]: Sync](xa: Transactor[F]): DoobieOrderHistoryRepository[F] =
    new DoobieOrderHistoryRepository(xa)
}