package players;

import event.PlayerActionListener;

/**
 * A HumanPlayer that relies on GUI interactions to choose actions.
 */
public class HumanPlayer implements Playable {

  @Override
  public void addActionListener(PlayerActionListener listener) {
    // A human player might never directly emit PlayerActionEvents;
    // instead, the view triggers them. So there may be nothing to do here.
  }

  @Override
  public void takeTurn() {
    // For a human, do nothing here. The user will click GUI elements,
    // so the real signals come from the view's mouse/key listeners.
  }
} 