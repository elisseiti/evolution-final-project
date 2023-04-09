package algebras

import domain.entity.Orders

trait OrderRepositoryAlgebra[F[_]] {
  def create(order: Orders): F[Orders]

  def get(orderId: Long): F[Option[Orders]]

  def delete(orderId: Long): F[Option[Orders]]

  def update(orders: Orders): F[Orders]
}
