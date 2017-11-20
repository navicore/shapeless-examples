// give the user a nice default project!
lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.4",
      libraryDependencies ++= Seq(
          "com.chuusai" %% "shapeless" % "2.3.2"
        )
    )),
    name := "naviless.g8"
  )
