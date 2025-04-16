package cs3500.pawnsboard.provider.players;

import cs3500.pawnsboard.provider.event.PlayerActionListener;

/**
 * A Playable represents a participant in the PawnsBoard game. This
 * could be a human (GUI) player or an AI player.
 */
public interface Playable {

  /**
   * Adds a listener that will respond to this player's actions.
   * For instance, a human might use a GUI to produce these actions,
   * whereas an AI might trigger them programmatically.
   */
  void addActionListener(PlayerActionListener listener);

  /**
   * Called when it is this player's turn. An AI can automatically
   * decide a move here, while a human might do nothing and simply
   * wait for GUI interactions. Potentially calls the listener's
   * methods to signal the chosen action.
   */
  void takeTurn();
} 