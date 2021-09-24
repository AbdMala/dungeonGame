package sopra.controller.visitor;

import java.util.Queue;
import org.json.JSONObject;
import sopra.comm.Observer;
import sopra.model.entities.craft.Trunk;


public class TrunkVisitor extends DefaultEntityVisitor {

  private final Queue<Observer> observers;


  public TrunkVisitor(final Queue<Observer> observers) {
    this.observers = observers;
  }

  @Override
  protected void handle(final Trunk trunk) {
    final JSONObject json = trunk.toJSON();
    observers.forEach(observer -> observer.notifyChoiceWindow(json.toString()));
  }
}
