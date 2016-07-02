package sneakysurgeons.pokecalc;

import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;
import org.takes.Response;
import org.takes.facets.fork.RqRegex;
import org.takes.facets.fork.TkRegex;
import org.takes.rs.RsWithStatus;
import sneakysurgeons.pokecalc.jinja.RsJinja;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Common take that extracts the locale from the url.
 *
 * @author Peter Jeschke
 */
public abstract class TkCommon implements TkRegex {

  @NotNull
  @Override
  public final Response act(@NotNull RqRegex req) throws IOException {
    Locale locale = Locale.forLanguageTag(req.matcher().group("locale"));
    if (!ResourceBundle.getBundle("messages", locale).getLocale().equals(locale)) {
      return showUnkownLocale(req, locale);
    }
    return act(req, locale);
  }

  @NotNull
  protected abstract Response act(@NotNull RqRegex req, @NotNull Locale locale) throws IOException;

  /**
   * Fallback in case the locale doesn't exist.
   */
  @NotNull
  @SuppressWarnings("WeakerAccess")
  protected Response showUnkownLocale(@NotNull RqRegex req, @NotNull Locale locale) throws IOException {
    return new RsWithStatus(new RsJinja(ResourceTool.getResource("/templates/404_missingLocale.html"),
                                        ImmutableMap.of("messages", ResourceBundle.getBundle("messages"))), 404);
  }
}
