package algebras

import domain.entity.{RegularUser, RegularUserWithId}

trait UsersRepositoryAlgebra[F[_]] {
  def create(user: RegularUser): F[RegularUser]

  def update(user: RegularUserWithId): F[Option[RegularUserWithId]]

  def get(id: Long): F[Option[RegularUserWithId]]

  def delete(id: Long): F[Option[RegularUserWithId]]
}
