package cs3500.pawnsboard.provider.view;

import cs3500.pawnsboard.provider.event.PlayerActionEvent;
import cs3500.pawnsboard.view.ViewActions;
import cs3500.pawnsboard.provider.event.ActionType;
import cs3500.pawnsboard.provider.event.PlayerActionListener;

public class PlayerActionListenerAdapter implements PlayerActionListener {
  private final ViewActions observer;

  public PlayerActionListenerAdapter(ViewActions adaptee) {
    this.observer = adaptee;
  }

  @Override
  public void playerActionPerformed(PlayerActionEvent event) {
    ActionType type = event.getActionType();
    switch (type) {
      case SELECT_CARD:
        observer.setCardIdx(event.getCardIndex());
        break;
      case SELECT_CELL:
        observer.setSelectedCell(event.getRow(), event.getCol());
        break;
      case CONFIRM:
        observer.placeCard();
        break;
      case PASS:
        observer.pass();
        break;
    }
  }
}
