ThisBuild / version := "1.1.0"

ThisBuild / scalaVersion := "2.13.12"

lazy val root = (project in file("."))
  .settings(
    name := "ip2location-io-scala"
  )
