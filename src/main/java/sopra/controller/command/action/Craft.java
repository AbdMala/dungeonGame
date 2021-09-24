package sopra.controller.command.action;

import java.util.Optional;
import java.util.Queue;
import sopra.comm.Direction;
import sopra.comm.Observer;
import sopra.controller.Config;
import sopra.controller.visitor.CraftVisitor;
import sopra.model.Coordinate;
import sopra.model.Tile;
import sopra.model.World;
import sopra.model.entities.Entity;
import sopra.model.entities.EntityType;
import sopra.model.entities.Player;
import sopra.model.entities.items.Item;


public class Craft extends Action {

  private final int index;
  private final Direction direction;

  public Craft(final int index, final Direction direction) {
    this.index = index - Config.INDEX_OFFSET;
    this.direction = direction;

  }

  @Override
  public void action(final Player player, final World world, final Queue<Observer> observers) {
    final Coordinate position = world.getPlayerPosition();
    final Coordinate destination = position.computeCoordinate(this.direction);
    final Optional<Tile> tile = world.getTile(destination);
    final Optional<Item> item = player.getItem(this.index);
    if (item.isPresent() && item.get().getType() == EntityType.RECIPE
        && tile.isPresent() && tile.get().hasEntity()) {
      final Optional<Entity> optional = tile.get().getEntity();
      optional.ifPresent(entity -> entity.accept(new CraftVisitor(player, this.index, observers)));

    } else {
      observers.forEach(observer -> observer
          .notifyCommandFailed("No item in this slot or item is not a recipe!"));
    }
  }
}
