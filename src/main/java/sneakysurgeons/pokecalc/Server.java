package sneakysurgeons.pokecalc;

import com.dieselpoint.norm.Database;
import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlite.JDBC;
import org.sqlite.SQLiteDataSource;
import org.takes.facets.fallback.FbChain;
import org.takes.facets.fallback.FbStatus;
import org.takes.facets.fallback.TkFallback;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.http.Exit;
import org.takes.http.FtBasic;
import org.takes.misc.Opt;
import org.takes.tk.TkWithType;
import sneakysurgeons.pokecalc.jinja.RsJinja;
import sneakysurgeons.pokecalc.pokemon.PokemonDao;
import sneakysurgeons.pokecalc.pokemon.TkPokemonDetail;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Creates the Server
 *
 * @author Peter-René Jeschke
 */
class Server {

  private static final Logger logger = LoggerFactory.getLogger(Server.class);
  private static final String SQLITE_URL = System.getProperty("SQLITE_PATH", "pokedex.sqlite");
  private static final int PORT = Integer.parseInt(System.getProperty("HTTP_PORT", "8080"));

  public static void main(String[] args) throws IOException {
    logger.debug("Starting Server.");

    Database database = new Database() {
      @Override
      protected DataSource getDataSource() throws SQLException {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(JDBC.PREFIX + SQLITE_URL);
        return dataSource;
      }
    };

    PokemonDao pokemonDao = new PokemonDao(database);
    new FtBasic(new TkFallback(
            new TkFork(new FkRegex("/", new TkMissingLocale()),
                       new FkRegex("/(?<locale>[^/]+)", new TkLanding()),
                       new FkRegex(resourceIndex("pokemon"),
                                   new TkSimple(ResourceTool.getResource("/templates/pokemon_index.html"))),
                       new FkRegex(resourceRedirect("pokemon"), new TkCommonMissingName("pokemon", pokemonDao)),
                       new FkRegex(resourceDetail("pokemon"), new TkPokemonDetail(pokemonDao)),
                       new FkRegex("/css/.+", new TkWithType(new TkResource(), "text/css")),
                       new FkRegex("/js/.+", new TkWithType(new TkResource(), "application/javascript"))
            ),
            new FbChain(new FbStatus(404, new RsJinja(ResourceTool.getResource("/templates/404.html"))),
                        req -> {
                          logger.error("An unknown error was logged in fallback.", req.throwable());
                          return new Opt.Single<>(new RsJinja(ResourceTool.getResource("/templates/error.html"),
                                                              ImmutableMap.of("req", req)));
                        }))
            , PORT).start(Exit.NEVER);
  }

  @NotNull
  private static String resourceIndex(@NotNull String type) {
    return "/(?<locale>[^/]+)/" + type;
  }

  @NotNull
  private static String resourceRedirect(@NotNull String type) {
    return "/(?<locale>[^/]+)/" + type + "/(?<id>[^/]+)";
  }

  @NotNull
  private static String resourceDetail(@NotNull String type) {
    return "/(?<locale>[^/]+)/" + type + "/(?<id>[^/]+)/(?<name>[^/]+)";
  }
}
