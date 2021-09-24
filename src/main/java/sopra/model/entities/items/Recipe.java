package sopra.model.entities.items;


import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import sopra.controller.Config;
import sopra.controller.ServerError;
import sopra.controller.visitor.EntityVisitor;
import sopra.model.World;
import sopra.model.entities.Character.CharacterSkill;
import sopra.model.entities.EntityType;
import sopra.utils.JSONSerializable;

public class Recipe extends Item implements JSONSerializable<JSONObject> {


  private final EntityType recipeType;
  private final Item item;
  private final int steel;
  private final int wood;
  private final int leather;
  private final int beetleshell;
  private final int herbs;
  private final int ruby;
  private final int amethyst;
  private final int emerald;
  private final int diamond;

  protected Recipe(final Item item, final int steel, final int wood, final int leather,
      final int beetleshell,
      final int herbs, final int ruby, final int amethyst, final int emerald, final int diamond) {
    super(EntityType.RECIPE);
    this.item = item;
    this.recipeType = item.getType();
    this.steel = steel;
    this.wood = wood;
    this.leather = leather;
    this.beetleshell = beetleshell;
    this.herbs = herbs;
    this.ruby = ruby;
    this.amethyst = amethyst;
    this.emerald = emerald;
    this.diamond = diamond;
  }

  public Map<EntityType, Integer> getElements() {
    final EnumMap<EntityType, Integer> elements = new EnumMap<>(EntityType.class);
    elements.put(EntityType.STEEL, steel);
    elements.put(EntityType.WOOD, wood);
    elements.put(EntityType.LEATHER, leather);
    elements.put(EntityType.BEETLESHELL, beetleshell);
    elements.put(EntityType.HERBS, herbs);
    elements.put(EntityType.RUBY, ruby);
    elements.put(EntityType.AMETHYST, amethyst);
    elements.put(EntityType.EMERALD, emerald);
    elements.put(EntityType.DIAMOND, diamond);
    return elements;
  }

  public static Recipe fromJson(final JSONObject root) {

    return new Recipe(switch (root.getString("recipeType")) {
      case "weapon" -> Weapon.fromJson(root.getJSONObject("weapon"));
      case "armor" -> Armor.fromJson(root.getJSONObject("armor"));
      case "potion" -> Potion.fromJson(root.getJSONObject("potion"));
      case "decoction" -> Decoction.fromJson(root.getJSONObject("decoction"));
      default -> throw new JSONException("Unexpected value: " + root.getString("recipeType"));

    }, root.has("steel") ? root.getInt("steel") : 0, root.has("wood") ? root.getInt("wood") : 0,
        root.has("leather") ? root.getInt("leather") : 0,
        root.has("beetleshell") ? root.getInt("beetleshell") : 0,
        root.has("herbs") ? root.getInt("herbs") : 0, root.has("ruby") ? root.getInt("ruby") : 0,
        root.has("amethyst") ? root.getInt("amethyst") : 0,
        root.has("emerald") ? root.getInt("emerald") : 0,
        root.has("diamond") ? root.getInt("diamond") : 0);
  }


  public EntityType getRecipeType() {
    return recipeType;

  }

  public Item getItem() {
    return item;
  }


  private String toType(final EntityType entityType) {
    switch (entityType) {
      case WEAPON:
        return "weapon";
      case ARMOR:
        return "armor";
      case POTION:
        return "potion";
      case DECOCTION:
        return "decoction";
      default:
        break;
    }
    return "";
  }

  @Override
  public JSONObject toJSON() {
    final JSONObject root = new JSONObject();
    root.put("recipeType", toType(this.recipeType));
    switch (this.recipeType) {
      case WEAPON:
        final Weapon w = (Weapon) this.item;
        root.put("weapon", w.toJSON());
        break;
      case ARMOR:
        final Armor a = (Armor) this.item;
        root.put("armor", a.toJSON());
        break;
      case POTION:
        final Potion p = (Potion) this.item;
        root.put("potion", p.toJSON());
        break;
      case DECOCTION:
        final Decoction d = (Decoction) this.item;
        root.put("decoction", d.toJSON());
        break;
      default:
        break;
    }
    root.put("steel", this.steel);
    root.put("wood", this.wood);
    root.put("leather", this.leather);
    root.put("beetleshell", this.beetleshell);
    root.put("herbs", this.herbs);
    root.put("ruby", this.ruby);
    root.put("amethyst", this.amethyst);
    root.put("emerald", this.emerald);
    root.put("diamond", this.diamond);
    return root;

  }

