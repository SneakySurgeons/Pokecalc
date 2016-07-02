package sneakysurgeons.pokecalc;

import org.jetbrains.annotations.NotNull;
import org.takes.Response;
import org.takes.facets.fork.RqRegex;
import org.takes.rs.RsRedirect;
import org.takes.rs.RsWithStatus;

import java.io.IOException;
import java.util.Locale;

/**
 * Common class for urls that miss the name of the resource.
 *
 * @author Peter Jeschke
 */
class TkCommonMissingName extends TkCommon {

  @NotNull
  private final String path;
  @NotNull
  private final NameProvider nameProvider;

  TkCommonMissingName(@NotNull String path, @NotNull NameProvider nameProvider) {
    this.path = path;
    this.nameProvider = nameProvider;
  }

  @NotNull
  @Override
  protected final Response act(@NotNull RqRegex req, @NotNull Locale locale) throws IOException {
    int id = Integer.parseInt(req.matcher().group("id"));
    if (nameProvider.hasName(id, locale)) {
      return new RsRedirect(
              String.format("/%s/%s/%s/%s", locale.toLanguageTag(), path, req.matcher().group("id"),
                            nameProvider.getName(id, locale)));
    } else {
      return new RsWithStatus(404);
    }
  }
}
