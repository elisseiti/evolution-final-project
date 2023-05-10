name := "untitled2"

version := "0.1"

scalaVersion := "2.13.10"

lazy val doobieVersion = "1.0.0-RC1"
lazy val catsVersion = "3.4.8"
lazy val doobiePostgres = "1.0.0-RC1"
lazy val http4sVersion = "1.0.0-M39"
lazy val circeVersion = "0.14.5"
lazy val circeDerivationVersion = "0.13.0-M5"


libraryDependencies ++= Seq(
  "org.tpolecat" %% "doobie-core"     % doobieVersion,
  "org.tpolecat" %% "doobie-postgres" % doobieVersion,
  "org.tpolecat" %% "doobie-specs2"   % doobieVersion,
  "org.typelevel" %% "cats-effect" % catsVersion,
  "org.tpolecat" %% "doobie-postgres" % doobiePostgres,
  "org.http4s" %% "http4s-ember-client" % http4sVersion,
  "org.http4s" %% "http4s-ember-server" % http4sVersion,
  "org.http4s" %% "http4s-dsl"          % http4sVersion,
  "org.http4s" %% "http4s-ember-server" % http4sVersion,
  "org.http4s" %% "http4s-dsl"          % http4sVersion,
  "org.http4s" %% "http4s-circe"        % http4sVersion,
  "io.circe" %% "circe-core"           % circeVersion,
  "io.circe" %% "circe-generic"        % circeVersion
)

