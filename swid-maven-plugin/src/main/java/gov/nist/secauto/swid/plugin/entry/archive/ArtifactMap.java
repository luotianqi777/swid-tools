/**
 * Portions of this software was developed by employees of the National Institute
 * of Standards and Technology (NIST), an agency of the Federal Government.
 * Pursuant to title 17 United States Code Section 105, works of NIST employees are
 * not subject to copyright protection in the United States and are considered to
 * be in the public domain. Permission to freely use, copy, modify, and distribute
 * this software and its documentation without fee is hereby granted, provided that
 * this notice and disclaimer of warranty appears in all copies.
 *
 * THE SOFTWARE IS PROVIDED 'AS IS' WITHOUT ANY WARRANTY OF ANY KIND, EITHER
 * EXPRESSED, IMPLIED, OR STATUTORY, INCLUDING, BUT NOT LIMITED TO, ANY WARRANTY
 * THAT THE SOFTWARE WILL CONFORM TO SPECIFICATIONS, ANY IMPLIED WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, AND FREEDOM FROM
 * INFRINGEMENT, AND ANY WARRANTY THAT THE DOCUMENTATION WILL CONFORM TO THE
 * SOFTWARE, OR ANY WARRANTY THAT THE SOFTWARE WILL BE ERROR FREE. IN NO EVENT
 * SHALL NIST BE LIABLE FOR ANY DAMAGES, INCLUDING, BUT NOT LIMITED TO, DIRECT,
 * INDIRECT, SPECIAL OR CONSEQUENTIAL DAMAGES, ARISING OUT OF, RESULTING FROM, OR
 * IN ANY WAY CONNECTED WITH THIS SOFTWARE, WHETHER OR NOT BASED UPON WARRANTY,
 * CONTRACT, TORT, OR OTHERWISE, WHETHER OR NOT INJURY WAS SUSTAINED BY PERSONS OR
 * PROPERTY OR OTHERWISE, AND WHETHER OR NOT LOSS WAS SUSTAINED FROM, OR AROSE OUT
 * OF THE RESULTS OF, OR USE OF, THE SOFTWARE OR SERVICES PROVIDED HEREUNDER.
 */

package gov.nist.secauto.swid.plugin.entry.archive;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.archiver.ArchiveEntry;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class ArtifactMap {

  protected static Map<String, Artifact> initialize(MavenProject project) {
    Map<String, Artifact> retval = new HashMap<>();
    Artifact target = project.getArtifact();
    retval.put(target.getFile().getAbsolutePath(), target);

    project.getArtifacts().stream().filter(includeOnlyCompileAndRuntime())
        .forEach(a -> retval.put(a.getFile().getAbsolutePath(), a));
    return retval;
  }

  protected static Predicate<Artifact> includeOnlyCompileAndRuntime() {
    return new Predicate<Artifact>() {

      @Override
      public boolean test(Artifact artifact) {
        String scope = artifact.getScope();
        return Artifact.SCOPE_COMPILE.equals(scope) || Artifact.SCOPE_RUNTIME.equals(scope);
      }
    };
  }

  private final Map<String, Artifact> artifactFileToArtifactMap;

  protected ArtifactMap(Map<String, Artifact> map) {
    artifactFileToArtifactMap = map;
  }

  public ArtifactMap(MavenProject project) {
    this(initialize(project));
  }

  /**
   * Retrieve the artifact for an archiver entry.
   * 
   * @param entry
   *          the archive entry
   * @return the matching artifact or {@code null} if a matching artifact was not found
   */
  public Artifact lookupArtifactForArchiveEntry(ArchiveEntry entry) {
    Path entryPath = Paths.get(entry.getResource().getName());
    String path = entryPath.toString();
    return artifactFileToArtifactMap.get(path);
  }
}