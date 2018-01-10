name := """household"""

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.4"

resolvers += Resolver.jcenterRepo

libraryDependencies ++= Seq(
    jdbc,
    guice,
    evolutions,
    ehcache,
    "com.h2database" % "h2" % "1.4.196",
    "com.mohiva" %% "play-silhouette" % "5.0.0",
    "com.mohiva" %% "play-silhouette-password-bcrypt" % "5.0.0",
    "com.mohiva" %% "play-silhouette-crypto-jca" % "5.0.0",
    "com.mohiva" %% "play-silhouette-persistence" % "5.0.0",
    "com.mohiva" %% "play-silhouette-testkit" % "5.0.0" % "test",
    "net.codingwell" %% "scala-guice" % "4.1.1",
    "com.iheart" %% "ficus" % "1.4.3"
)