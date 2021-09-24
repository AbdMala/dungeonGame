package sopra.controller.command;

import java.util.Optional;
import java.util.Queue;
import sopra.comm.Direction;
import sopra.comm.Observer;
import sopra.controller.visitor.TrunkVisitor;
import sopra.model.Coordinate;
import sopra.model.Tile;
import sopra.model.World;
import sopra.model.entities.Entity;
import sopra.model.entities.Player;

public class Trunk implements Command {

  Direction direction;

  public Trunk(final Direction direction) {
    this.direction = direction;
  }

  @Override
  public void execute(final World world, final Queue<Observer> observers) {
    final Coordinate position = world.getPlayerPosition();
    final Coordinate destination = position.computeCoordinate(this.direction);

    final Optional<Tile> tile = world.getTile(destination);
    final Player player = world.getPlayer();
    Command.calculateState(world, player);
    switch (player.getState()) {
      case REGISTER -> observers.forEach(Observer::notifyRegistrationAborted);
      case DEFAULT, ATTACKED, COMBAT -> {
        if (tile.isPresent() && tile.get().hasEntity()) {
          final Optional<Entity> optional = tile.get().getEntity();
          optional.ifPresent(entity -> entity.accept(new TrunkVisitor(observers)));
        }
      }
    }


  }

}
