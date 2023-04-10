package repository.doobie

object DatabaseConfig {
  val dbDriverName = "org.postgresql.Driver"
  val dbUrl = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"
  val dbUser = "postgres"
  val dbPwd = "postgres"
}
