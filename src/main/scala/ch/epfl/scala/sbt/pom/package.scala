package ch.epfl.scala.sbt

import java.io.File

import org.apache.maven.repository.internal._
import org.eclipse.aether.impl.{
  ArtifactDescriptorReader,
  DefaultServiceLocator,
  MetadataGeneratorFactory,
  VersionRangeResolver,
  VersionResolver
}
import org.eclipse.aether.repository.LocalRepository
import org.eclipse.aether.spi.connector.RepositoryConnectorFactory
import org.eclipse.aether.connector.basic.BasicRepositoryConnectorFactory
import org.eclipse.aether.spi.connector.transport.TransporterFactory
import org.eclipse.aether.transport.file.FileTransporterFactory
import org.eclipse.aether.transport.http.HttpTransporterFactory
import org.eclipse.aether.{
  DefaultRepositorySystemSession,
  RepositorySystem,
  RepositorySystemSession
}

/** Helper methods for dealing with starting up Aether. */
package object pom {
  def newRepositorySystemImpl: RepositorySystem = {
    val locator = new DefaultServiceLocator()
    locator.addService(classOf[ArtifactDescriptorReader],
                       classOf[DefaultArtifactDescriptorReader])
    locator.addService(classOf[VersionResolver],
                       classOf[DefaultVersionResolver])
    locator.addService(classOf[VersionRangeResolver],
                       classOf[DefaultVersionRangeResolver])
    locator.addService(classOf[MetadataGeneratorFactory],
                       classOf[SnapshotMetadataGeneratorFactory])
    locator.addService(classOf[MetadataGeneratorFactory],
                       classOf[VersionsMetadataGeneratorFactory])
    locator.addService(classOf[RepositoryConnectorFactory],
                       classOf[BasicRepositoryConnectorFactory])
    locator.addService(classOf[TransporterFactory],
                       classOf[FileTransporterFactory])
    locator.addService(classOf[TransporterFactory],
                       classOf[HttpTransporterFactory])
    locator.getService(classOf[RepositorySystem])
  }

  def newSessionImpl(system: RepositorySystem,
                     localRepoDir: File): RepositorySystemSession = {
    val session = new DefaultRepositorySystemSession()
    val localRepo: LocalRepository = new LocalRepository(
      localRepoDir.getAbsolutePath)
    session.setLocalRepositoryManager(
      system.newLocalRepositoryManager(session, localRepo))
    session
  }

  def defaultLocalRepo: java.io.File = {
    import sbt._
    file(sys.props("user.home")) / ".m2" / "repository"
  }

  def loadEffectivePom(pom: File,
                       localRepo: File = defaultLocalRepo,
                       profiles: Seq[String],
                       userProps: Map[String, String]) =
    MavenPomResolver(localRepo).loadEffectivePom(pom,
                                                 Seq.empty,
                                                 profiles,
                                                 userProps)
}
