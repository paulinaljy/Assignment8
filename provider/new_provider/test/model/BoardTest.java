package model;

import org.junit.Assert;
import org.junit.Test;

import players.Player;

/**
 * Unit tests for the Board class with various scenarios related to card placement.
 */
public class BoardTest {

  @Test
  public void testPlaceCardSuccessfulPlacement() {
    Board board = new Board(5, 5);
    Player currentPlayer = Player.RED;

    board.initializeInitialPawns(Player.RED, Player.BLUE);
    Card card = new Card("Test Card", 1, 5, currentPlayer, new boolean[][]{{true}});

    int row = 2;
    int col = 0;

    board.placeCard(row, col, card, currentPlayer);

    Assert.assertEquals(card, board.getCell(row, col).getCard());
  }

  @Test
  public void testPlaceCardInsufficientPawns() {
    Board board = new Board(5, 5);
    Player currentPlayer = Player.RED;

    board.initializeInitialPawns(Player.RED, Player.BLUE);
    Card card = new Card("Insufficient Pawns Card", 2, 5, currentPlayer, new boolean[][]{{true}});

    int row = 2;
    int col = 0;

    IllegalArgumentException exception = Assert.assertThrows(
            IllegalArgumentException.class,
        () -> board.placeCard(row, col, card, currentPlayer)
    );

    Assert.assertEquals("Cannot place card: insufficient pawns or cell not owned by "
            + "the player.", exception.getMessage());
  }

  @Test
  public void testPlaceCard_invalidCellCoordinates() {
    Board board = new Board(5, 5);
    Player currentPlayer = Player.RED;

    board.initializeInitialPawns(Player.RED, Player.BLUE);
    Card card = new Card("Invalid Cell Card", 1, 5, currentPlayer, new boolean[][]{{true}});

    int row = -1;
    int col = 10;

    IllegalArgumentException exception = Assert.assertThrows(
            IllegalArgumentException.class,
        () -> board.placeCard(row, col, card, currentPlayer)
    );

    Assert.assertEquals("Invalid cell coordinates.", exception.getMessage());
  }

  @Test
  public void testPlaceCardCellAlreadyContainsCard() {
    Board board = new Board(5, 5);
    Player currentPlayer = Player.RED;

    board.initializeInitialPawns(Player.RED, Player.BLUE);
    Card card1 = new Card("First Card", 1, 5, currentPlayer, new boolean[][]{{true}});
    Card card2 = new Card("Second Card", 1, 4, currentPlayer, new boolean[][]{{true}});

    int row = 2;
    int col = 0;

    board.placeCard(row, col, card1, currentPlayer);

    IllegalArgumentException exception = Assert.assertThrows(
            IllegalArgumentException.class,
        () -> board.placeCard(row, col, card2, currentPlayer)
    );

    Assert.assertEquals("Cell already contains a card.", exception.getMessage());
  }

  @Test
  public void testPlaceCardCellNotOwnedByPlayer() {
    Board board = new Board(5, 5);
    Player currentPlayer = Player.RED;

    board.initializeInitialPawns(Player.RED, Player.BLUE);
    Card card = new Card("Opponent Cell Card", 1, 5, currentPlayer, new boolean[][]{{true}});

    int row = 2;
    int col = 4; // Controlled by opponent (BLUE player)

    IllegalArgumentException exception = Assert.assertThrows(
            IllegalArgumentException.class,
        () -> board.placeCard(row, col, card, currentPlayer)
    );

    Assert.assertEquals("Cannot place card: insufficient pawns or cell not owned by "
            + "the player.", exception.getMessage());
  }
}