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
import sopra.model.entities.items.Armor;
import sopra.model.entities.items.Item.ItemFactory;
import sopra.model.entities.items.Recipe;

public class RecipeTest {

  @Test
  public void generateTest() throws IOException {

    final File file = new File(
        "maps/TestMap.json");
    final JSONObject json = new JSONObject(
        Objects.requireNonNull(Files.readString(file.toPath(), StandardCharsets.UTF_8)));

    final Schema schema = SchemaLoader.load(new JSONObject(new JSONTokener(Objects
        .requireNonNull(
            Main.class.getClassLoader().getResourceAsStream("schema/world.json")))));
    final World world = World.fromJson(json, new Die(22), schema);

    final Recipe r = ItemFactory.creatRecipe(world, 3);
    final Armor myArmor = (Armor) r.getItem();
    Assertions.assertEquals("armor", myArmor.getName());
    Assertions.assertEquals(5, myArmor.getArmor());
    Assertions.assertEquals(3, myArmor.getLevel());

  }
}
