package cs3500.pawnsboard.provider.model;

public interface Board {
  /**
   * Returns the number of rows in the board.
   *
   * @return the number of rows.
   */
  int getRows();

  /**
   * Returns the number of columns in the board.
   *
   * @return the number of columns.
   */
  int getCols();

  /**
   * Returns the cell at the specified row and column.
   *
   * @param row the row index
   * @param col the column index
   * @return the Cell object at that position.
   * @throws IndexOutOfBoundsException if the cell indices are out of bounds.
   */
  Cell getCell(int row, int col);

  /**
   * Attempts to place a card on the specified cell. The cell must be owned by the current player
   * and contain enough pawns to cover the card's cost. Once the card is placed, the pawns in the
   * cell are removed and the card's influence is applied.
   *
   * @param row the row index where the card is to be placed
   * @param col the column index where the card is to be placed
   * @param card the card to be placed
   * @param currentPlayer the player who is placing the card
   * @throws IllegalArgumentException if the cell is invalid or doesn't meet placement requirements
   */
  void placeCard(int row, int col, Card card, Player currentPlayer);

  /**
   * Checks if the given row and column indices are valid for this board.
   *
   * @param row the row index
   * @param col the column index
   * @return true if the cell exists within the board, false otherwise.
   */
  boolean isValidPlacement(int row, int col);

  /**
   * Calculates the row score of the given player and row.
   * @param player the current player
   * @param row the row of the score to be calculated
   * @return score of the row
   */
  int calculateRowScore(Player player, int row);

  /**
   * Returns a copy of the board.
   * @return a copy of the game board
   */
  Board copy();
}