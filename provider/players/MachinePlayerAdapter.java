package cs3500.pawnsboard.provider.players;

import cs3500.pawnsboard.player.GamePlayer;
import cs3500.pawnsboard.provider.event.PlayerActionListener;
import cs3500.pawnsboard.provider.view.PlayerActionListenerAdapter;
import cs3500.pawnsboard.view.ViewActions;

public class MachinePlayerAdapter implements GamePlayer {
  private final GamePlayer machinePlayer;
  private PlayerActionListener listener;

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
}
