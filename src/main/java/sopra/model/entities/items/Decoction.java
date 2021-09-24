package sopra.model.entities.items;

import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sopra.controller.Config;
import sopra.controller.visitor.EntityVisitor;
import sopra.model.entities.Character;
import sopra.model.entities.Character.CharacterSkill;
import sopra.model.entities.EntityType;
import sopra.utils.JSONSerializable;


public class Decoction extends Stackable implements JSONSerializable<JSONObject> {

  private static final Logger LOGGER = LoggerFactory.getLogger(Decoction.class);
  private final String name;
  private int duration;
  private final CharacterSkill skill;


  public Decoction(final String name, final int stackSize, final int duration,
      final CharacterSkill skill) {
    super(EntityType.DECOCTION, stackSize);
    this.name = name;
    this.duration = duration;
    this.skill = skill;
  }


  public String getName() {
    return name;
  }

  public static Decoction fromJson(final JSONObject root) {
    final CharacterSkill c = switch (root.getString("skill")) {
      case "strength" -> Character.CharacterSkill.STRENGTH;
      case "vitality" -> Character.CharacterSkill.VITALITY;
      case "agility" -> Character.CharacterSkill.AGILITY;
      case "luck" -> Character.CharacterSkill.LUCK;
      default -> throw new JSONException("Unexpected value: " + root.getString("skill"));
    };
    return new Decoction(root.optString("name"),
        root.has("stackSize") ? root.getInt("stackSize") : 1,
        root.optInt("duration"),
        c);
  }

  @Override
  public <T> T accept(final EntityVisitor<T> visitor) {
    return visitor.visit(this);
  }

  public int getDuration() {
    return this.duration;
  }

  public Character.CharacterSkill getSkill() {
    return this.skill;
  }

  @Override
  protected boolean isStackable(final CharacterSkill skill) {
    return skill == this.skill;
  }

  @Override
  public boolean isStackable(final Item item) {

    if (item.getType() != EntityType.DECOCTION) {
      return false;
    }
    return item.getType() == EntityType.DECOCTION
        && ((Decoction) item).getDuration() == this.duration
        && ((Decoction) item).name.equals(this.name) && ((Decoction) item).getSkill() == this.skill;
  }

  @Override
  public Decoction spawn() {
    return new Decoction(this.name, Config.MIN_STACK, this.duration, this.skill);
  }


  /**
   * Reduces and disables decoction.
   */
  public void tick() {
    this.duration--;
    LOGGER.debug("tick {} -> {}", this.skill, this.duration);
    if (this.duration < 0) {
      this.disable();
    }
  }


  @Override
  public JSONObject toJSON() {
    return new JSONObject().putOpt("name", this.name).putOpt("stackSize", super.getStack())
        .putOpt("duration", this.duration)
        .put("skill", skill.toString().toLowerCase(Locale.ENGLISH));
  }
}