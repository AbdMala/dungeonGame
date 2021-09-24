package sopra.controller.visitor;

import java.util.Optional;
import java.util.Queue;
import sopra.comm.Observer;
import sopra.model.entities.Chest;
import sopra.model.entities.Monster;
import sopra.model.entities.Player;
import sopra.model.entities.craft.Cauldron;
import sopra.model.entities.craft.Table;
import sopra.model.entities.craft.Trunk;
import sopra.model.entities.items.Armor;
import sopra.model.entities.items.Decoction;
import sopra.model.entities.items.Item;
import sopra.model.entities.items.Jewel;
import sopra.model.entities.items.Material;
import sopra.model.entities.items.Potion;
import sopra.model.entities.items.Recipe;
import sopra.model.entities.items.SwordPart;
import sopra.model.entities.items.Weapon;

public class DropVisitor extends DefaultEntityVisitor {

  private final Player player;
  private final int index;
  private final Queue<Observer> observers;

  public DropVisitor(final Player player, final int index, final Queue<Observer> observers) {
    this.player = player;
    this.index = index;
    this.observers = observers;
  }

  @Override
  protected void handle(final Trunk trunk) {
    final Optional<Item> slot = player.getItem(this.index);
    if (slot.isPresent() && trunk.add(slot.get())) {
      player.removeAll(this.index);
      //observers.forEach(observer -> observer.notifyUpdateWorld(this.tile));
    } else {
      observers.forEach(observer -> observer.notifyCommandFailed("No item in this slot!"));
    }
  }

  @Override
  protected void handle(final Decoction decoction) {
    observers.forEach(observer -> observer.notifyCommandFailed("No item in this slot!"));
  }

  @Override
  protected void handle(final Monster monster) {
    observers.forEach(observer -> observer.notifyCommandFailed("No item in this slot!"));
  }

  @Override
  protected void handle(final Player player) {
    observers.forEach(observer -> observer.notifyCommandFailed("No item in this slot!"));
  }

  @Override
  protected void handle(final Potion potion) {
    observers.forEach(observer -> observer.notifyCommandFailed("No item in this slot!"));
  }

  @Override
  protected void handle(final SwordPart swordPart) {
    observers.forEach(observer -> observer.notifyCommandFailed("No item in this slot!"));
  }

  @Override
  protected void handle(final Chest chest) {
    observers.forEach(observer -> observer.notifyCommandFailed("No item in this slot!"));
  }

  @Override
  protected void handle(final Weapon weapon) {
    observers.forEach(observer -> observer.notifyCommandFailed("No item in this slot!"));
  }

  @Override
  protected void handle(final Armor armor) {
    observers.forEach(observer -> observer.notifyCommandFailed("No item in this slot!"));
  }

  @Override
  protected void handle(final Table table) {
    observers.forEach(observer -> observer.notifyCommandFailed("No item in this slot!"));
  }

  @Override
  protected void handle(final Cauldron cauldron) {
    observers.forEach(observer -> observer.notifyCommandFailed("No item in this slot!"));
  }

  @Override
  protected void handle(final Recipe recipe) {
    observers.forEach(observer -> observer.notifyCommandFailed("No item in this slot!"));
  }

  @Override
  protected void handle(final Jewel jewel) {
    observers.forEach(observer -> observer.notifyCommandFailed("No item in this slot!"));
  }

  @Override
  protected void handle(final Material material) {
    observers.forEach(observer -> observer.notifyCommandFailed("No item in this slot!"));
  }
}
