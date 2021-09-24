package sopra.model.entities.items;

import org.json.JSONObject;
import sopra.controller.Config;
import sopra.controller.visitor.EntityVisitor;
import sopra.model.entities.EntityType;
import sopra.utils.JSONSerializable;


public class Potion extends Stackable implements JSONSerializable<JSONObject> {

  private final String name;
  private final int level;
  private final int value;

  Potion(final String name, final int stackSize, final int level, final int value) {
    super(EntityType.POTION, stackSize);
    this.name = name;
    this.level = level;
    this.value = value;
  }


  public static Potion fromJson(final JSONObject root) {
    return new Potion(root.optString("name"), root.has("stackSize") ? root.getInt("stackSize") : 1,
        root.getInt("level"),
        root.getInt("value"));
  }


  @Override
  public <T> T accept(final EntityVisitor<T> visitor) {
    return visitor.visit(this);
  }

  public int getLevel() {
    return this.level;
  }

  public String getName() {
    return name;
  }

  public int getValue() {
    return this.value;
  }

  @Override
  public boolean isStackable(final Item item) {
    if (item.getType() != EntityType.POTION) {
      return false;
    }
    return item.getType() == EntityType.POTION && ((Potion) item).getLevel() == this.level
        && ((Potion) item).name.equals(this.name) && ((Potion) item).getValue() == this.value;
  }

  @Override
  public Potion spawn() {
    return new Potion(this.name, Config.MIN_STACK, this.level, this.value);
  }

  @Override
  public JSONObject toJSON() {
    return new JSONObject().putOpt("name", this.name).putOpt("stackSize", super.getStack())
        .put("level", this.level).putOpt("value", this.value);
  }
}
