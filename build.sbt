name := "sbt-pom-reader"
organization := "ch.epfl.scala"
licenses +=("Apache-2.0", url("http://opensource.org/licenses/Apache-2.0"))
sbtPlugin := true
scalaVersion := "2.12.4"
scalacOptions += "-target:jvm-1.6"
releaseEarlyWith := SonatypePublisher

val mvnVersion = "3.3.9"
val mvnWagonVersion = "2.10"
val aetherVersion = "1.0.2.v20150114"

libraryDependencies ++= Seq(
  "org.apache.maven" % "maven-embedder" % mvnVersion,
  "org.apache.maven" % "maven-aether-provider" % mvnVersion,
  "org.eclipse.aether" % "aether-transport-wagon" % aetherVersion,
  "org.apache.maven.wagon" % "wagon-http" % mvnWagonVersion,
  "org.apache.maven.wagon" % "wagon-http-lightweight" % mvnWagonVersion,
  "org.apache.maven.wagon" % "wagon-file" % mvnWagonVersion
)

initialCommands in console :=
  """| import ch.epfl.scala.sbt.pom._
     | import sbt._
     | val localRepo = file(sys.props("user.home")) / ".m2" / "repository"
     | val pomFile = file("src/sbt-test/simple-pom/can-extract-basics/pom.xml")
     | val pom = loadEffectivePom(pomFile, localRepo, Seq.empty, Map.empty)
     | """.stripMargin

scriptedLaunchOpts := scriptedLaunchOpts.value ++ Seq("-Dproject.version=" + version.value)
