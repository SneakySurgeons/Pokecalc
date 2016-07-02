package sneakysurgeons.pokecalc;

import org.takes.HttpException;
import org.takes.rq.RqHref;
import org.takes.rs.RsWithBody;
import org.takes.tk.TkWrap;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A take that tries to resolve a resource by its path.
 *
 * @author Peter Jeschke
 */
class TkResource extends TkWrap {
  TkResource() {
    super(req -> {
      try {
        URL resource = ResourceTool.getResource(new RqHref.Base(req).href().path());
        if (resource == null) {
          throw new HttpException(HttpURLConnection.HTTP_NOT_FOUND);
        }
        InputStream inputStream = resource.openStream();
        return new RsWithBody(inputStream);
      } catch (FileNotFoundException exc) {
        throw new HttpException(HttpURLConnection.HTTP_NOT_FOUND);
      }
    });
  }
}
