package repository

import algebras.UsersRepositoryAlgebra
import cats.data.OptionT
import cats.effect._
import cats.implicits.{catsSyntaxOptionId, toFunctorOps}
import doobie._
import doobie.implicits._
import entity.Gender.Gender
import entity.{Gender, RegularUser}
import doobie.postgres.implicits._

private object UserSQL {

  implicit val StatusMeta: Meta[Gender] =
    Meta[String].imap(Gender.withName)(_.toString)

  def insert(user: RegularUser): Update0 = sql"""
    INSERT INTO USERS (USERNAME, NAME, SURNAME, BIRTHDAY, EMAIL, PASSWORD, GENDER)
    VALUES (${user.username}, ${user.name}, ${user.surname}, ${user.birthday}, ${user.email}, ${user.password}, ${user.gender})
  """.update

  def update(user: RegularUser, id: Long): Update0 = sql"""
    UPDATE USERS
    SET USERNAME = ${user.username}, NAME = ${user.name}, SURNAME = ${user.surname}, BIRTHDAY = $user.birthday,
        EMAIL = ${user.email}, PASSWORD = ${user.password}, GENDER = ${user.gender}
    WHERE ID = $id
  """.update


  def select(userId: Long): Query0[RegularUser] = sql"""
    SELECT ID, USERNAME, NAME, SURNAME, BIRTHDAY, EMAIL, PASSWORD, GENDER
    FROM USERS
    WHERE ID = $userId
  """.query[RegularUser]



  def delete(userId: Long): Update0 = sql"""
    DELETE FROM USERS WHERE ID = $userId
  """.update

}

class DoobieUserRepository[F[_]: Sync](val xa: Transactor[F])  extends UsersRepositoryAlgebra[F] {
  override def create(user: RegularUser): F[RegularUser] = UserSQL
    .insert(user)
    .withUniqueGeneratedKeys[Long]("ID")
    .map(id => user.copy(id = id.some))
    .transact(xa)

  override def update(user: RegularUser): F[Option[RegularUser]] = OptionT
    .fromOption[ConnectionIO](user.id)
    .semiflatMap(id => UserSQL.update(user,id).run.as(user))
    .value
    .transact(xa)

  override def get(id: Long): F[Option[RegularUser]] = UserSQL.select(id).option.transact(xa)

  override def delete(id: Long): F[Option[RegularUser]] = OptionT(get(id))
    .semiflatMap(user => UserSQL.delete(id).run.transact(xa).as(user))
    .value
}

object DoobieUserRepository {
  def apply[F[_]: Sync](
                         xa: Transactor[F],
                       ): DoobieUserRepository[F] =
    new DoobieUserRepository(xa)
}
