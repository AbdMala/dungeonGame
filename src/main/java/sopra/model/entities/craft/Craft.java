package sopra.model.entities.craft;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import sopra.controller.Config;
import sopra.model.entities.Entity;
import sopra.model.entities.EntityType;
import sopra.model.entities.Player;
import sopra.model.entities.items.Item;
import sopra.model.entities.items.Recipe;
import sopra.model.entities.items.Stackable;
import sopra.utils.JSONSerializable;

public abstract class Craft extends Entity implements JSONSerializable<JSONObject> {

  private final List<Recipe> recipes = new ArrayList<>();
  private final List<Recipe> tmpRecipes = new ArrayList<>();

  protected Craft(final EntityType type) {
    super(type);
  }

  public boolean addRecipes(final Player player, final EntityType a, final EntityType b) {
    for (int i = 0; i < Config.INVENTORY_SIZE; i++) {
      if (player.getItem(i).isPresent() && player.getItem(i).get().getType() == EntityType.RECIPE) {
        final var x = (Recipe) (player.getItem(i).get());
        if (x.getRecipeType() == a || x.getRecipeType() == b) {
          tmpRecipes.add(x);
        }
      }
    }
    for (final var r : tmpRecipes) {
      if (isReady(player, r)) {
        recipes.add(r);
      }
    }
    return true;
  }

  public void deleteElements(final Player player, final Recipe recipe) {
    final Map<EntityType, Integer> materials = recipe.getElements();
    for (final Map.Entry<EntityType, Integer> entry : materials.entrySet()) {
      final EntityType key = entry.getKey();
      final int value = entry.getValue();
      if (value > 0) {
        for (int i = 0; i < Config.INVENTORY_SIZE; i++) {
          if (player.getItem(i).isEmpty()) {
            continue;
          }
          final Item del3 = player.getItem(i).get();

          if (del3.getType() == key) {
            player.removeItem(i, value);
          }
        }
      }
    }
  }

  public boolean isReady(final Player player, final Recipe recipe) {
    final Map<EntityType, Integer> materials = recipe.getElements();

    for (final Map.Entry<EntityType, Integer> entry : materials.entrySet()) {

      final EntityType key = entry.getKey();
      final int value = entry.getValue();
      if (value <= 0) {
        continue;
      }
      boolean found = false;
      for (int i = 0; i < Config.INVENTORY_SIZE; i++) {
        if (player.getItem(i).isEmpty()) {
          continue;
        }
        final Item temp = player.getItem(i).get();

        if (temp.getType() == key) {
          final Stackable temp2 = (Stackable) temp;
          if (temp2.getStack() >= value) {
            found = true;
          } else {
            return false;
          }
        }
      }
      if (!found) {
        return false;
      }
    }
    return true;
  }

  @Override
  public JSONObject toJSON() {
    final JSONObject root = new JSONObject();
    final JSONArray items = new JSONArray();
    for (final Recipe optional : this.recipes) {
      final JSONObject item = new JSONObject();
      item.put("objectType", "recipe");
      item.put("recipe", optional.toJSON());
      items.put(item);
    }
    return root.put("listType", "craft").put("craft", items);
  }


}
