name := "untitled2"

version := "0.1"

scalaVersion := "2.13.10"

lazy val doobieVersion = "1.0.0-RC1"
lazy val catsVersion = "3.4.8"

libraryDependencies ++= Seq(
  "org.tpolecat" %% "doobie-core"     % doobieVersion,
  "org.tpolecat" %% "doobie-postgres" % doobieVersion,
  "org.tpolecat" %% "doobie-specs2"   % doobieVersion,
  "org.typelevel" %% "cats-effect" % catsVersion
)