  @Override
  public <T> T accept(final EntityVisitor<T> visitor) {
    return visitor.visit(this);
  }


  private static int getWeaponBasicDamage(final int level) {
    return switch (level) {
      case 1 -> 1;
      case 2 -> 2;
      case 3 -> 4;
      case 4 -> 6;
      case 5 -> 8;
      case 6 -> 10;
      case 7 -> 12;
      case 8 -> 14;
      case 9 -> 16;
      case 10 -> 18;
      case 42 -> 20;
      default -> throw new ServerError();
    };
  }

  private static int getArmorValue(final int level) {

    return switch (level) {
      case 1 -> 1;
      case 2 -> 2;
      case 3 -> 5;
      case 4 -> 9;
      case 5 -> 14;
      case 6 -> 20;
      case 7 -> 27;
      case 8 -> 35;
      case 9 -> 40;
      case 10 -> 45;
      default -> throw new ServerError();
    };
  }

  private static int getPotionValue(final int level) {
    return level * 5;
  }


  public static Recipe generate(final World world, final int level) {
    final HashMap<EntityType, Integer> values = new HashMap<>();
    values.put(EntityType.STEEL, 0);
    values.put(EntityType.WOOD, 0);
    values.put(EntityType.LEATHER, 0);
    values.put(EntityType.BEETLESHELL, 0);
    values.put(EntityType.HERBS, 0);
    values.put(EntityType.RUBY, 0);
    values.put(EntityType.AMETHYST, 0);
    values.put(EntityType.EMERALD, 0);
    values.put(EntityType.DIAMOND, 0);

    switch (world.roll(5)) {
      case 1:
        final int basicDamage = getWeaponBasicDamage(level);
        values.put(EntityType.WOOD, 1);
        values.put(EntityType.STEEL, 2);
        Item s = ItemFactory.createWeapon("sword", level, basicDamage, 1, null);
        s = addMod(world, values, s, "sword");
        s = addEffect(world, values, s, "sword");
        return new Recipe(s, values.get(EntityType.STEEL), values.get(EntityType.WOOD),
            values.get(EntityType.LEATHER), values.get(EntityType.BEETLESHELL),
            values.get(EntityType.HERBS), values.get(EntityType.RUBY),
            values.get(EntityType.AMETHYST), values.get(EntityType.EMERALD),
            values.get(EntityType.DIAMOND));
      case 2:
        final int bowDamage = getWeaponBasicDamage(level);
        values.put(EntityType.LEATHER, 1);
        values.put(EntityType.WOOD, 2);
        Item bw = ItemFactory.createWeapon("bow", level, bowDamage, 3, null);
        bw = addMod(world, values, bw, "bow");
        bw = addEffect(world, values, bw, "bow");
        return new Recipe(bw, values.get(EntityType.STEEL), values.get(EntityType.WOOD),
            values.get(EntityType.LEATHER), values.get(EntityType.BEETLESHELL),
            values.get(EntityType.HERBS), values.get(EntityType.RUBY),
            values.get(EntityType.AMETHYST), values.get(EntityType.EMERALD),
            values.get(EntityType.DIAMOND));
      case 3:
        final int armorValue = getArmorValue(level);
        values.put(EntityType.LEATHER, 2);
        values.put(EntityType.STEEL, 1);
        Item ar = ItemFactory.createArmor("armor", level, armorValue, null);
        ar = addMod(world, values, ar, "armor");
        ar = addEffect(world, values, ar, "armor");
        return new Recipe(ar, values.get(EntityType.STEEL), values.get(EntityType.WOOD),
            values.get(EntityType.LEATHER), values.get(EntityType.BEETLESHELL),
            values.get(EntityType.HERBS), values.get(EntityType.RUBY),
            values.get(EntityType.AMETHYST), values.get(EntityType.EMERALD),
            values.get(EntityType.DIAMOND));
      case 4:
        final int potionValue = getPotionValue(level);
        values.put(EntityType.HERBS, 3);
        Item po = ItemFactory.createPotion("potion", Config.MIN_STACK, level, potionValue);
        po = addMod(world, values, po, "potion");
        return new Recipe(po, values.get(EntityType.STEEL), values.get(EntityType.WOOD),
            values.get(EntityType.LEATHER), values.get(EntityType.BEETLESHELL),
            values.get(EntityType.HERBS), values.get(EntityType.RUBY),
            values.get(EntityType.AMETHYST), values.get(EntityType.EMERALD),
            values.get(EntityType.DIAMOND));
      case 5:
        values.put(EntityType.BEETLESHELL, 3);
        Item dec = ItemFactory
            .createDecoction("decoction", Config.MIN_STACK, Config.DURATION, null);
        dec = addMod(world, values, dec, "decoction");
        dec = addEffect(world, values, dec, "decoction");
        return new Recipe(dec, values.get(EntityType.STEEL), values.get(EntityType.WOOD),
            values.get(EntityType.LEATHER), values.get(EntityType.BEETLESHELL),
            values.get(EntityType.HERBS), values.get(EntityType.RUBY),
            values.get(EntityType.AMETHYST), values.get(EntityType.EMERALD),
            values.get(EntityType.DIAMOND));

      default:
        break;

    }

    return null;
  }

