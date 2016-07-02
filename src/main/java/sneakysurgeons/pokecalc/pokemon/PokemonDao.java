package sneakysurgeons.pokecalc.pokemon;

import com.dieselpoint.norm.Database;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sneakysurgeons.pokecalc.NameProvider;
import sneakysurgeons.pokecalc.ResourceProvider;

import java.util.HashMap;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * The DAO that provides access to the pokemon.
 *
 * @author Peter Jeschke
 */
public class PokemonDao implements NameProvider, ResourceProvider<Pokemon> {

  private static final Logger logger = LoggerFactory.getLogger(PokemonDao.class);

  /**
   * I expect this not to change ;)
   */
  // TODO: 30.06.2016 Not exactly nice
  private static final int LANGUAGE_ID_ENGLISH = 9;
  private final Database database;

  public PokemonDao(Database database) {
    this.database = database;
  }

  @NotNull
  @Override
  public Optional<Pokemon> get(int id, @NotNull Locale locale) {
    logger.trace("Entering get[id=%d, Locale=%s]", id, locale);
    return database
            .where("species_id=?", id)
            .orderBy("id")
            .results(Pokemon.class)
            .stream()
            .findFirst();
  }

  @Override
  public boolean hasName(int id, @NotNull Locale locale) {
    logger.trace("Entering hasName[id=%d, Locale=%s]", id, locale);
    return !database.table("pokemon_species_name")
                    .where("pokemon_species_id=? AND local_language_id=?", id, getLanguageId(locale))
                    .results(HashMap.class).isEmpty();
  }

  @NotNull
  @Override
  public String getName(int id, @NotNull Locale locale) {
    logger.trace("Entering getName[id=%d, Locale=%s]", id, locale);
    return database.table("pokemon_species_names")
                   .where("pokemon_species_id=? AND local_language_id=?", id, getLanguageId(locale))
                   .results(HashMap.class)
                   .stream()
                   .map(m -> (String) m.get("name"))
                   .findFirst()
                   .orElseThrow(() -> new NoSuchElementException("No name found!"));
  }

  /**
   * Returns the language id for the locale or for en, if the locale wasn't found.
   */
  private int getLanguageId(@NotNull Locale locale) {
    logger.trace("Entering getLanguageId[Locale=%s]", locale);
    return database.table("languages")
                   .where("iso639=?", locale.toLanguageTag())
                   .results(HashMap.class)
                   .stream()
                   .map(m -> (int) m.get("id"))
                   .findFirst()
                   .orElse(LANGUAGE_ID_ENGLISH);
  }
}
