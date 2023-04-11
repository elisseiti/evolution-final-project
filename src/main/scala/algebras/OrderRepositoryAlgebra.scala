package algebras

import domain.entity.Orders

trait OrderRepositoryAlgebra[F[_]] {
  def create(order: Orders): F[Orders]

  def get(orderId: Long): F[Option[Orders]]

}
