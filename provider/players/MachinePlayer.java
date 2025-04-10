package cs3500.pawnsboard.provider.players;

import event.PlayerActionEvent;
import event.PlayerActionListener;
import event.ActionType;

/**
 * A MachinePlayer that automates moves using a chosen strategy.
 */
public class MachinePlayer implements Playable {
  private PlayerActionListener listener;

  @Override
  public void addActionListener(PlayerActionListener listener) {
    this.listener = listener;
  }

  @Override
  public void takeTurn() {
    // For example, the machine chooses to pass immediately:
    if (listener != null) {
      listener.playerActionPerformed(
          new PlayerActionEvent(ActionType.PASS)
      );
    }
  }
} 