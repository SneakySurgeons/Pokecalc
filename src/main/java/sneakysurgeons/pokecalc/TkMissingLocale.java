package sneakysurgeons.pokecalc;

import org.jetbrains.annotations.NotNull;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.forward.RsForward;

import java.io.IOException;
import java.util.Comparator;
import java.util.Locale;
import java.util.stream.StreamSupport;

/**
 * Take that forwards to a localized page.
 *
 * @author Peter Jeschke
 */
class TkMissingLocale implements Take {

  @NotNull
  @Override
  public Response act(@NotNull Request req) throws IOException {
    return new RsForward(acceptLanguage(req.head()));
  }

  /**
   * Filters the preferred accept language from the http headers.
   *
   * @param headerLines the lines of the http header as iterable
   * @return the preffered language or <tt>en</tt> if no preferred language is found
   */
  @NotNull
  private String acceptLanguage(@NotNull Iterable<String> headerLines) {
    return StreamSupport.stream(headerLines.spliterator(), false)
                        .filter(s -> s.startsWith("Accept-Language:"))
                        .map(this::parsePreferredLanguage)
                        .findFirst()
                        .orElse(Locale.ENGLISH.toLanguageTag());
  }

  /**
   * Parses the preferred language from the Accept-Language line.
   *
   * @param acceptLanguage the header line <tt>Accept-Language</tt>
   * @return the preferred language
   */
  @NotNull
  private String parsePreferredLanguage(@NotNull String acceptLanguage) {
    System.err.println(acceptLanguage);
    return Locale.LanguageRange.parse(acceptLanguage)
                               .stream()
                               .sorted(Comparator.comparing(Locale.LanguageRange::getWeight).reversed())
                               .map(Locale.LanguageRange::getRange)
                               .findFirst()
                               .orElse(Locale.ENGLISH.toLanguageTag());
  }
}
