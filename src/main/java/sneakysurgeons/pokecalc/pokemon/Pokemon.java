package sneakysurgeons.pokecalc.pokemon;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Model for a pokemon. <p> This class is not fit for adding or updating values. </p>
 *
 * @author Peter Jeschke
 */
@SuppressWarnings({"WeakerAccess", "unused"}) // Has to be public to support the orm features
@Table(name = "pokemon")
public class Pokemon {
  @Id
  public long id;
}
