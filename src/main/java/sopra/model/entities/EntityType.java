package sopra.model.entities;

import org.jetbrains.annotations.Contract;
import sopra.utils.JSONSerializable;


public enum EntityType implements JSONSerializable<String> {
  PLAYER((char) 64, ObjectType.PLAYER),
  ASSISTANT((char) 181, ObjectType.MONSTER, "assistant", 15),
  BUG((char) 164, ObjectType.MONSTER, "bug", 1),
  OVERFLOW((char) 33, ObjectType.MONSTER, "overflow", 5),
  TUTOR((char) 229, ObjectType.MONSTER, "tutor", 10),
  PROFESSOR((char) 182, ObjectType.MONSTER, "professor", 20),
  WEAPON((char) 43, ObjectType.WEAPON),
  ARMOR((char) 38, ObjectType.ARMOR),
  POTION((char) 63, ObjectType.POTION),
  DECOCTION((char) 191, ObjectType.DECOCTION),
  CHEST((char) 61, ObjectType.CHEST),
  SWORD_PART((char) 35, ObjectType.SWORD_PART),
  TABLE((char) 110, ObjectType.TABLE),
  CAULDRON((char) 117, ObjectType.CAULDRON),
  TRUNK((char) 215, ObjectType.TRUNK),
  STEEL((char) 230, ObjectType.MATERIAL),
  WOOD((char) 124, ObjectType.MATERIAL),
  LEATHER((char) 152, ObjectType.MATERIAL),
  BEETLESHELL((char) 248, ObjectType.MATERIAL),
  HERBS((char) 255, ObjectType.MATERIAL),
  RUBY((char) 242, ObjectType.JEWEL),
  AMETHYST((char) 243, ObjectType.JEWEL),
  EMERALD((char) 244, ObjectType.JEWEL),
  DIAMOND((char) 245, ObjectType.JEWEL),
  RECIPE((char) 174, ObjectType.RECIPE);
  private final int factor;
  private final String json;
  private final ObjectType objectType;
  private final char representation;

  EntityType(final char representation, final ObjectType objectType) {
    this(representation, objectType, objectType.toJSON(), 0);
  }

  EntityType(final char representation, final ObjectType objectType, final String json,
      final int factor) {
    this.representation = representation;
    this.objectType = objectType;
    this.json = json;
    this.factor = factor;
  }

  public int getFactor() {
    return this.factor;
  }

  public ObjectType getObjectType() {
    return this.objectType;
  }

  public String getRepresentation() {
    return String.valueOf(this.representation);
  }

  @Override
  public String toJSON() {
    return this.json;
  }

  @Override
  public String toString() {
    return this.json;
  }

  private enum ObjectType implements JSONSerializable<String> {
    PLAYER("player"),
    MONSTER("monster"),
    WEAPON("weapon"),
    ARMOR("armor"),
    POTION("potion"),
    DECOCTION("decoction"),
    CHEST("chest"),
    SWORD_PART("swordPart"),
    TABLE("table"),
    CAULDRON("cauldron"),
    TRUNK("trunk"),
    MATERIAL("material"),
    JEWEL("jewel"),
    RECIPE("recipe");
    private final String json;

    @Contract(pure = true)
    ObjectType(final String json) {
      this.json = json;
    }

    @Override
    public String toJSON() {
      return this.json;
    }
  }
}
