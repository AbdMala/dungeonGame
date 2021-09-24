package sopra.comm;

import sopra.model.Coordinate;
import sopra.model.Tile;
import sopra.model.World;
import sopra.model.entities.Player;


public interface Observer {

  void notifyActNow();

  void notifyChoiceWindow(String json);

  void notifyCommandFailed(String message);

  void notifyDrawWorld(World world);

  void notifyGameEnd(boolean win);

  void notifyGameStarted();

  void notifyMessage(String message);

  void notifyNextCycle(int cycle);

  void notifyRegistrationAborted();

  void notifySetCamera(Coordinate coordinate);

  void notifyUpdatePlayer(Player player);

  void notifyUpdateWorld(Tile tile);
}
