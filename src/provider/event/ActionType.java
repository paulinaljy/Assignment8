package cs3500.pawnsboard.provider.event;

/**
 * Represents the types of actions that a player can perform during the game.
 * These actions include selecting cards/cells and confirming moves.
 */
public enum ActionType {
  SELECT_CARD,   // When player clicks a card in their hand
  SELECT_CELL,   // When player clicks a cell on the board
  CONFIRM,       // When player confirms their move (e.g., Enter key)
  PASS           // When player passes their turn (e.g., Space key)
}