package cs3500.pawnsboard.provider.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents an adapter for a board used in the provider view. This class implements the provider's
 * generic Board interface, while composing our model implementation of the board, which is a list
 * of rows of our board Cells. This adapter has behaviors including getting width and height of the
 * board, getting cells at specific locations of the board, checking if a position is valid, and
 * calculating row scores.
 *
 * <p>Note: Some methods (e.g., {@code placeCard}) are not implemented since they are
 * not required for view operations.
 */
public class BoardAdapter implements Board {
  private final List<ArrayList<cs3500.pawnsboard.model.Cell>> board;

  /**
   * Initializes a BoardAdapter with our board used in the game.
   * @param board an List of rows of Cells used in the game
   */
  public BoardAdapter(List<ArrayList<cs3500.pawnsboard.model.Cell>> board) {
    if (board == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.board = board;
  }

  @Override
  public boolean isValidPlacement(int row, int col) {
    return row >= 0 && row < board.size() && col >= 0 && col < board.get(0).size();
  }

  @Override
  public int calculateRowScore(Player player, int row) {
    return 0;
  }

  @Override
  public Board copy() {
    return new BoardAdapter(board);
  }

  @Override
  public Cell getCell(int row, int col) {
    return new CellAdapter(board.get(row).get(col));
  }

  @Override
  public int getRows() {
    return board.size();
  }

  @Override
  public int getCols() {
    return board.get(0).size();
  }

  @Override
  public void placeCard(int row, int col, Card card, Player currentPlayer) {
    // not needed for view
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof BoardAdapter)) {
      return false;
    }
    BoardAdapter that = (BoardAdapter) other;
    return this.board == that.board;
  }

  @Override
  public int hashCode() {
    return Objects.hash(board);
  }

}
