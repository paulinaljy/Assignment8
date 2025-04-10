package cs3500.pawnsboard.provider.model;

import java.awt.*;
import java.util.List;

import cs3500.pawnsboard.model.GameCard;
import cs3500.pawnsboard.model.Position;
import cs3500.pawnsboard.provider.players.Player;

public class Card implements MockCardInterface {
  private final GameCard adaptee;
  private final boolean[][] influenceGrid;

  /**
   * Initializes a GameCard with a name, cost, value, and influence grid.
   *
   * @param influenceGrid list of relative positions representing cells influenced
   */
  public Card(GameCard adaptee, boolean[][] influenceGrid) {
    if (adaptee == null) {
      throw new IllegalArgumentException("GameCard Adaptee cannot be null");
    }
    this.adaptee = adaptee;
    this.influenceGrid = convertToGrid(adaptee.getPositions());
  }

  /**
   * Converts the list of relative positions from (2,2) to booleans marked as true, indicating they
   * are influence cells.
   * @param positions list of influenced cell positions
   * @return a 2D array of booleans
   */
  private boolean[][] convertToGrid(List<Position> positions) {
    boolean[][] grid = new boolean[5][5];
    for (Position pos : positions) {
      int row = pos.getRowDelta() + 2;
      int col = pos.getColDelta() + 2;
      if (row >= 0 && row < 5 && col >= 0 && col < 5) {
        grid[row][col] = true;
      }
    }
    return grid;
  }

  @Override
  public String getName() {
    return adaptee.getName();
  }

  @Override
  public int getCost() {
    return adaptee.getCost();
  }

  @Override
  public int getValue() {
    return adaptee.getValue();
  }

  @Override
  public Player getOwner() {
    Color playerColor = adaptee.getOwnedColor();
    if (playerColor.equals(Color.red)) {
      return Player.RED;
    } else {
      return Player.BLUE;
    }
  }

  @Override
  public boolean[][] getInfluenceGrid() {
    return this.influenceGrid;
  }

  @Override
  public void applyInfluence(Board board, int placedRow, int placedCol, Player currentPlayer) { /////////////

  }
}
