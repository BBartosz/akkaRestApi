name := "AkkaRestApi"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= {
  val akkaStreamVersion = "2.0-M2"
  val akkaVersion = "2.3.12"
  val scalaTestVersion       = "2.2.4"
  val scalaMockVersion       = "3.2.2"
  val slickVersion = "3.1.0"

  Seq(
    "com.typesafe.akka" %% "akka-actor"                           % akkaVersion,
    "com.typesafe.akka" %% "akka-stream-experimental"             % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-experimental"               % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-core-experimental"          % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-spray-json-experimental"    % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-testkit-experimental"       % akkaStreamVersion,
    "com.typesafe.slick" %% "slick"                               % slickVersion,
    "org.slf4j"          %  "slf4j-nop"                            % "1.6.4",
    "org.postgresql"     %  "postgresql"                           % "9.4-1201-jdbc41",
    "org.flywaydb"       %  "flyway-core"                          % "3.2.1",
    "com.typesafe.akka"  %% "akka-testkit"                         % akkaVersion % "test",
    "org.scalatest"      %% "scalatest"                            % scalaTestVersion,
    "org.scalamock"      %% "scalamock-scalatest-support"          % scalaMockVersion,
    "com.typesafe.akka"  %% "akka-http-testkit-experimental"       % akkaStreamVersion
  )
}