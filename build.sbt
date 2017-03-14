name := "entrenamiento_starbucks"

version := "1.0"

scalaVersion := "2.12.1"

resolvers ++= Seq(
  Resolver.bintrayRepo("hseeberger", "maven"),
  "Typesafe repository releases" at "http://repo.typesafe" + ".com/typesafe/releases/",
  "Twitter Repository"           at "http://maven.twttr.com"
)

libraryDependencies ++= {

  val akkaV = "10.0.4"
  val phantomV = "2.3.1"
  val circeVersion = "0.7.0"

  Seq(
    "com.typesafe.akka"   %% "akka-http"            % akkaV,
    "com.outworkers"      %% "phantom-dsl"          % phantomV,
    "io.circe"            %% "circe-core"           % circeVersion,
    "io.circe"            %% "circe-generic"        % circeVersion,
    "io.circe"            %% "circe-parser"         % circeVersion,
    "de.heikoseeberger"   %% "akka-http-circe"      % "1.13.0"
  )
}