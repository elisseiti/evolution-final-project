name := "untitled2"

version := "0.1"

scalaVersion := "2.13.10"

lazy val doobieVersion = "1.0.0-RC1"
lazy val catsVersion = "3.4.8"
lazy val doobiePostgresCirce = "1.0.0-RC1"
lazy val doobiePostgres = "1.0.0-RC1"

libraryDependencies ++= Seq(
  "org.tpolecat" %% "doobie-core"     % doobieVersion,
  "org.tpolecat" %% "doobie-postgres" % doobieVersion,
  "org.tpolecat" %% "doobie-specs2"   % doobieVersion,
  "org.typelevel" %% "cats-effect" % catsVersion,
"org.tpolecat" %% "doobie-postgres-circe" % doobiePostgresCirce,
"org.tpolecat" %% "doobie-postgres" % doobiePostgres
)