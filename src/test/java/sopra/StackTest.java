package sopra;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sopra.model.entities.Character.CharacterSkill;
import sopra.model.entities.EntityType;
import sopra.model.entities.craft.Trunk;
import sopra.model.entities.items.Decoction;
import sopra.model.entities.items.Item.ItemFactory;
import sopra.model.entities.items.Jewel;
import sopra.model.entities.items.Material;
import sopra.model.entities.items.Potion;
import sopra.model.entities.items.Stackable;


public class StackTest {


  @Test
  public void stackRuby() {
    final Jewel j1 = ItemFactory.creatJewel(EntityType.RUBY, 1);
    final Jewel j2 = ItemFactory.creatJewel(EntityType.RUBY, 1);
    final Jewel j3 = ItemFactory.creatJewel(EntityType.EMERALD, 1);
    Assertions.assertTrue(j1.isStackable(j2));
    Assertions.assertFalse(j1.isStackable(j3));
    Assertions.assertEquals(1, j1.getStack());
    j1.stack(j2);
    Assertions.assertEquals(2, j1.getStack());
    j2.stack(j3);
    Assertions.assertEquals(1, j2.getStack());


  }

  @Test
  public void removeTest() {
    final Jewel j1 = ItemFactory.creatJewel(EntityType.DIAMOND, 2);
    Assertions.assertEquals(2, j1.getStack());
    j1.remove(1);
    Assertions.assertEquals(1, j1.getStack());
  }

  @Test
  public void sendToTrunkTest() {
    final Trunk trunk = Trunk.fromJson();
    final Jewel j1 = ItemFactory.creatJewel(EntityType.DIAMOND, 1);
    final Jewel j2 = ItemFactory.creatJewel(EntityType.DIAMOND, 2);
    trunk.add(j1);
    trunk.add(j2);
    final Stackable item = (Stackable) trunk.getItems().get(0).get();
    final int x = item.getStack();
    Assertions.assertEquals(3, x);

  }

  @Test
  public void isStackableTest() {
    final Jewel j1 = ItemFactory.creatJewel(EntityType.DIAMOND, 1);
    final Jewel j2 = ItemFactory.creatJewel(EntityType.DIAMOND, 2);
    Assertions.assertTrue(j1.isStackable(j2));

    final Decoction d1 = ItemFactory.createDecoction("decoction", 2, 10, CharacterSkill.AGILITY);
    final Decoction d2 = ItemFactory.createDecoction("decoction", 1, 10, CharacterSkill.AGILITY);
    final Decoction d3 = ItemFactory
        .createDecoction("smalldecoction", 1, 10, CharacterSkill.AGILITY);

    Assertions.assertTrue(d1.isStackable(d2));
    Assertions.assertFalse(d1.isStackable(d3));

  }

  @Test
  public void isStackable2Test() {
    final Potion p1 = ItemFactory.createPotion("potion", 1, 1, 5);
    final Decoction d1 = ItemFactory.createDecoction("decoction", 2, 10, CharacterSkill.AGILITY);
    final Material m1 = ItemFactory.creatMaterial(EntityType.LEATHER, 3);
    final Jewel j2 = ItemFactory.creatJewel(EntityType.DIAMOND, 2);
    Assertions.assertFalse(p1.isStackable(d1));
    Assertions.assertFalse(d1.isStackable(p1));
    Assertions.assertFalse(p1.isStackable(m1));
    Assertions.assertFalse(m1.isStackable(p1));
    Assertions.assertFalse(j2.isStackable(p1));
    Assertions.assertFalse(p1.isStackable(j2));
    Assertions.assertFalse(m1.isStackable(j2));
    Assertions.assertFalse(j2.isStackable(m1));
  }
}
