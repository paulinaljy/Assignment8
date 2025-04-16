package cs3500.pawnsboard.provider.view;

import cs3500.pawnsboard.provider.event.PlayerActionEvent;
import cs3500.pawnsboard.view.ViewActions;
import cs3500.pawnsboard.provider.event.ActionType;
import cs3500.pawnsboard.provider.event.PlayerActionListener;

/**
 * Represents an adapter for the player action listener used in the provider's code. This class
 * implements the provider's generic PlayerActionListener interface, while composing our listener
 * implementation, ViewActions. This adapter has behaviors including performing the player action
 * event based on the action type: SELECT_CARD notifies our observer to set the card index.
 * SELECT_CELL notifies our observer to set the selected cell. CONFIRM notifies our observer to
 * place the card. PASS notifies our observer to pass.
 *
 */
public class PlayerActionListenerAdapter implements PlayerActionListener {
  private final ViewActions observer;

  /**
   * Initializes a PlayerActionListenerAdapter with our ViewActions observer.
   * @param observer our ViewActions observer
   */
  public PlayerActionListenerAdapter(ViewActions observer) {
    this.observer = observer;
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
