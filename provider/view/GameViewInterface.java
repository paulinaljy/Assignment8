package cs3500.pawnsboard.provider.view;

import cs3500.pawnsboard.provider.event.PlayerActionListener;

/**
 * Interface for a game view.
 * Provides a method to register a listener for player actions.
 */
public interface GameViewInterface {

  /**
   * Registers a listener to receive player action events.
   *
   * @param listener the listener to add
   */
  void addPlayerActionListener(PlayerActionListener listener);
}