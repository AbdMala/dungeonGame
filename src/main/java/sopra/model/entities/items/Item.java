package sopra.model.entities.items;

import org.json.JSONObject;
import sopra.controller.Config;
import sopra.model.World;
import sopra.model.entities.Character.CharacterSkill;
import sopra.model.entities.Entity;
import sopra.model.entities.EntityType;
import sopra.utils.JSONSerializable;

public abstract class Item extends Entity implements JSONSerializable<JSONObject> {

  protected Item(final EntityType type) {
    super(type);
  }

  protected boolean isStackable(final CharacterSkill skill) {
    return false;
  }

  public boolean isStackable(final Item item) {
    return false;
  }

  public boolean remove(final int amount) {
    return true;
  }

  public Item spawn() { // override in stackable
    return this;
  }

  public void stack(final Item item) {
    throw new UnsupportedOperationException("Normal items cannot be stacked!");
  }

  @Override
  public abstract JSONObject toJSON();

  public static final class ItemFactory {


    private ItemFactory() {
      // empty
    }


    public static Armor createArmor(final String name, final int level,
        final int armor, final String effect) {
      return new Armor(name, level, armor, effect);
    }

    public static Decoction createDecoction(final String name, final int stackSize,
        final int duration,
        final CharacterSkill skill) {
      return new Decoction(name, stackSize, duration, skill);
    }

    public static Potion createPotion(final String name, final int stackSize,
        final int level, final int value) {
      return new Potion(name, stackSize, level, value);
    }

    public static Weapon createSOPRASword() {
      return new Weapon("sword", Config.SOPRA_SWORD, 20, 1, null);
    }

    public static Weapon createSword(final int level) {
      return new Weapon("sword", level, 1, 1, null);
    }

    public static SwordPart createSwordPart() {
      return new SwordPart();
    }

    public static Weapon createWeapon(final String name, final int level, final int damage,
        final int range, final String effect) {
      return new Weapon(name, level, damage, range, effect);
    }

    public static Material creatMaterial(final EntityType type, final int stackSize) {
      return new Material(type, stackSize);
    }

    public static Jewel creatJewel(final EntityType type, final int stackSize) {
      return new Jewel(type, stackSize);
    }

    public static Recipe creatRecipe(final World world, final int level) {
      return Recipe.generate(world, level);
    }
  }
}
