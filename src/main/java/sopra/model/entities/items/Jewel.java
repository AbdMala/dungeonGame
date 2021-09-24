package sopra.model.entities.items;

import org.json.JSONException;
import org.json.JSONObject;
import sopra.controller.Config;
import sopra.controller.visitor.EntityVisitor;
import sopra.model.entities.EntityType;

public class Jewel extends Stackable {

  Jewel(final EntityType type, final int stack) {
    super(type, stack);
  }

  public static Jewel fromJson(final JSONObject root) {
    return new Jewel(switch (root.getString("type")) {
      case "ruby" -> EntityType.RUBY;
      case "amethyst" -> EntityType.AMETHYST;
      case "emerald" -> EntityType.EMERALD;
      case "diamond" -> EntityType.DIAMOND;
      default -> throw new JSONException("Unexpected value: " + root.getString("type"));
    }, root.has("stackSize") ? root.getInt("stackSize") : 1);
  }

  private String toType(final EntityType entityType) {
    return switch (entityType) {
      case RUBY -> "ruby";
      case AMETHYST -> "amethyst";
      case EMERALD -> "emerald";
      case DIAMOND -> "diamond";
      default -> "";
    };
  }


  @Override
  public <T> T accept(final EntityVisitor<T> visitor) {
    return visitor.visit(this);
  }

  @Override
  public boolean isStackable(final Item item) {
    return item.getType() == super.getType();
  }


  @Override
  public Item spawn() {
    return new Jewel(super.getType(), Config.MIN_STACK);
  }

  @Override
  public JSONObject toJSON() {
    return new JSONObject().put("type", toType(super.getType()))
        .put("stackSize", super.getStack());
  }

}
