name := "entrenamiento_starbucks"

version := "1.0"

scalaVersion := "2.12.1"

resolvers ++= Seq(
  "Typesafe repository releases" at "http://repo.typesafe" + ".com/typesafe/releases/"
)

libraryDependencies ++= {
  Seq(
    "com.typesafe.akka" %% "akka-http" % "10.0.4",
    "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.4"
  )
}