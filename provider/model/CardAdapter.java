package cs3500.pawnsboard.provider.model;

import java.awt.*;

import cs3500.pawnsboard.model.GameCard;
import cs3500.pawnsboard.model.Position;

public class CardAdapter implements Card {
  private final GameCard card;

  public CardAdapter(GameCard card) {
    this.card = card;
  }
  @Override
  public String getName() {
    return card.getName();
  }

  @Override
  public int getCost() {
    return card.getCost();
  }

  @Override
  public int getValue() {
    return card.getValue();
  }

  @Override
  public Player getOwner() {
    if (card.getOwnedColor().equals(Color.RED)) {
      return Player.RED;
    } else if (card.getOwnedColor().equals(Color.BLUE)){
      return Player.BLUE;
    } else {
      return null;
    }
  }

  @Override
  public boolean[][] getInfluenceGrid() {
    boolean[][] grid = new boolean[5][5];
    for (Position pos : card.getPositions()) {
      int row = pos.getRowDelta() + 2;
      int col = pos.getColDelta() + 2;
      if (row >= 0 && row < 5 && col >= 0 && col < 5) {
        grid[row][col] = true;
      }
    }
    return grid;
  }

  @Override
  public void applyInfluence(Board board, int placedRow, int placedCol, Player currentPlayer) {
    // not needed for view
  }
}
