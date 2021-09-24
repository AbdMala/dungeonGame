package sopra.controller.visitor;

import sopra.controller.command.Command;
import sopra.model.entities.Chest;
import sopra.model.entities.Monster;
import sopra.model.entities.Player;
import sopra.model.entities.craft.Cauldron;
import sopra.model.entities.craft.Table;
import sopra.model.entities.craft.Trunk;
import sopra.model.entities.items.Armor;
import sopra.model.entities.items.Decoction;
import sopra.model.entities.items.Jewel;
import sopra.model.entities.items.Material;
import sopra.model.entities.items.Potion;
import sopra.model.entities.items.Recipe;
import sopra.model.entities.items.SwordPart;
import sopra.model.entities.items.Weapon;

/**
 * {@link EntityVisitor}s are used to help the {@link Command}s in executing their task.
 *
 * <p>They encapsulate {@link Command} related code for different entities.
 *
 */
public interface EntityVisitor<T> {

  T visit(Armor armor);

  T visit(Player player);

  T visit(Decoction decoction);

  T visit(Monster monster);

  T visit(Potion potion);

  T visit(SwordPart swordPart);

  T visit(Chest chest);

  T visit(Weapon weapon);

  T visit(Table table);

  T visit(Cauldron cauldron);

  T visit(Trunk trunk);

  T visit(Recipe recipe);

  T visit(Jewel jewel);

  T visit(Material material);
}
