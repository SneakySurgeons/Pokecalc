package sneakysurgeons.pokecalc;

import org.jetbrains.annotations.NotNull;
import org.takes.Response;
import org.takes.facets.fork.RqRegex;

import java.io.IOException;
import java.util.Locale;

/**
 * Common take that extracts the locale, the name and the id of a resource.
 *
 * @author Peter Jeschke
 */
public abstract class TkCommonDetail extends TkCommon {
  @NotNull
  @Override
  protected final Response act(@NotNull RqRegex req, @NotNull Locale locale) throws IOException {
    return act(req, locale, Integer.parseInt(req.matcher().group("id")), req.matcher().group("name"));
  }

  @NotNull
  protected abstract Response act(@NotNull RqRegex req, @NotNull Locale locale, int id, @NotNull String name)
          throws IOException;
}
