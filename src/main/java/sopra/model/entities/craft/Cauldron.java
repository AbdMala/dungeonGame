package sopra.model.entities.craft;


import org.json.JSONObject;
import sopra.controller.visitor.EntityVisitor;
import sopra.model.entities.EntityType;
import sopra.utils.JSONSerializable;


public class Cauldron extends Craft implements JSONSerializable<JSONObject> {


  protected Cauldron() {
    super(EntityType.CAULDRON);

  }

  public static Cauldron fromJson() {
    return new Cauldron();
  }


  @Override
  public <T> T accept(final EntityVisitor<T> visitor) {
    return visitor.visit(this);
  }

}
