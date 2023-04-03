package algebras

import domain.entity.{Owner,OwnerWithId}

trait OwnerRepositoryAlgebra[F[_]] {
  def create(owner: Owner): F[OwnerWithId]

  def update(owner: OwnerWithId): F[Option[OwnerWithId]]

  def get(id: Long): F[Option[OwnerWithId]]

  def delete(id: Long): F[Option[OwnerWithId]]
}
