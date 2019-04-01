scalaVersion := "2.12.8"
organization := "com.mytask"


lazy val hello = (project in file("."))
  .settings(
    name := "Crimes" ,
    mainClass in assembly := Some("com.mytask.Crimes"),
    assemblyJarName in assembly := "crimes.jar",
  )