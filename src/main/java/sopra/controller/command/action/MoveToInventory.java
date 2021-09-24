package sopra.controller.command.action;

import java.util.Optional;
import java.util.Queue;
import sopra.comm.Direction;
import sopra.comm.Observer;
import sopra.controller.Config;
import sopra.controller.visitor.MoveToInventoryVisitor;
import sopra.model.Coordinate;
import sopra.model.Tile;
import sopra.model.World;
import sopra.model.entities.Entity;
import sopra.model.entities.Player;


public class MoveToInventory extends Action {

  private final int index;
  private final Direction direction;

  public MoveToInventory(final int index, final Direction direction) {
    this.index = index - Config.INDEX_OFFSET;
    this.direction = direction;
  }

  @Override
  public void action(final Player player, final World world, final Queue<Observer> observers) {
    final Coordinate position = world.getPlayerPosition();
    final Coordinate destination = position.computeCoordinate(this.direction);
    final Optional<Tile> tile = world.getTile(destination);

    if (tile.isPresent() && tile.get().hasEntity()) {
      final Optional<Entity> optional = tile.get().getEntity();
      optional.ifPresent(
          entity -> entity.accept(new MoveToInventoryVisitor(player, this.index, observers)));

    } else {
      observers.forEach(observer -> observer
          .notifyCommandFailed("No item in this slot or item is not a recipe!"));
    }
  }
}
