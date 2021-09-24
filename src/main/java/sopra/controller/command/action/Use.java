package sopra.controller.command.action;

import java.util.Optional;
import java.util.Queue;
import sopra.comm.Observer;
import sopra.controller.Config;
import sopra.controller.visitor.UseVisitor;
import sopra.model.World;
import sopra.model.entities.Player;
import sopra.model.entities.items.Item;
import sopra.utils.Utils;

public class Use extends Action {

  private final int index;

  public Use(final int index) {
    this.index = index - Config.INDEX_OFFSET;
  }

  @Override
  public void action(final Player player, final World world, final Queue<Observer> observers) {
    final Optional<Item> item = player.getItem(this.index);
    if (item.isPresent()) {
      item.get().accept(new UseVisitor(player, this.index, observers));
    } else {
      observers.forEach(observer -> observer.notifyCommandFailed("No item in this slot!"));
    }
  }

  @Override
  public String toString() {
    return Utils.substitute("USE({})", this.index);
  }

}
