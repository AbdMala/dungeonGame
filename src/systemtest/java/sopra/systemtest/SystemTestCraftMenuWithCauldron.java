package sopra.systemtest;

import static sopra.systemtest.api.Utils.loadResource;

import sopra.comm.Direction;
import sopra.comm.TimeoutException;
import sopra.systemtest.api.SystemTest;

public class SystemTestCraftMenuWithCauldron extends SystemTest {

  public SystemTestCraftMenuWithCauldron() {
    super(SystemTestCraftMenuWithCauldron.class, false);
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

    sendPickUp(Direction.EAST);
    assertEvent();
    assertEvent();
    assertEvent();

    sendPickUp(Direction.NORTH_EAST);
    assertEvent();
    assertEvent();
    assertEvent();

    sendCraftingMenu(Direction.WEST);
    assertEvent();
    assertEvent();
    assertEvent();

    sendLeave();
    assertGameEnd(false);

  }
}