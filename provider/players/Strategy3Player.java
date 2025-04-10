package cs3500.pawnsboard.provider.players;

import cs3500.pawnsboard.provider.event.ActionType;
import cs3500.pawnsboard.provider.event.PlayerActionEvent;
import cs3500.pawnsboard.provider.event.PlayerActionListener;
import cs3500.pawnsboard.provider.model.ModelActionInterface;
import cs3500.pawnsboard.provider.model.GameModel;
import cs3500.pawnsboard.provider.strategies.MinimaxStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Strategy 3: Fill Random - Places cards in random valid positions.
 */
public class Strategy3Player implements Playable {
  private List<PlayerActionListener> listeners = new ArrayList<>();
  private final ModelActionInterface model;
  private final Player playerColor;
  private final MinimaxStrategy strategy;

    /**
     * Construct the player on strategy 3.
     * @param model given game model.
     * @param playerColor given player color.
     */
  public Strategy3Player(ModelActionInterface model, Player playerColor) {
    this.model = model;
    this.playerColor = playerColor;
    this.strategy = new MinimaxStrategy();
  }

  @Override
  public void takeTurn() {
    Hand hand = model.getHand(playerColor);
    if (hand.getCards().isEmpty()) {
      fireActionEvent(new PlayerActionEvent(ActionType.PASS));
      return;
    }

    // Use MinimaxStrategy to determine the move
    boolean moveMade = strategy.makeMove((GameModel) model);

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