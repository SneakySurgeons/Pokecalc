package sneakysurgeons.pokecalc;

import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;
import org.takes.Response;
import org.takes.facets.fork.RqRegex;
import sneakysurgeons.pokecalc.jinja.RsJinja;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * A simple Take that returns a page without resources (except the localization).
 *
 * @author Peter Jeschke
 */
public class TkSimple extends TkCommon {

  @NotNull
  private final URL resource;

  public TkSimple(@NotNull final URL resource) {
    this.resource = resource;
  }

  @NotNull
  @Override
  protected Response act(@NotNull RqRegex req, @NotNull Locale locale) throws IOException {
    return new RsJinja(resource, ImmutableMap.of("messages", ResourceBundle.getBundle("messages", locale)));
  }
}
