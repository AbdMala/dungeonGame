package sopra.systemtest;

import static sopra.systemtest.api.Utils.loadResource;

import sopra.comm.Direction;
import sopra.comm.TimeoutException;
import sopra.systemtest.api.SystemTest;

public class SystemTestMoveIntoCauldron extends SystemTest {

  public SystemTestMoveIntoCauldron() {
    super(SystemTestMoveIntoCauldron.class, false);
  }

  @Override
  protected long createSeed() {
    return 0;
  }

  @Override
  protected String createWorld() {
    return loadResource(this.getClass(), "maps/default.json");
  }

  @Override
  protected void run() throws TimeoutException, AssertionError {
    this.sendRegister();
    this.assertGameStarted();

    this.assertSetCamera(5, -20, 15);

    this.assertDrawWorld("Island");

    // assert UpdateWorld-Event for every visible tile
    for (int i = 0; i < 37; i++) {
      this.assertEvent();
    }
    this.assertEvent();
    this.assertNextCycle(0);
    this.assertEvent();

    sendMove(Direction.WEST);

    assertEvent();
    assertEvent();

    sendLeave();
    assertGameEnd(false);

  }
}
