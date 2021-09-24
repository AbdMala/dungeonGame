package sopra.model.entities.items;

import org.json.JSONObject;
import sopra.controller.visitor.EntityVisitor;
import sopra.model.entities.EntityType;
import sopra.utils.JSONSerializable;

public class Armor extends Item implements JSONSerializable<JSONObject> {

  private final int level;
  private final String name;
  private final int armor;
  private final String effect;

  Armor(final String name, final int level, final int armor, final String effect) {
    super(EntityType.ARMOR);
    this.level = level;
    this.name = name;
    this.armor = armor;
    this.effect = effect;
  }

  public static Armor fromJson(final JSONObject root) {
    return new Armor(root.getString("name"), root.getInt("level"), root.optInt("armor"),
        root.optString("effect", null));
  }

  public int getArmor() {
    return armor;
  }

  public String getEffect() {
    return effect;
  }

  @Override
  public <T> T accept(final EntityVisitor<T> visitor) {
    return visitor.visit(this);
  }

  public int getLevel() {
    return this.level;
  }

  public String getName() {
    return this.name;
  }

  /**
   * Computes the armor value.
   *
   * @return the armor value
   */
  public int getValue() {
    return this.armor;

  }

  @Override
  public JSONObject toJSON() {
    return new JSONObject().put("name", this.name).put("level", this.level)
        .putOpt("armor", this.armor)
        .putOpt("effect", this.effect);
  }
}
