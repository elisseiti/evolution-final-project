//package repository.doobie
//
//import algebras.OrderRepositoryAlgebra
//import cats.data.OptionT
//import cats.effect.Sync
//import cats.syntax.all._
//import domain.entity.OrderStatus.OrderStatus
//import domain.entity.{OrderStatus, Orders, OrdersWithId}
//import doobie._
//import doobie.implicits._
//
//private object OrderSQL {
//  /* We require type StatusMeta to handle our ADT Status */
//  implicit val status: Meta[OrderStatus] =
//    Meta[String].imap(OrderStatus.withName)(_.entryName)
//
//  def select(orderId: Long): Query0[Orders] = sql"""
//    SELECT ORDER_ID, SHIP_DATE, STATUS, COMPLETE, ID, USER_ID
//    FROM ORDERS
//    WHERE ID = $orderId
//  """.query[Orders]
//
//  def insert(order: Orders): Update0 = sql"""
//    INSERT INTO ORDERS (PET_ID, SHIP_DATE, STATUS, COMPLETE, USER_ID)
//    VALUES (${order.petId}, ${order.shipDate}, ${order.status}, ${order.complete}, ${order.userId.get})
//  """.update
//
//  def delete(orderId: Long): Update0 = sql"""
//    DELETE FROM ORDERS
//    WHERE ID = $orderId
//  """.update
//}
//
//class DoobieOrderRepository[F[_]: Sync](val xa: Transactor[F])
//    extends OrderRepositoryAlgebra[F] {
//  import OrderSQL._
//
//  def create(order: OrdersWithId): F[OrdersWithId] =
//    insert(order)
//      .withUniqueGeneratedKeys[Long]("ID")
//      .map(id => order.copy(id = id.some))
//      .transact(xa)
//
//  def get(orderId: Long): F[Option[OrdersWithId]] =
//    OrderSQL.select(orderId).option.transact(xa)
//
//  def delete(orderId: Long): F[Option[OrdersWithId]] =
//    OptionT(get(orderId))
//      .semiflatMap(order => OrderSQL.delete(orderId).run.transact(xa).as(order))
//      .value
//}
//
//object DoobieMealRepository {
//  def apply[F[_]: Sync](
//                         xa: Transactor[F],
//                       ): DoobieMealRepository[F] =
//    new DoobieMealRepository(xa)
//}
