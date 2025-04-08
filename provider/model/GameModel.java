package model;

/**
 * The mutable interface for the Pawns Board game model.
 * Extends the read-only interface to include game state modifications.
 */
public interface GameModel extends ReadonlyPawnsBoardModel {

  /**
   * Attempts to place a card on the specified cell.
   * This method delegates to the Board's placeCard method for validation and influence application.
   * If successful, the turn is switched.
   *
   * @param row  the row index for placement.
   * @param col  the column index for placement.
   * @param card the card to place.
   * @throws IllegalArgumentException if the move is illegal.
   */
  void placeCard(int row, int col, Card card);

  /**
   * The current player passes their turn.
   */
  void passTurn();
}