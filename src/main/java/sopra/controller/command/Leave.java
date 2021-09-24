package sopra.controller.command;

import java.util.Queue;
import sopra.comm.Observer;
import sopra.model.World;

public class Leave implements Command {

  @Override
  public void execute(final World world, final Queue<Observer> observers) {
    world.loose();
  }

  @Override
  public String toString() {
    return "LEAVE()";
  }
}
