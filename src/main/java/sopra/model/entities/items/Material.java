package sopra.model.entities.items;

import org.json.JSONException;
import org.json.JSONObject;
import sopra.controller.Config;
import sopra.controller.visitor.EntityVisitor;
import sopra.model.entities.EntityType;

public class Material extends Stackable {


  public Material(final EntityType type, final int stack) {
    super(type, stack);
  }

  public static Material fromJson(final JSONObject root) {
    return new Material(switch (root.getString("type")) {
      case "steel" -> EntityType.STEEL;
      case "wood" -> EntityType.WOOD;
      case "leather" -> EntityType.LEATHER;
      case "beetleshell" -> EntityType.BEETLESHELL;
      case "herbs" -> EntityType.HERBS;
      default -> throw new JSONException("Unexpected value: " + root.getString("type"));
    }, root.has("stackSize") ? root.getInt("stackSize") : 1);
  }

  private String toType(final EntityType entityType) {
    return switch (entityType) {
      case STEEL -> "steel";
      case WOOD -> "wood";
      case LEATHER -> "leather";
      case BEETLESHELL -> "beetleshell";
      case HERBS -> "herbs";
      default -> "";
    };
  }


  @Override
  public <T> T accept(final EntityVisitor<T> visitor) {
    return visitor.visit(this);
  }

  @Override
  public Item spawn() {
    return new Material(super.getType(), Config.MIN_STACK);
  }

  @Override
  public boolean isStackable(final Item item) {
    return item.getType() == super.getType();
  }

  @Override
  public JSONObject toJSON() {
    return new JSONObject().put("type", toType(super.getType()))
        .put("stackSize", super.getStack());
  }
}
