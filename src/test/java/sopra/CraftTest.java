package sopra;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sopra.controller.Die;
import sopra.model.World;
import sopra.model.entities.EntityType;
import sopra.model.entities.Player;
import sopra.model.entities.craft.Table;
import sopra.model.entities.craft.Trunk;
import sopra.model.entities.items.Item.ItemFactory;
import sopra.model.entities.items.Material;
import sopra.model.entities.items.Potion;
import sopra.model.entities.items.Recipe;

public class CraftTest {

  @Test
  public void craftTest1() throws IOException {
    final File file = new File(
        "maps/TestMap.json");
    final JSONObject json = new JSONObject(
        Objects.requireNonNull(Files.readString(file.toPath(), StandardCharsets.UTF_8)));

    final Schema schema = SchemaLoader.load(new JSONObject(new JSONTokener(Objects
        .requireNonNull(
            Main.class.getClassLoader().getResourceAsStream("schema/world.json")))));
    final World world = World.fromJson(json, new Die(22), schema);

    final Recipe r = ItemFactory.creatRecipe(world, 3);

    final Table table = Table.fromJson();

    final Player player = world.getPlayer();
    final Material m1 = new Material(EntityType.LEATHER, 2);
    final Material m2 = new Material(EntityType.STEEL, 1);

    player.addItem(m1);
    player.addItem(m2);

    Assertions.assertTrue(table.isReady(player, r));
    player.addItem(r);

    Assertions.assertTrue(table.addRecipes(player, EntityType.WEAPON, EntityType.ARMOR));
    table.deleteElements(player, r);
    Assertions.assertFalse(table.isReady(player, r));

  }

  @Test
  public void craft2() throws IOException {
    final File file = new File(
        "maps/recipe.json");
    final JSONObject json = new JSONObject(
        Objects.requireNonNull(Files.readString(file.toPath(), StandardCharsets.UTF_8)));
    final Recipe r = Recipe.fromJson(json.getJSONObject("recipe"));

    final Trunk trunk = Trunk.fromJson();
    Assertions.assertTrue(trunk.add(r));

    trunk.remove(0);
    Assertions.assertTrue(trunk.getItems().get(0).isEmpty());
  }

  @Test
  public void craft3() throws IOException {
    final File file = new File(
        "maps/player.json");
    final JSONObject json = new JSONObject(
        Objects.requireNonNull(Files.readString(file.toPath(), StandardCharsets.UTF_8)));
    final Recipe r = Recipe.fromJson(json.getJSONObject("recipe"));
    final Player player = Player.fromJson(json.getJSONObject("player"));
    final Trunk trunk = Trunk.fromJson();
    trunk.add(r);
    trunk.moveToInventory(player, 0);
    final Recipe tmp = (Recipe) trunk.getItems().get(0).get();
    Assertions.assertEquals(EntityType.RECIPE, tmp.getType());

  }

  @Test
  public void moveToInventoryTest() throws IOException {
    final File file = new File(
        "maps/player.json");
    final JSONObject json = new JSONObject(
        Objects.requireNonNull(Files.readString(file.toPath(), StandardCharsets.UTF_8)));
    final Player player = Player.fromJson(json.getJSONObject("player"));
    final Trunk trunk = Trunk.fromJson();
    final Potion p1 = ItemFactory.createPotion("potion", 1, 1, 5);
    trunk.add(p1);
    Assertions.assertTrue(trunk.moveToInventory(player, 0));
    trunk.remove(0);
    Assertions.assertFalse(trunk.moveToInventory(player, 0));
  }

}
