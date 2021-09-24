package sopra.controller.visitor;


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
import sopra.model.entities.items.Jewel;
import sopra.model.entities.items.Material;
import sopra.model.entities.items.Potion;
import sopra.model.entities.items.Recipe;
import sopra.model.entities.items.SwordPart;
import sopra.model.entities.items.Weapon;

public class MoveToInventoryVisitor extends DefaultEntityVisitor {

  private final Player player;
  private final int index;
  private final Queue<Observer> observers;

  public MoveToInventoryVisitor(final Player player, final int index,
      final Queue<Observer> observers) {
    this.player = player;
    this.index = index;
    this.observers = observers;
  }

  @Override
  protected void handle(final Trunk trunk) {
    if (trunk.moveToInventory(player, this.index)) {
      trunk.remove(this.index);
      // this.observers.forEach(observer -> observer.notifyUpdatePlayer(this.player));
    } else {
      this.observers
          .forEach(observer -> observer.notifyCommandFailed("Transfer not possible."));
    }
  }


  @Override
  protected void handle(final Decoction decoction) {
    this.observers
        .forEach(observer -> observer.notifyCommandFailed("Transfer not possible."));
  }

  @Override
  protected void handle(final Potion potion) {
    this.observers
        .forEach(observer -> observer.notifyCommandFailed("Transfer not possible."));
  }

  @Override
  protected void handle(final SwordPart swordPart) {
    this.observers
        .forEach(observer -> observer.notifyCommandFailed("Transfer not possible."));
  }

  @Override
  protected void handle(final Chest chest) {
    this.observers
        .forEach(observer -> observer.notifyCommandFailed("Transfer not possible."));
  }

  @Override
  protected void handle(final Weapon weapon) {
    this.observers
        .forEach(observer -> observer.notifyCommandFailed("Transfer not possible."));
  }

  @Override
  protected void handle(final Armor armor) {
    this.observers
        .forEach(observer -> observer.notifyCommandFailed("Transfer not possible."));
  }

  @Override
  protected void handle(final Monster monster) {
    this.observers
        .forEach(observer -> observer.notifyCommandFailed("Transfer not possible."));
  }

  @Override
  protected void handle(final Player player) {
    this.observers
        .forEach(observer -> observer.notifyCommandFailed("Transfer not possible."));
  }

  @Override
  protected void handle(final Table table) {
    this.observers
        .forEach(observer -> observer.notifyCommandFailed("Transfer not possible."));
  }

  @Override
  protected void handle(final Cauldron cauldron) {
    this.observers
        .forEach(observer -> observer.notifyCommandFailed("Transfer not possible."));
  }

  @Override
  protected void handle(final Recipe recipe) {
    this.observers
        .forEach(observer -> observer.notifyCommandFailed("Transfer not possible."));
  }

  @Override
  protected void handle(final Jewel jewel) {
    this.observers
        .forEach(observer -> observer.notifyCommandFailed("Transfer not possible."));
  }

  @Override
  protected void handle(final Material material) {
    this.observers
        .forEach(observer -> observer.notifyCommandFailed("Transfer not possible."));
  }
}
