package algebras

import domain.entity.{RegularUser}

trait UsersRepositoryAlgebra[F[_]] {
  def create(user: RegularUser): F[RegularUser]

  def update(user: RegularUser): F[Option[RegularUser]]

  def get(id: Long): F[Option[RegularUser]]

  def delete(id: Long): F[Option[RegularUser]]
}
