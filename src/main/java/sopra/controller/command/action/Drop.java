package sopra.controller.command.action;

import java.util.Optional;
import java.util.Queue;
import sopra.comm.Direction;
import sopra.comm.Observer;
import sopra.controller.Config;
import sopra.controller.ServerError;
import sopra.controller.visitor.DropVisitor;
import sopra.model.Coordinate;
import sopra.model.Tile;
import sopra.model.World;
import sopra.model.entities.Entity;
import sopra.model.entities.EntityType;
import sopra.model.entities.Player;
import sopra.model.entities.items.Item;
import sopra.utils.Utils;


public class Drop extends Action {

  private final Direction direction;
  private final int index;

  public Drop(final int index, final Direction direction) {
    this.index = index - Config.INDEX_OFFSET;
    this.direction = direction;
  }

  @Override
  public void action(final Player player, final World world, final Queue<Observer> observers) {
    final Optional<Item> slot = player.getItem(this.index);
    if (slot.isPresent()) {
      final Coordinate coordinate = world.getPlayerPosition().computeCoordinate(this.direction,
          Config.DROP_DISTANCE);
      final Tile tile = world.getTile(coordinate).orElseThrow(ServerError::new);
      if (world.isAccessible(EntityType.PLAYER, coordinate)) {
        final Item item = slot.get().spawn();
        item.setPosition(coordinate);
        tile.setEntity(item);
        player.removeItem(this.index);
        observers.forEach(observer -> observer.notifyUpdateWorld(tile));
      } else {
        final Optional<Entity> optional = tile.getEntity();
        optional
            .ifPresent(
                entity -> entity.accept(new DropVisitor(player, this.index, observers)));
      }
    } else {
      observers.forEach(observer -> observer.notifyCommandFailed("No item in this slot!"));
    }
  }

  @Override
  public String toString() {
    return Utils.substitute("DROP({}, {})", this.index, this.direction);
  }
}