  private static Item addMod(final World world,
      final Map<EntityType, Integer> values, final Item basicItem, final String basicName) {
    if ((world.roll(world.getPlayer().getSkill(CharacterSkill.LUCK))) % 2 == 0) {
      switch (basicName) {
        case "sword" -> {
          final Weapon t = (Weapon) basicItem;
          switch (world.roll(3)) {
            case 1:
              values.put(EntityType.STEEL, values.get(EntityType.STEEL) + 1);
              return ItemFactory.createWeapon("sharp " + t.getName(), t.getLevel(),
                  t.getDamage() + 1, t.getRange(), t.getEffect());
            case 2:
              values.put(EntityType.STEEL, values.get(EntityType.STEEL) - 1);
              return ItemFactory.createWeapon("blunt " + t.getName(),
                  t.getLevel(), Math.max(t.getDamage() - 1, 1), t.getRange(), t.getEffect());
            case 3:
              values.put(EntityType.WOOD, values.get(EntityType.WOOD) + 1);
              return ItemFactory.createWeapon("long " + t.getName(), t.getLevel(), t.getDamage(),
                  t.getRange() + 1, t.getEffect());
            default:
              break;
          }
        }
        case "bow" -> {
          final Weapon bo = (Weapon) basicItem;
          switch (world.roll(3)) {
            case 1:
              values.put(EntityType.WOOD, values.get(EntityType.WOOD) + 1);
              return ItemFactory.createWeapon("long " + bo.getName(), bo.getLevel(), bo.getDamage(),
                  bo.getRange() + 1, bo.getEffect());
            case 2:
              values.put(EntityType.WOOD, values.get(EntityType.WOOD) - 1);
              return ItemFactory.createWeapon("short " + bo.getName(),
                  bo.getLevel(), bo.getDamage(), Math.max(bo.getRange() - 1, 1), bo.getEffect());
            case 3:
              values.put(EntityType.WOOD, values.get(EntityType.WOOD) + 1);
              return ItemFactory.createWeapon("reinforced " + bo.getName(),
                  bo.getLevel(), bo.getDamage() + 1, bo.getRange(), bo.getEffect());
            default:
              break;
          }
        }
        case "armor" -> {
          final Armor ar = (Armor) basicItem;
          switch (world.roll(3)) {
            case 1:
              values.put(EntityType.STEEL, values.get(EntityType.STEEL) + 2);
              return ItemFactory.createArmor("ironclad " + ar.getName(),
                  ar.getLevel(), ar.getArmor() + 4, ar.getEffect());
            case 2:
              values.put(EntityType.LEATHER, values.get(EntityType.LEATHER) + 1);
              return ItemFactory
                  .createArmor("improved " + ar.getName(), ar.getLevel(), ar.getArmor() + 2, null);

            case 3:
              values.put(EntityType.LEATHER, values.get(EntityType.LEATHER) - 1);
              return ItemFactory.createArmor("light " + ar.getName(),
                  ar.getLevel(), Math.max(ar.getArmor() - 2, 1), null);
            default:
              break;
          }
        }
        case "potion" -> {
          final Potion po = (Potion) basicItem;
          switch (world.roll(2)) {
            case 1:
              values.put(EntityType.HERBS, values.get(EntityType.HERBS) + 1);
              return ItemFactory.createPotion("great " + po.getName(),
                  Config.MIN_STACK, po.getLevel(), po.getValue() + 3);
            case 2:
              values.put(EntityType.HERBS, values.get(EntityType.HERBS) - 1);
              return ItemFactory.createPotion("small " + po.getName(),
                  Config.MIN_STACK, po.getLevel(), Math.max(po.getValue() - 3, 1));
            default:
              break;
          }
        }
        case "decoction" -> {
          final Decoction de = (Decoction) basicItem;
          switch (world.roll(2)) {
            case 1:
              values.put(EntityType.BEETLESHELL, values.get(EntityType.BEETLESHELL) + 1);
              return ItemFactory.createDecoction("strong " + de.getName(),
                  Config.MIN_STACK, de.getDuration() + 5, null);
            case 2:
              values.put(EntityType.BEETLESHELL, values.get(EntityType.BEETLESHELL) - 1);
              return ItemFactory.createDecoction("weak " + de.getName(),
                  Config.MIN_STACK, Math.max(de.getDuration() - 5, 1), null);
            default:
              break;
          }
        }
      }
    }
    return basicItem;
  }


