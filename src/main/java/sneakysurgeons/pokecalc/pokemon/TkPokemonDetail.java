package sneakysurgeons.pokecalc.pokemon;

import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;
import org.takes.HttpException;
import org.takes.Response;
import org.takes.facets.fork.RqRegex;
import org.takes.rs.RsRedirect;
import sneakysurgeons.pokecalc.ResourceTool;
import sneakysurgeons.pokecalc.TkCommonDetail;
import sneakysurgeons.pokecalc.jinja.RsJinja;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The take that will show the details of a pokemon. Also has a check that will redirect to the correct name if the
 * wrong name was supplied in the url.
 *
 * @author Peter Jeschke
 */
public class TkPokemonDetail extends TkCommonDetail {
  /**
   * The DAO that can be used to access the pokemon.
   */
  @NotNull
  private final PokemonDao dao;

  public TkPokemonDetail(@NotNull PokemonDao dao) {
    this.dao = dao;
  }

  @NotNull
  @Override
  protected Response act(@NotNull RqRegex req, @NotNull Locale locale, int id, @NotNull String name)
          throws IOException {
    Optional<Pokemon> poke = dao.get(id, locale);

    if (!poke.isPresent()) {
      throw new HttpException(HttpURLConnection.HTTP_NOT_FOUND);
    }
    String pokemonName = dao.getName(id, locale);
    if (pokemonName.equals(name)) {
      return new RsJinja(ResourceTool.getResource("/templates/pokemon_detail.html"), ImmutableMap.of(
              "pokemon", poke,
              "name", pokemonName,
              "messages", ResourceBundle.getBundle("messages", locale)));
    }
    return new RsRedirect(String.format("/%s/pokemon/%s/%s", locale.toLanguageTag(), id, pokemonName));
  }
}
