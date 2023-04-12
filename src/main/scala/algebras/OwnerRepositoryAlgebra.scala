package algebras

import entity.Owner

trait OwnerRepositoryAlgebra[F[_]] {
  def create(owner: Owner): F[Owner]

  def update(owner: Owner): F[Option[Owner]]

  def get(id: Long): F[Option[Owner]]

  def delete(id: Long): F[Option[Owner]]
}
