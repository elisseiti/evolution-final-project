package algebras

import domain.entity.{Orders, OrdersWithId}

trait OrderRepositoryAlgebra[F[_]] {
  def create(order: Orders): F[Orders]

  def get(orderId: Long): F[Option[OrdersWithId]]

  def delete(orderId: Long): F[Option[OrdersWithId]]

  def update(orders: OrdersWithId): F[OrdersWithId]
}
