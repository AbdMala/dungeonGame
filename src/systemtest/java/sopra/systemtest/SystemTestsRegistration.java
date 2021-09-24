package sopra.systemtest;

import java.util.Objects;
import sopra.systemtest.api.SystemTestManager;

final class SystemTestsRegistration {

  public static void registerSystemTests(final SystemTestManager manager) {
    assert Objects.nonNull(manager);

    manager.registerTest(new SystemTestPickup());

    manager.registerTest(new SystemTestCraftMenuWithTable());
    manager.registerTest(new SystemTestCraftWeapon());
    manager.registerTest(new SystemTestCraftArmor());

    manager.registerTest(new SystemTestCraftMenuWithCauldron());
    manager.registerTest(new SystemTestCraftPotion());

    manager.registerTest(new SystemTestTrunk());
    manager.registerTest(new SystemTestMoveToInventory());
    manager.registerTest(new SystemTestDropInTrunk());

    manager.registerTest(new SystemTestAttackTable());
    manager.registerTest(new SystemTestPickUpCauldron());
    manager.registerTest(new SystemTestMoveIntoCauldron());
    manager.registerTest(new SystemTestUseRecipe());

  }
}
