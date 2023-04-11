package repository.doobie


import algebras.OwnerRepositoryAlgebra
import cats.data.OptionT
import cats.effect._
import cats.implicits.{catsSyntaxOptionId, toFunctorOps}
import domain.entity.Gender.Gender
import domain.entity.{Gender, Owner}
import doobie._
import doobie.implicits._
import doobie.postgres.implicits._

private object OwnerSQL {

  implicit val StatusMeta: Meta[Gender] =
    Meta[String].imap(Gender.withName)(_.toString)

  def insert(owner: Owner): Update0 = sql"""
    INSERT INTO OWNERS (USERNAME, NAME, SURNAME, BIRTHDAY, EMAIL, PASSWORD, GENDER)
    VALUES (${owner.username}, ${owner.name}, ${owner.surname}, ${owner.birthday}, ${owner.email}, ${owner.password}, ${owner.gender})
  """.update

  def update(owner: Owner, id: Long): Update0 = sql"""
    UPDATE OWNERS
    SET USERNAME = ${owner.username}, NAME = ${owner.name}, SURNAME = ${owner.surname}, BIRTHDAY = $owner.birthday,
        EMAIL = ${owner.email}, PASSWORD = ${owner.password}, GENDER = $owner.gender
    WHERE ID = $id
  """.update


  def select(userId: Long): Query0[Owner] = sql"""
    SELECT ID, USERNAME, NAME, SURNAME, BIRTHDAY, EMAIL, PASSWORD, GENDER
    FROM OWNERS
    WHERE ID = $userId
  """.query[Owner]



  def delete(userId: Long): Update0 = sql"""
    DELETE FROM OWNERS WHERE ID = $userId
  """.update

}

class DoobieOwnerRepository[F[_]: Sync](val xa: Transactor[F])  extends OwnerRepositoryAlgebra[F] {
  override def create(owner: Owner): F[Owner] = OwnerSQL
    .insert(owner)
    .withUniqueGeneratedKeys[Long]("ID")
    .map(id => owner.copy(id = id.some))
    .transact(xa)

  override def update(owner: Owner): F[Option[Owner]] = OptionT
    .fromOption[ConnectionIO](owner.id)
    .semiflatMap(id => OwnerSQL.update(owner,id).run.as(owner))
    .value
    .transact(xa)

  override def get(id: Long): F[Option[Owner]] = OwnerSQL.select(id).option.transact(xa)

  override def delete(id: Long): F[Option[Owner]] = OptionT(get(id))
    .semiflatMap(owner => OwnerSQL.delete(id).run.transact(xa).as(owner))
    .value
}

object DoobieOwnerRepository {
  def apply[F[_]: Sync](
                         xa: Transactor[F],
                       ): DoobieOwnerRepository[F] =
    new DoobieOwnerRepository(xa)
}
