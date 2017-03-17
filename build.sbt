name := "akka_http_training"

version := "1.0"

scalaVersion := "2.12.1"

resolvers ++= Seq(
  Resolver.bintrayRepo("hseeberger", "maven"),
  "Typesafe repository releases" at "http://repo.typesafe" + ".com/typesafe/releases/",
  "Twitter Repository"           at "http://maven.twttr.com",
  "Artima Maven Repository"      at "http://repo.artima.com/releases"
)

libraryDependencies ++= {

  val akkaHttpV = "10.0.4"
  val phantomV = "2.3.1"
  val circeV = "0.7.0"

  Seq(
    "com.typesafe.akka"   %% "akka-http"            % akkaHttpV,
    "com.outworkers"      %% "phantom-dsl"          % phantomV,
    "io.circe"            %% "circe-core"           % circeV,
    "io.circe"            %% "circe-parser"         % circeV,
    "io.circe"            %% "circe-generic"        % circeV,
    "de.heikoseeberger"   %% "akka-http-circe"      % "1.13.0",
    // Test
    "com.typesafe.akka"   %% "akka-http-testkit"    % akkaHttpV     % "test",
    "org.scalactic"       %% "scalactic"            % "3.0.1"       % "test",
    "org.scalatest"       %% "scalatest"            % "3.0.1"       % "test",
    "org.mockito"         %  "mockito-core"         % "2.7.17"      % "test"
  )
}

coverageEnabled := true