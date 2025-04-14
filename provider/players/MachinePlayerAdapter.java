package cs3500.pawnsboard.provider.players;

import cs3500.pawnsboard.player.GamePlayer;
import cs3500.pawnsboard.provider.event.PlayerActionListener;
import cs3500.pawnsboard.provider.view.PlayerActionListenerAdapter;
import cs3500.pawnsboard.view.ViewActions;

/**
 * Represents an adapter for the machine player used in our view. This class implements our generic
 * GamePlayer interface, while composing our MachinePlayer implementation. This adapter has
 * behaviors including choosing a move, getting player ID, and subscribing to our provider's action
 * listener.
 *
 */
public class MachinePlayerAdapter implements GamePlayer {
  private final GamePlayer machinePlayer;
  private PlayerActionListener listener;

  /**
   * Initializes a MachinePlayerAdapter with a GamePlayer.
   * @param machinePlayer our machine player used in the game.
   */
  public MachinePlayerAdapter(GamePlayer machinePlayer) {
    if (machinePlayer == null) {
      throw new IllegalArgumentException("Machine Player cannot be null");
    }
    this.machinePlayer = machinePlayer;
    this.listener = null;
  }

  @Override
  public void chooseMove() {
    machinePlayer.chooseMove();
  }

  @Override
  public boolean isHumanPlayer() {
    return false;
  }

  @Override
  public int getPlayerID() {
    return machinePlayer.getPlayerID();
  }

  @Override
  public void subscribe(ViewActions observer) {
    machinePlayer.subscribe(observer);
    this.listener = new PlayerActionListenerAdapter(observer);
  }

  @Override
  public String toString() {
    return "Player " + this.getPlayerID();
  }
}
