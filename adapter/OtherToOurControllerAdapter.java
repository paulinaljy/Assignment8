package cs3500.pawnsboard.adapter;

import cs3500.pawnsboard.view.ViewActions;
import cs3500.pawnsboard.provider.event.ActionType;
import cs3500.pawnsboard.provider.event.PlayerActionEvent;
import cs3500.pawnsboard.provider.event.PlayerActionListener;

public class OtherToOurControllerAdapter implements PlayerActionListener {
  private final ViewActions adaptee;

  public OtherToOurControllerAdapter(ViewActions adaptee) {
    this.adaptee = adaptee;
  }

  @Override
  public void playerActionPerformed(PlayerActionEvent event) {
    ActionType type = event.getActionType();
    switch (type) {
      case SELECT_CARD -> adaptee.setCardIdx(event.getCardIndex());
      case SELECT_CELL -> adaptee.setSelectedCell(event.getRow(), event.getCol());
      case CONFIRM -> adaptee.placeCard();
      case PASS -> adaptee.pass();
    }
  }
}
