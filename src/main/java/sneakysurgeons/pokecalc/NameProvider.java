package sneakysurgeons.pokecalc;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

/**
 * Interface for all sources that can provide a name for a resource.
 *
 * @author Peter Jeschke
 */
public interface NameProvider {
  /**
   * Indicates whether a name can be provided for the given id and locale.
   *
   * @param id     the id of the resource
   * @param locale the wanted locale
   * @return true if a name can be provided
   */
  boolean hasName(int id, @NotNull Locale locale);

  /**
   * Returns the name of the resource.
   *
   * @param id     the id of the resource
   * @param locale the wanted locale
   * @return the name
   */
  @NotNull
  String getName(int id, @NotNull Locale locale);
}
