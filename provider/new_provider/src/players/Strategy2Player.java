package players;

import event.ActionType;
import event.PlayerActionEvent;
import event.PlayerActionListener;
import model.ModelActionInterface;
import model.Hand;
import model.GameModel;
import strategies.ControlBoardStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Strategy 2: Fill Most - Places cards in positions with the most pawns.
 */
public class Strategy2Player implements Playable {
  private List<PlayerActionListener> listeners = new ArrayList<>();
  private final ModelActionInterface model;
  private final Player playerColor;
  private final ControlBoardStrategy strategy;

  /**
   * Construct the player on strategy 2.
   * @param model given game model.
   * @param playerColor given player color.
   */
  public Strategy2Player(ModelActionInterface model, Player playerColor) {
    this.model = model;
    this.playerColor = playerColor;
    this.strategy = new ControlBoardStrategy();
  }

  @Override
  public void takeTurn() {
    Hand hand = model.getHand(playerColor);
    if (hand.getCards().isEmpty()) {
      fireActionEvent(new PlayerActionEvent(ActionType.PASS));
      return;
    }

    // Use ControlBoardStrategy to determine the move
    boolean moveMade = strategy.makeMove((GameModel)model);

    if (!moveMade) {
      fireActionEvent(new PlayerActionEvent(ActionType.PASS));
    }
  }

  @Override
  public void addActionListener(PlayerActionListener listener) {
    listeners.add(listener);
  }

  /**
   * Notify models.
   * @param event Given event.
   */
  private void fireActionEvent(PlayerActionEvent event) {
    for (PlayerActionListener listener : listeners) {
      listener.playerActionPerformed(event);
    }
  }
}