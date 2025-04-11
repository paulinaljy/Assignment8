package cs3500.pawnsboard.provider.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import cs3500.pawnsboard.model.Cell;
import cs3500.pawnsboard.provider.players.Player;

public class Board implements MockBoardInterface {
  private final List<ArrayList<BoardCell>> boardGrid;
  final int height;
  final int width;

  public Board(int width, int height) {
    if (height <= 0) {
      throw new IllegalArgumentException("Height of board must be greater than 0");
    }
    if (width <= 0 || width % 2 == 0) {
      throw new IllegalArgumentException("Width of board must be odd and greater than 0");
    }
    this.height = height;
    this.width = width;
    this.boardGrid = new ArrayList<ArrayList<BoardCell>>();
  }

  @Override
  public boolean isValidCell(int row, int col) {
    return row >= 0 && row < height && col >= 0 && col < width;
  }

  @Override
  public BoardCell getCell(int row, int col) {
    return boardGrid.get(row).get(col);
  }

  @Override
  public void initializeInitialPawns(Player red, Player blue) {
    for (int r = 0; r < height; r++) {
      boardGrid.get(r).get(0).setPawns(1, red);
      boardGrid.get(r).get(width - 1).setPawns(1, blue);
    }
  }

  @Override
  public int getRows() {
    return this.height;
  }

  @Override
  public int getCols() {
    return this.width;
  }

  @Override
  public void placeCard(int row, int col, Card card, Player currentPlayer) {

    BoardCell cell = this.boardGrid.get(row).get(col);
    if (cell.hasCard()) {
      throw new IllegalArgumentException("Cannot place card: Cell already has card");
    }

    if (cell.getOwner() != currentPlayer || cell.getPawnCount() < card.getCost()) {
      throw new IllegalArgumentException("Cannot place card.");
    }

    cell.setCard(card);

    card.applyInfluence(this, row, col, currentPlayer);
  }
}
