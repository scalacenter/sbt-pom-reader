name := "sbt-pom-reader"
organization := "ch.epfl.scala"
licenses +=("Apache-2.0", url("http://opensource.org/licenses/Apache-2.0"))
sbtPlugin := true
scalaVersion := "2.12.4"
releaseEarlyWith := SonatypePublisher

val mvnVersion = "3.5.2"
val aetherProviderVersion = "3.3.9"
val mvnWagonVersion = "3.0.0"
val aetherVersion = "1.1.0"

libraryDependencies ++= Seq(
  "org.apache.maven" % "maven-embedder" % mvnVersion,
  "org.apache.maven" % "maven-aether-provider" % aetherProviderVersion,
  "org.eclipse.aether" % "aether-transport-wagon" % aetherVersion,
  "org.apache.maven.wagon" % "wagon-http" % mvnWagonVersion,
  "org.apache.maven.wagon" % "wagon-http-lightweight" % mvnWagonVersion,
  "org.apache.maven.wagon" % "wagon-file" % mvnWagonVersion,
  "org.apache.maven.plugin-tools" % "maven-plugin-tools-api" % "3.5",
)

dependencyOverrides ++= List(
  "org.apache.maven.wagon" % "wagon-provider-api" % mvnWagonVersion,
  "org.eclipse.aether" % "aether-api" % aetherVersion,
  "org.eclipse.aether" % "aether-util" % aetherVersion,
  "org.eclipse.aether" % "aether-spi" % aetherVersion,
  "com.google.guava" % "guava" % "18.0",
  "org.codehaus.plexus" % "plexus-utils" % "3.0.24",
  "org.apache.maven" % "maven-model" % "3.5.2",
  "org.apache.maven" % "maven-plugin-api" % "3.5.2",
  "org.apache.maven" % "maven-artifact" % "3.5.2",
  "org.apache.maven" % "maven-repository-metadata" % "3.5.2",
  "org.apache.maven" % "maven-settings" % "3.5.2",
)

initialCommands in console :=
  """| import ch.epfl.scala.sbt.pom._
     | import sbt._
     | val localRepo = file(sys.props("user.home")) / ".m2" / "repository"
     | val pomFile = file("src/sbt-test/simple-pom/can-extract-basics/pom.xml")
     | val pom = loadEffectivePom(pomFile, localRepo, Seq.empty, Map.empty)
     | """.stripMargin

scriptedLaunchOpts := scriptedLaunchOpts.value ++ Seq("-Dproject.version=" + version.value)
