package sopra.controller.visitor;

import java.util.Queue;
import org.json.JSONObject;
import sopra.comm.Observer;
import sopra.model.World;
import sopra.model.entities.EntityType;
import sopra.model.entities.craft.Cauldron;
import sopra.model.entities.craft.Table;

public class CraftingMenuVisitor extends DefaultEntityVisitor {

  private final Queue<Observer> observers;
  private final World world;

  public CraftingMenuVisitor(final World world, final Queue<Observer> observers) {
    this.world = world;
    this.observers = observers;
  }

  @Override
  protected void handle(final Table table) {
    if (table.addRecipes(world.getPlayer(), EntityType.WEAPON, EntityType.ARMOR)) {
      final JSONObject json = table.toJSON();
      observers.forEach(observer -> observer.notifyChoiceWindow(json.toString()));
    }
  }

  @Override
  protected void handle(final Cauldron cauldron) {
    if (cauldron.addRecipes(world.getPlayer(), EntityType.POTION, EntityType.DECOCTION)) {
      final JSONObject json = cauldron.toJSON();
      observers.forEach(observer -> observer.notifyChoiceWindow(json.toString()));
    }
  }
}
