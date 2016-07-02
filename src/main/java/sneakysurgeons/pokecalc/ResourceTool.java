package sneakysurgeons.pokecalc;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Toolkit to load a resource.
 *
 * @author Peter Jeschke
 */
public class ResourceTool {
  /**
   * Returns an URL to the resource. Honors debug settings.
   *
   * @param path the relative path to the resource
   * @return the URL the resource
   * @throws MalformedURLException if the resulting URL is malformed
   */
  // TODO: 26.06.2016 public static: Code smell
  @Nullable
  public static URL getResource(@NotNull String path) throws MalformedURLException {
    if (System.getProperty("DEVELOP", "false").equals("true")) {
      return new URL(System.getProperty("DEVELOP_PATH") + path);
    } else {
      return ResourceTool.class.getResource(path);
    }
  }
}
