package sneakysurgeons.pokecalc;

import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;
import org.takes.Response;
import org.takes.facets.fork.RqRegex;
import sneakysurgeons.pokecalc.jinja.RsJinja;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Take that serves the index page.
 *
 * @author Peter Jeschke
 */
class TkLanding extends TkCommon {
  @NotNull
  @Override
  protected Response act(@NotNull RqRegex req, @NotNull Locale locale) throws IOException {
    return new RsJinja(getClass().getResource("/templates/index.html"),
                       ImmutableMap.of("locale", ResourceBundle.getBundle("messages", locale)));
  }
}
