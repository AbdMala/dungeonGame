package sopra.model.entities;

import java.util.EnumMap;
import java.util.Optional;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sopra.controller.Config;
import sopra.controller.ServerError;
import sopra.model.Coordinate;
import sopra.model.entities.items.Armor;
import sopra.model.entities.items.Weapon;
import sopra.utils.JSONSerializable;
import sopra.utils.Utils;


public abstract class Character extends Entity implements JSONSerializable<JSONObject> {

  private static final Logger LOGGER = LoggerFactory.getLogger(Character.class);
  protected final String name;
  private final EnumMap<CharacterSkill, Integer> skills;
  private Optional<Armor> armor;
  private int currentHealth;
  private int level;
  private int maxHealth;
  private CharacterState state;
  private Weapon weapon;

  Character(final EntityType type, final String name, final int level, final int currentHealth,
      final int maxHealth, final Weapon weapon, final Optional<Armor> armor,
      final EnumMap<CharacterSkill, Integer> skills, final CharacterState state) {
    super(type);
    this.name = name;
    this.level = level;
    this.currentHealth = currentHealth;
    this.maxHealth = maxHealth;
    this.weapon = weapon;
    this.armor = armor;
    this.skills = skills;
    this.state = state;
  }

  static int calculateHP(final int level, final int vitality) {
    return 10 * level * (int) Math.ceil(1 + .5 * vitality);
  }

  /**
   * Computes the distance between a {@link Character} and a given {@link Coordinate}.
   *
   * @param position the position to which the distance should be computed
   * @return the distance
   */
  public int calculateDistance(final Coordinate position) {
    return this.getPosition().orElseThrow(ServerError::new).distance(position);
  }

  /**
   * Deals damage to an character.
   *
   * @param damage the damage to be dealt
   * @return true if damage was dealt
   */
  public boolean dealDamage(final int damage) {
    final int amount = this.armor.map(i -> Math.max(damage - i.getValue(), 0)).orElse(damage);
    if (amount <= 0) {
      return false;
    }
    this.currentHealth -= amount;
    LOGGER.debug("-{} HP -> {}", amount, this.currentHealth);
    if (this.currentHealth <= 0) {
      this.disable();
    }
    return true;
  }

  public Optional<Armor> getArmor() {
    return this.armor;
  }

  public void setArmor(final Armor armor) {
    this.armor = Optional.of(armor);
  }

  public int getLevel() {
    return this.level;
  }

  public String getName() {
    return this.name;
  }

  public int getSkill(final CharacterSkill skill) {
    return this.skills.get(skill);
  }

  public void addToSkill(final CharacterSkill skill, final int value) {
    this.skills.put(skill, skills.get(skill) + value);
  }

  public CharacterState getState() {
    return this.state;
  }

  public void setState(final CharacterState state) {
    this.state = state;
  }

  public Weapon getWeapon() {
    return this.weapon;
  }

  public void setWeapon(final Weapon weapon) {
    this.weapon = weapon;
  }

  /**
   * Heals the character by a given amount
   *
   * @param value the amount of healthpoints to be healed
   * @return true if health points changed
   */
  public boolean heal(final int value) {
    if (this.currentHealth == this.maxHealth) {
      return false;
    }
    this.currentHealth = Math.min(this.currentHealth + value, this.maxHealth);
    LOGGER.debug("+{} HP -> {}", value, this.currentHealth);
    return true;
  }

  /**
   * Levels up the {@link Player}.
   */
  void levelUp() {
    this.level++;
    LOGGER.debug("{} level up -> {}", this.getType(), this.getLevel());
    this.updateMaxHP();
    LOGGER.debug("HP -> {}", this.currentHealth);
  }

  @Override
  public JSONObject toJSON() {
    final JSONObject root = new JSONObject();
    root.put("agility", this.getSkill(CharacterSkill.AGILITY));
    root.put("currentHealth", this.currentHealth);
    root.put("level", this.level);
    root.put("maxHealth", this.maxHealth);
    root.put("name", this.name);
    root.put("strength", this.getSkill(CharacterSkill.STRENGTH));
    root.put("vitality", this.getSkill(CharacterSkill.VITALITY));
    root.put("weapon", this.weapon.toJSON());
    this.armor.ifPresent(armor -> root.put("armor", armor.toJSON()));
    return root;
  }

  @Override
  public String toString() {
    return Utils.substitute("{}({})", this.getType(), this.getLevel());
  }

  void updateMaxHP() {
    this.maxHealth = calculateHP(this.level, this.getSkill(CharacterSkill.VITALITY));
    this.currentHealth = this.maxHealth;
  }

  /**
   * Upgrades a character skill.
   *
   * @param skill the skill to be upgraded
   * @return true if skill was upgraded
   */
  public boolean upgrade(final CharacterSkill skill) {
    if (this.skills.get(skill) >= Config.MAX_SKILL_LEVEL) {
      return false;
    }
    this.skills.computeIfPresent(skill, (k, v) -> v + 1);
    if (skill == CharacterSkill.VITALITY) {
      this.maxHealth = calculateHP(this.level, this.getSkill(CharacterSkill.VITALITY));
      this.currentHealth = this.maxHealth;
      LOGGER.debug("HP -> {}", this.currentHealth);
    }
    return true;
  }

  public enum CharacterSkill implements JSONSerializable<String> {
    STRENGTH("strength"),
    VITALITY("vitality"),
    AGILITY("agility"),
    LUCK("luck");
    private final String json;

    CharacterSkill(final String json) {
      this.json = json;
    }

    public static CharacterSkill fromIndex(final EntityType type, final int index) {
      assert index >= 1 && index <= values(type).length;
      return values(type)[index - 1];
    }

    public static CharacterSkill[] values(final EntityType type) {
      return switch (type) {
        case PLAYER -> CharacterSkill.values();
        case ASSISTANT, BUG, TUTOR, OVERFLOW, PROFESSOR -> new CharacterSkill[]{
            CharacterSkill.STRENGTH,
            CharacterSkill.VITALITY,
            CharacterSkill.AGILITY
        };
        default -> throw new IllegalArgumentException();
      };
    }

    @Override
    public String toJSON() {
      return this.json;
    }
  }
}
