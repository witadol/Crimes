scalaVersion := "2.12.8"
organization := "com.mytask"


lazy val hello = (project in file("."))
  .settings(
    name := "Crimes" ,
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test,
    mainClass in assembly := Some("com.mytask.Crimes"),
    assemblyJarName in assembly := "crimes.jar",
  )