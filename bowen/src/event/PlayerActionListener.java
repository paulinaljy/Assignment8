package event;

/**
 * Interface for objects that want to be notified of player actions.
 */
public interface PlayerActionListener {

  /**
   * Called when a player (human or AI) attempts an action.
   *
   * @param event contains the type of action and any associated data
   */
  void playerActionPerformed(PlayerActionEvent event);
}