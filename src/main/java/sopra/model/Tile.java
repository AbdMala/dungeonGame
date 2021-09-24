package sopra.model;

import java.util.Optional;
import org.json.JSONObject;
import sopra.controller.ServerError;
import sopra.model.entities.Entity;
import sopra.utils.JSONSerializable;

public class Tile implements JSONSerializable<JSONObject> {

  private final Coordinate coordinate;
  private Optional<Entity> entity;

  private Tile(final Coordinate coordinate, final Entity entity) {
    this.coordinate = coordinate;
    this.entity = Optional.ofNullable(entity);
  }

  Tile(final Coordinate coordinate) {
    this(coordinate, null);
  }

  public Optional<Entity> getEntity() {
    return this.entity;
  }

  public void setEntity(final Entity entity) {
    if (this.entity.isPresent()) {
      throw new ServerError();
    }
    this.entity = Optional.ofNullable(entity);
  }

  public Coordinate getPosition() {
    return this.coordinate;
  }

  public String getRepresentation() {
    return this.entity.isPresent() ? this.entity.get().getRepresentation() : ".";
  }

  public boolean hasEntity() {
    return this.entity.isPresent();
  }

  void removeEntity() {
    this.entity = Optional.empty();
  }

  @Override
  public JSONObject toJSON() {
    return new JSONObject().put("position", this.coordinate.toJSON())
        .put("representation", this.getRepresentation());
  }

  @Override
  public String toString() {
    return this.toJSON().toString();
  }
}