  private static Item addEffect(final World world,
      final Map<EntityType, Integer> values, final Item s,
      final String basicName) {

    switch (basicName) {
      case "potion":
        return s;
      case "decoction":
        final Decoction d = (Decoction) s;
        switch (world.roll(4)) {
          case 1:
            values.put(EntityType.RUBY, 1);
            return ItemFactory.createDecoction(d.getName() + " of strength", Config.MIN_STACK,
                d.getDuration(), CharacterSkill.STRENGTH);
          case 2:
            values.put(EntityType.AMETHYST, 1);
            return ItemFactory.createDecoction(d.getName() + " of vitality", Config.MIN_STACK,
                d.getDuration(), CharacterSkill.VITALITY);
          case 3:
            values.put(EntityType.EMERALD, 1);
            return ItemFactory.createDecoction(d.getName() + " of agility", Config.MIN_STACK,
                d.getDuration(), CharacterSkill.AGILITY);
          case 4:
            values.put(EntityType.DIAMOND, 1);
            return ItemFactory.createDecoction(d.getName() + " of luck", Config.MIN_STACK,
                d.getDuration(), CharacterSkill.LUCK);
          default:
            break;
        }
        break;
      case "sword":
      case "bow":
        if ((world.roll(10) + world.getPlayer().getSkill(CharacterSkill.LUCK)) < 10) {
          return s;
        } else {
          final Weapon w = (Weapon) s;
          switch (world.roll(4)) {
            case 1:
              values.put(EntityType.RUBY, 1);
              return ItemFactory
                  .createWeapon(w.getName() + " of strength", w.getLevel(), w.getDamage(),
                      w.getRange(), "strength");
            case 2:
              values.put(EntityType.AMETHYST, 1);
              return ItemFactory
                  .createWeapon(w.getName() + " of vitality", w.getLevel(), w.getDamage(),
                      w.getRange(), "vitality");
            case 3:
              values.put(EntityType.EMERALD, 1);
              return ItemFactory
                  .createWeapon(w.getName() + " of agility", w.getLevel(), w.getDamage(),
                      w.getRange(), "agility");
            case 4:
              values.put(EntityType.DIAMOND, 1);
              return ItemFactory
                  .createWeapon(w.getName() + " of luck", w.getLevel(), w.getDamage(),
                      w.getRange(), "luck");
            default:
              break;
          }
          break;
        }
      case "armor":
        if ((world.roll(10) + world.getPlayer().getSkill(CharacterSkill.LUCK)) < 10) {
          return s;
        } else {
          final Armor w = (Armor) s;
          switch (world.roll(4)) {
            case 1:
              values.put(EntityType.RUBY, 1);
              return ItemFactory
                  .createArmor(w.getName() + " of strength", w.getLevel(), w.getArmor(),
                      "strength");
            case 2:
              values.put(EntityType.AMETHYST, 1);
              return ItemFactory
                  .createArmor(w.getName() + " of vitality", w.getLevel(), w.getArmor(),
                      "vitality");
            case 3:
              values.put(EntityType.EMERALD, 1);
              return ItemFactory
                  .createArmor(w.getName() + " of agility", w.getLevel(), w.getArmor(),
                      "agility");
            case 4:
              values.put(EntityType.DIAMOND, 1);
              return ItemFactory
                  .createArmor(w.getName() + " of luck", w.getLevel(), w.getArmor(),
                      "luck");
            default:
              break;
          }
          break;
        }
    }
    return s;
  }

}
