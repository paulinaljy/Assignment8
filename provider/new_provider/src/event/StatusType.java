package event;

/**
 * Represents the types of status changes that can occur in the game model. This enumeration is used
 * to specify the nature of a game model's state update, such as a board update, a change in player
 * turns, or the end of the game.
 */
public enum StatusType {
  BOARD_UPDATED,
  TURN_CHANGED,
  GAME_OVER
}
