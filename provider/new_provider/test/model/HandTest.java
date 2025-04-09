package model;

import org.junit.Test;
import org.junit.Assert;

import players.Player;

/**
 * Unit tests for the Hand class with various scenarios related to card placement.
 */
public class HandTest {

  @Test
  public void testSizeWithEmptyHand() {
    Hand hand = new Hand();

    Assert.assertEquals("The size of an empty hand should be 0.", 0, hand.size());
  }

  @Test
  public void testSizeAfterAddingOneCard() {
    boolean[][] exampleInfluenceGrid = {
            {false, false, true, false, false},
            {false, true, true, true, false},
            {true, true, true, true, true},
            {false, true, true, true, false},
            {false, false, true, false, false}
    };

    Hand hand = new Hand();
    Card card = new Card("Card1", 1, 1, Player.RED, exampleInfluenceGrid);
    hand.addCard(card);

    Assert.assertEquals("The size of the hand should be 1 after adding one card.",
            1, hand.size());
  }

  @Test
  public void testSizeAfterAddingMultipleCards() {

    boolean[][] exampleInfluenceGrid = {
            {false, false, true, false, false},
            {false, true, true, true, false},
            {true, true, true, true, true},
            {false, true, true, true, false},
            {false, false, true, false, false}
    };

    Hand hand = new Hand();
    Card card1 = new Card("Spades", 1, 1, Player.RED, exampleInfluenceGrid);
    Card card2 = new Card("Diamonds", 2, 3, Player.BLUE, exampleInfluenceGrid);
    hand.addCard(card1);
    hand.addCard(card2);

    Assert.assertEquals("The size of the hand should be 2 after adding two cards.",
            2, hand.size());
  }

  @Test
  public void testSizeAfterRemovingCard() {

    boolean[][] exampleInfluenceGrid = {
            {false, false, true, false, false},
            {false, true, true, true, false},
            {true, true, true, true, true},
            {false, true, true, true, false},
            {false, false, true, false, false}
    };

    Hand hand = new Hand();
    Card card1 = new Card("Clubs", 3, 2, Player.RED, exampleInfluenceGrid);
    Card card2 = new Card("Hearts", 1, 1, Player.BLUE, exampleInfluenceGrid);
    hand.addCard(card1);
    hand.addCard(card2);
    hand.removeCard(card1);

    Assert.assertEquals("The size of the hand should be 1 after removing one of two cards.",
            1, hand.size());
  }

  @Test
  public void testSizeAfterRemovingAllCards() {

    boolean[][] exampleInfluenceGrid = {
            {false, false, true, false, false},
            {false, true, true, true, false},
            {true, true, true, true, true},
            {false, true, true, true, false},
            {false, false, true, false, false}
    };

    Hand hand = new Hand();
    Card card1 = new Card("Spades", 1, 2, Player.RED, exampleInfluenceGrid);
    Card card2 = new Card("Diamonds", 2, 2, Player.BLUE, exampleInfluenceGrid);
    hand.addCard(card1);
    hand.addCard(card2);
    hand.removeCard(card1);
    hand.removeCard(card2);

    Assert.assertEquals("The size of the hand should be 0 after removing all cards.",
            0, hand.size());
  }
}