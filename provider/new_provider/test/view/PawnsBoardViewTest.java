package view;

import model.Board;
import model.Card;
import model.Deck;
import model.ModelActionInterface;
import model.PawnsBoardModel;
import players.Player;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Unit tests for PawnsBoardView to ensure proper functionality and behavior.
 */
public class PawnsBoardViewTest {

  @Test
  public void testToString_BoardWithPawns() {
    // Arrange
    int rows = 3;
    int cols = 3;
    Board board = new Board(rows, cols);
    board.getCell(0, 0).addPawns(2);
    board.getCell(1, 1).addPawns(1);
    board.getCell(2, 2).addPawns(3);

    ModelActionInterface model = new PawnsBoardModel(3,3,
            new Deck(List.of(new Card("A", 1, 1, Player.RED, new boolean[3][3]),
                    new Card("A", 1, 1, Player.RED, new boolean[3][3]),
                    new Card("A", 1, 1, Player.RED, new boolean[3][3]))),
            new Deck(List.of(new Card("B", 1, 1, Player.BLUE, new boolean[3][3]),
                    new Card("B", 1, 1, Player.BLUE, new boolean[3][3]),
                    new Card("B", 1, 1, Player.BLUE, new boolean[3][3]))));
    PawnsBoardView view = new PawnsBoardView(model);

    // Act
    String result = view.toString();

    // Assert
    String expected = "0 1_1 0\n0 1_1 0\n0 1_1 0\nCurrent Scores => RED: 0, BLUE: 0\nCurrent Turn:"
            + " RED\n";

    Assert.assertEquals(expected, result);
  }
}