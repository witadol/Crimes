ThisBuild / scalaVersion := "2.12.8"
ThisBuild / organization := "com.task"

lazy val hello = (project in file("."))
  .settings(
    name := "Crimes"
  )