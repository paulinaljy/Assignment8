package cs3500.pawnsboard.provider.model;

import java.util.ArrayList;
import java.util.List;

public class BoardAdapter implements Board {
  private final List<ArrayList<cs3500.pawnsboard.model.Cell>> board;

  public BoardAdapter(List<ArrayList<cs3500.pawnsboard.model.Cell>> board) {
    if (board == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.board = board;
  }

  @Override
  public boolean isValidCell(int row, int col) {
    return row >= 0 && row < board.size() && col >= 0 && col < board.get(0).size();
  }

  @Override
  public Cell getCell(int row, int col) {
    return new CellAdapter(board.get(row).get(col));
  }

  @Override
  public void initializeInitialPawns(Player red, Player blue) {
    // not supported for view
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
    // not supported for view
  }
}
