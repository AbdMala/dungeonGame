package sopra.model.entities.craft;

import org.json.JSONObject;
import sopra.controller.visitor.EntityVisitor;
import sopra.model.entities.EntityType;
import sopra.utils.JSONSerializable;


public class Table extends Craft implements JSONSerializable<JSONObject> {


  protected Table() {
    super(EntityType.TABLE);
  }

  public static Table fromJson() {
    return new Table();
  }


  @Override
  public <T> T accept(final EntityVisitor<T> visitor) {
    return visitor.visit(this);
  }

}
