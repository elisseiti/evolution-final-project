package algebras

import domain.entity.OrderHistory

trait OrderHistoryRepositoryAlgebra[F[_]] {
  def create(order: OrderHistory): F[OrderHistory]

  def get(orderId: Long): F[Option[OrderHistory]]

  def update(orderHistory: OrderHistory) : F[Option[OrderHistory]]

}
