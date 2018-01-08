name := "sbt-pom-reader"
organization := "ch.epfl.scala"
licenses +=("Apache-2.0", url("http://opensource.org/licenses/Apache-2.0"))
sbtPlugin := true
scalaVersion := "2.12.4"
releaseEarlyWith := SonatypePublisher

val mavenVersion = "3.5.2"
val aetherVersion = "1.1.0"

libraryDependencies ++= Seq(
  "org.apache.maven" % "maven-embedder" % mavenVersion,
  "org.apache.maven" % "maven-resolver-provider" % mavenVersion,

  "org.apache.maven.resolver" % "maven-resolver" % aetherVersion,
  "org.apache.maven.resolver" % "maven-resolver-transport-wagon" % aetherVersion,
  "org.apache.maven.resolver" % "maven-resolver-transport-http" % aetherVersion,
  "org.apache.maven.resolver" % "maven-resolver-transport-file" % aetherVersion,
  "org.apache.maven.resolver" % "maven-resolver-connector-basic" % aetherVersion,
)

update := update.dependsOn(evicted).value

initialCommands in console :=
  """| import ch.epfl.scala.sbt.pom._
     | import sbt._
     | val localRepo = file(sys.props("user.home")) / ".m2" / "repository"
     | val pomFile = file("src/sbt-test/simple-pom/can-extract-basics/pom.xml")
     | val pom = loadEffectivePom(pomFile, localRepo, Seq.empty, Map.empty)
     | """.stripMargin

scriptedLaunchOpts := scriptedLaunchOpts.value ++ Seq("-Dproject.version=" + version.value)
