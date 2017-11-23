lazy val appSettings = Seq(
  name := "constellation",
  scalaVersion := "2.11.8",
  version := "0.0.1"
)

lazy val scalaTestVersion = "2.2.6"

lazy val akkaVersion = "2.4.2"

lazy val lib = Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion
  exclude("org.scala-lang", "scala-library"),
  "com.typesafe.akka" %% "akka-slf4j" % "2.4.16" ,
  "com.typesafe.akka" %% "akka-stream" % "2.4.16",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.4.16",
  "com.typesafe.akka" %% "akka-testkit" % "2.4.16",
  "com.typesafe.akka" %% "akka-remote" % "2.5.6",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
//  "org.consensusresearch" %% "scorex-basics" % "1.+",
//  "org.consensusresearch" %% "scorex-consensus" % "1.+",
//  "org.consensusresearch" %% "scorex-perma" % "1.+",
//  "org.consensusresearch" %% "scorex-transaction" % "1.+",
  "org.scalactic" %% "scalactic" % "3.0.1" % "test",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "org.scalacheck" %% "scalacheck" % "1.13.+" % "test",
  "com.typesafe.akka" %% "akka-testkit" % "2.4.17" % "test",
  "net.databinder.dispatch" %% "dispatch-core" % "+" % "test")

lazy val testLib = Seq(
  "org.scalatest" %% "scalatest" % scalaTestVersion % Test withSources()
)

lazy val constellation = (project in file("."))
  .settings(appSettings: _*)
  .settings(
    libraryDependencies ++= lib,
    libraryDependencies ++= testLib
  )