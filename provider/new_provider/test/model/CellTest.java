package model;

import org.junit.Assert;
import org.junit.Test;

import players.Player;

/**
 * Unit tests for the Cell class to ensure proper functionality and behavior.
 */
public class CellTest {

  @Test
  public void testSetCardSuccessfully() {
    // Arrange
    Cell cell = new Cell();

    Card card = new Card("TestCard", 1, 2, Player.RED, new boolean[3][3]);

    // Act
    cell.setCard(card);

    // Assert
    Assert.assertEquals(card, cell.getCard());
    Assert.assertTrue(cell.hasCard());
    Assert.assertEquals(Player.RED, cell.getOwner());
    Assert.assertEquals(0, cell.getPawnCount());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetCardWithNullThrowsException() {
    // Arrange
    Cell cell = new Cell();

    // Act & Assert
    cell.setCard(null);
  }

  @Test
  public void testSetCardOverridesPreviousCard() {
    // Arrange
    Cell cell = new Cell();
    Player firstOwner = Player.RED;
    Card firstCard = new Card("FirstCard", 1, 2, firstOwner, new boolean[3][3]);
    Player secondOwner = Player.BLUE;
    Card secondCard = new Card("SecondCard", 2, 3, secondOwner, new boolean[3][3]);

    // Act
    cell.setCard(firstCard);
    cell.setCard(secondCard);

    // Assert
    Assert.assertEquals(secondCard, cell.getCard());
    Assert.assertTrue(cell.hasCard());
    Assert.assertEquals(secondOwner, cell.getOwner());
    Assert.assertEquals(0, cell.getPawnCount());
  }
}