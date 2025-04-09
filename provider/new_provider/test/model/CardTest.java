package model;

import org.junit.Test;
import org.junit.Assert;

import players.Player;

/**
 * Unit tests for Cards to ensure proper functionality and behavior.
 */
public class CardTest {

  @Test
  public void testApplyInfluence_AddsPawnToEmptyCell() {
    boolean[][] influenceGrid = new boolean[5][5];
    influenceGrid[2][2] = true;

    Player owner = Player.RED;
    Player currentPlayer = Player.RED;
    Card card = new Card("Test Card", 1, 1, owner, influenceGrid);
    Board board = new Board(5, 5);

    int row = 2;
    int col = 2;

    card.applyInfluence(board, row, col, currentPlayer);

    Cell targetCell = board.getCell(row, col);
    Assert.assertEquals(1, targetCell.getPawnCount());
    Assert.assertEquals(currentPlayer, targetCell.getOwner());
  }

  @Test
  public void testApplyInfluence_IncrementPawnCountOnSameOwnerCell() {
    boolean[][] influenceGrid = new boolean[5][5];
    influenceGrid[2][2] = true;

    Player owner = Player.RED;
    Player currentPlayer = Player.RED;
    Card card = new Card("Test Card", 1, 1, owner, influenceGrid);
    Board board = new Board(5, 5);

    int row = 2;
    int col = 2;

    board.getCell(row, col).setPawns(1, currentPlayer);
    card.applyInfluence(board, row, col, currentPlayer);

    Cell targetCell = board.getCell(row, col);
    Assert.assertEquals(2, targetCell.getPawnCount());
    Assert.assertEquals(currentPlayer, targetCell.getOwner());
  }

  @Test
  public void testApplyInfluence_LimitsPawnCountToThree() {
    boolean[][] influenceGrid = new boolean[5][5];
    influenceGrid[2][2] = true;

    Player owner = Player.RED;
    Player currentPlayer = Player.RED;
    Card card = new Card("Test Card", 1, 1, owner, influenceGrid);
    Board board = new Board(5, 5);

    int row = 2;
    int col = 2;

    board.getCell(row, col).setPawns(3, currentPlayer);
    card.applyInfluence(board, row, col, currentPlayer);

    Cell targetCell = board.getCell(row, col);
    Assert.assertEquals(3, targetCell.getPawnCount());
    Assert.assertEquals(currentPlayer, targetCell.getOwner());
  }

  @Test
  public void testApplyInfluence_SkipsOccupiedCellWithCard() {
    boolean[][] influenceGrid = new boolean[5][5];
    influenceGrid[2][2] = true;

    Player owner = Player.RED;
    Player currentPlayer = Player.RED;
    Card card = new Card("Test Card", 1, 1, owner, influenceGrid);
    Board board = new Board(5, 5);

    int row = 2;
    int col = 2;

    board.placeCard(row, col, card, currentPlayer);
    card.applyInfluence(board, row, col, currentPlayer);

    Cell targetCell = board.getCell(row, col);
    Assert.assertTrue(targetCell.hasCard());
    Assert.assertEquals(0, targetCell.getPawnCount());
  }

  @Test
  public void testApplyInfluence_DoesNotOverwriteOpponentsCell() {
    boolean[][] influenceGrid = new boolean[5][5];
    influenceGrid[2][2] = true;

    Player owner = Player.RED;
    Player currentPlayer = Player.RED;
    Player opponent = Player.BLUE;
    Card card = new Card("Test Card", 1, 1, owner, influenceGrid);
    Board board = new Board(5, 5);

    int row = 2;
    int col = 2;

    board.getCell(row, col).setPawns(2, opponent);
    card.applyInfluence(board, row, col, currentPlayer);

    Cell targetCell = board.getCell(row, col);
    Assert.assertEquals(2, targetCell.getPawnCount());
    Assert.assertEquals(opponent, targetCell.getOwner());
  }

  @Test
  public void testApplyInfluence_HandlesOutOfBoundsCells() {
    boolean[][] influenceGrid = new boolean[5][5];
    influenceGrid[0][0] = true;
    influenceGrid[4][4] = true;

    Player owner = Player.RED;
    Player currentPlayer = Player.RED;
    Card card = new Card("Test Card", 1, 1, owner, influenceGrid);
    Board board = new Board(5, 5);

    card.applyInfluence(board, 0, 0, currentPlayer);
    card.applyInfluence(board, 4, 4, currentPlayer);

    Cell targetCell1 = board.getCell(0, 0);
    Cell targetCell2 = board.getCell(4, 4);

    Assert.assertEquals(1, targetCell1.getPawnCount());
    Assert.assertEquals(currentPlayer, targetCell1.getOwner());

    Assert.assertEquals(1, targetCell2.getPawnCount());
    Assert.assertEquals(currentPlayer, targetCell2.getOwner());
  }

  @Test
  public void testApplyInfluence_AppliesMirroredInfluenceForBluePlayer() {
    boolean[][] influenceGrid = new boolean[5][5];
    influenceGrid[2][0] = true;

    Player owner = Player.RED;
    Player currentPlayer = Player.BLUE;
    Card card = new Card("Test Card", 1, 1, owner, influenceGrid);
    Board board = new Board(5, 5);

    int row = 2;
    int col = 2;

    card.applyInfluence(board, row, col, currentPlayer);

    Cell targetCell = board.getCell(row, col + 2);
    Assert.assertEquals(1, targetCell.getPawnCount());
    Assert.assertEquals(currentPlayer, targetCell.getOwner());
  }
}