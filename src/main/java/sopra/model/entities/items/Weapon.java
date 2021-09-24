package sopra.model.entities.items;

import org.json.JSONObject;
import sopra.controller.Config;
import sopra.controller.visitor.EntityVisitor;
import sopra.model.entities.EntityType;
import sopra.utils.JSONSerializable;


public class Weapon extends Item implements JSONSerializable<JSONObject> {

  private final int level;
  private final String name;
  private final int damage;
  private final int range;
  private final String effect;

  Weapon(final String name, final int level, final int damage, final int range,
      final String effect) {
    super(EntityType.WEAPON);
    this.name = name;
    this.level = level;
    this.damage = damage;
    this.range = range;
    this.effect = effect;
  }

  public static Weapon fromJson(final JSONObject root) {
    return new Weapon(root.getString("name"), root.getInt("level"), root.optInt("damage"),
        root.getInt("range"), root.optString("effect", null));
  }

  @Override
  public <T> T accept(final EntityVisitor<T> visitor) {
    return visitor.visit(this);
  }

  @Override
  public boolean isLegendary() {
    return this.level == Config.SOPRA_SWORD;
  }

  public int getLevel() {
    return this.level;
  }

  public String getName() {
    return this.name;
  }

  public int getRange() {
    return this.range;
  }

  /**
   * Computes the value of the weapon.
   *
   * @return the value
   */
  public int getValue() {
    return this.damage;
  }

  public String getEffect() {
    return effect;
  }

  public int getDamage() {
    return damage;
  }

  @Override
  public JSONObject toJSON() {
    return new JSONObject().put("name", this.name).put("level", this.level)
        .put("damage", this.damage)
        .put("range", this.range).putOpt("effect", this.effect);
  }
}
