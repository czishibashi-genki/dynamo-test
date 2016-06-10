name := """fox2_dynamo_test"""

version := "1.0"

scalaVersion := "2.11.7"

// Change this to another test framework if you prefer
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "com.github.seratch" %% "awscala" % "0.5.+",
  "com.typesafe.akka" %% "akka-actor" % "2.4.7"
)

// jar作成時にTestのファイルは無視する
test in assembly := {}