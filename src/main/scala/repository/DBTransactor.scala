package repository

import cats.effect.Async
import doobie.Transactor
import DatabaseConfig.{dbDriverName, dbPwd, dbUrl, dbUser}

object DBTransactor {

  /** Simplest `transactor`, slow[er], inefficient for large apps, but OK for testing and learning.
   * Derives transactor from driver.
   *
   * `Transactor` is a means for transformation `ConnectionIO ~> IO`
   */
  def make[F[_]: Async]: Transactor[F] =
    Transactor.fromDriverManager[F](
      driver = dbDriverName,
      url = dbUrl,
      user = dbUser,
      pass = dbPwd,
    )


}
