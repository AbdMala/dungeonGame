package sopra.model.entities.craft;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.json.JSONArray;
import org.json.JSONObject;
import sopra.controller.Config;
import sopra.controller.ServerError;
import sopra.controller.visitor.EntityVisitor;
import sopra.model.entities.Entity;
import sopra.model.entities.EntityType;
import sopra.model.entities.Player;
import sopra.model.entities.items.Item;
import sopra.model.entities.items.Stackable;
import sopra.utils.JSONSerializable;

public class Trunk extends Entity implements JSONSerializable<JSONObject> {

  private final List<Optional<Item>> items;

  protected Trunk() {
    super(EntityType.TRUNK);
    this.items = new ArrayList<>();
    IntStream.range(0, Config.INVENTORY_SIZE).forEach(i -> this.items.add(i, Optional.empty()));
  }

  public static Trunk fromJson() {
    return new Trunk();
  }

  public boolean add(final Item item) {
    for (final Optional<Item> slot : this.items) {
      if (slot.isPresent() && slot.get().isStackable(item)) {
        final Stackable tmp = (Stackable) item;
        final Stackable tmpSlot = (Stackable) slot.get();
        tmpSlot.stackToTrunk(tmp, tmp.getStack());
        return true;
      }
    }
    for (int i = 0; i < this.items.size(); i++) {
      final Optional<Item> slot = this.items.get(i);
      if (slot.isEmpty()) {
        this.items.set(i, Optional.of(item));
        return true;
      }
    }
    return false;
  }

  public void remove(final int index) {
    this.items.set(index, Optional.empty());
  }

  public List<Optional<Item>> getItems() {
    return items;
  }

  public boolean moveToInventory(final Player player, final int index) {
    if (this.items.get(index).isPresent()) {
      if (!this.items.get(index).get().isStackable(this.items.get(index).get())) {
        return player.addItem(this.items.get(index).get());
      } else {
        final Stackable tmp = (Stackable) this.items.get(index).get();
        if (tmp.getStack() < 1) {
          return false;
        } else {
          return player.addItem(tmp);
        }
      }
    } else {
      return false;
    }
  }

  @Override
  public <T> T accept(final EntityVisitor<T> visitor) {
    return visitor.visit(this);
  }


  @Override
  public JSONObject toJSON() {
    final JSONObject root = new JSONObject();
    final JSONArray items = new JSONArray();
    for (final Optional<Item> optional : this.items) {
      final JSONObject item = new JSONObject();
      if (optional.isPresent()) {
        final String type = optional.orElseThrow().getType().toJSON();
        item.put("objectType", type);
        item.put(type, optional.orElseThrow(ServerError::new).toJSON());
      } else {
        item.put("objectType", "empty");
      }
      items.put(item);
    }
    return root.put("listType", "trunk").put("trunk", items);
  }

}
