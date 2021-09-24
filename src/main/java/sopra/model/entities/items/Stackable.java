package sopra.model.entities.items;

import org.json.JSONObject;
import sopra.model.entities.EntityType;
import sopra.utils.JSONSerializable;

public abstract class Stackable extends Item implements JSONSerializable<JSONObject> {

  private int stack;

  Stackable(final EntityType type, final int stack) {
    super(type);
    this.stack = stack;
  }

  public int getStack() {
    return this.stack;
  }

  public void stackToTrunk(final Item item, final int amount) {
    if (this.isStackable(item)) {
      this.stack += amount;
    }
  }

  @Override
  public boolean remove(final int amount) {
    this.stack -= amount;
    return this.stack <= 0;
  }

  @Override
  public void stack(final Item item) {
    if (this.isStackable(item)) {
      final Stackable t = (Stackable) item;
      this.stack += t.getStack();
    }
  }

  @Override
  public JSONObject toJSON() {
    return new JSONObject().put("stackSize", this.stack);
  }

}