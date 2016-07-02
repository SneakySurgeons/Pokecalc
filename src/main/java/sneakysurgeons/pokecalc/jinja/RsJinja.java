package sneakysurgeons.pokecalc.jinja;

import com.google.common.io.Resources;
import com.hubspot.jinjava.Jinjava;
import org.jetbrains.annotations.NotNull;
import org.takes.Response;
import org.takes.rs.RsEmpty;
import org.takes.rs.RsWrap;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Request that uses Jinja templates.
 *
 * @author Peter Jeschke
 */
public class RsJinja extends RsWrap {

  /**
   * Ctor.
   *
   * @param template an URL that points to the template
   */
  public RsJinja(@NotNull final URL template) {
    this(template, new HashMap<>());
  }

  /**
   * Ctor.
   *
   * @param template an URL that points to the template
   * @param params   the context that is injected into the template
   */
  public RsJinja(@NotNull final URL template, @NotNull final Map<String, ?> params) {
    super(new Response() {
      @NotNull
      @Override
      public Iterable<String> head() throws IOException {
        return new RsEmpty().head();
      }

      @NotNull
      @Override
      public InputStream body() throws IOException {
        Jinjava jinjava = new Jinjava();
        return new ByteArrayInputStream(
                jinjava.render(Resources.toString(template, Charset.defaultCharset()), params).getBytes());
      }
    });
  }
}
