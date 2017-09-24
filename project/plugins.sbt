resolvers += Resolver.url(
  "bintray-sbt-plugin-releases",
    url("http://dl.bintray.com/content/sbt/sbt-plugin-releases"))(
        Resolver.ivyStylePatterns)

resolvers += "jgit-repo" at "http://download.eclipse.org/jgit/maven"

addSbtPlugin("org.foundweekends" % "sbt-bintray" % "0.5.1")
libraryDependencies += {
  "org.scala-sbt" % "scripted-plugin" % sbtVersion.value
}

