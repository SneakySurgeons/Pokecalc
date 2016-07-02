package sneakysurgeons.pokecalc;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Optional;

/**
 * Interface for sources that provide a resource.
 *
 * @author Peter Jeschke
 */
public interface ResourceProvider<T> {
  /**
   * Provides the resource.
   *
   * @param id     the id of the resource
   * @param locale the locale of the requested language
   * @return an optional that contains the resource, only if it exists
   */
  @NotNull
  Optional<T> get(int id, @NotNull Locale locale);
}
