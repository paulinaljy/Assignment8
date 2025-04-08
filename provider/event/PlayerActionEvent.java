package cs3500.pawnsboard.provider.event;

/**
 * Encapsulates information about a player's action.
 * You can extend this class with additional details (e.g., card selected, board coordinates, etc.)
 */
public class PlayerActionEvent {

  private final ActionType actionType;
  private final int cardIndex;  // For SELECT_CARD
  private final int row;        // For SELECT_CELL
  private final int col;        // For SELECT_CELL

  /**
   * Creates a new player action event for card selection.
   *
   * @param actionType must be SELECT_CARD
   * @param cardIndex  the index of the selected card
   */
  public PlayerActionEvent(ActionType actionType, int cardIndex) {
    if (actionType != ActionType.SELECT_CARD) {
      throw new IllegalArgumentException("Wrong constructor for " + actionType);
    }
    this.actionType = actionType;
    this.cardIndex = cardIndex;
    this.row = -1;
    this.col = -1;
  }

  /**
   * Creates a new player action event for cell selection.
   *
   * @param actionType must be SELECT_CELL
   * @param row        the selected row
   * @param col        the selected column
   */
  public PlayerActionEvent(ActionType actionType, int row, int col) {
    if (actionType != ActionType.SELECT_CELL) {
      throw new IllegalArgumentException("Wrong constructor for " + actionType);
    }
    this.actionType = actionType;
    this.cardIndex = -1;
    this.row = row;
    this.col = col;
  }

  /**
   * Creates a new player action event for CONFIRM or PASS.
   *
   * @param actionType must be CONFIRM or PASS
   */
  public PlayerActionEvent(ActionType actionType) {
    if (actionType != ActionType.CONFIRM && actionType != ActionType.PASS) {
      throw new IllegalArgumentException("Wrong constructor for " + actionType);
    }
    this.actionType = actionType;
    this.cardIndex = -1;
    this.row = -1;
    this.col = -1;
  }

  /**
   * Returns the type of player action.
   *
   * @return the action type
   */
  public ActionType getActionType() {
    return actionType;
  }

  public int getCardIndex() {
    return cardIndex;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }
}