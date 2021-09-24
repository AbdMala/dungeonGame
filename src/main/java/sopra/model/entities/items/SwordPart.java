package sopra.model.entities.items;

import sopra.controller.Config;
import sopra.controller.visitor.EntityVisitor;
import sopra.model.entities.EntityType;


public class SwordPart extends Stackable {

  SwordPart() {
    super(EntityType.SWORD_PART, Config.MIN_STACK);
  }

  public static SwordPart fromJson() {
    return new SwordPart();
  }



  @Override
  public <T> T accept(final EntityVisitor<T> visitor) {
    return visitor.visit(this);
  }

  @Override
  public boolean isLegendary() {
    return true;
  }

  @Override
  public boolean isStackable(final Item item) {
    return item.getType() == EntityType.SWORD_PART;
  }

  @Override
  public SwordPart spawn() {
    return new SwordPart();
  }
}
