package model;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import players.Player;

/**
 * Unit tests for the Deck class with various scenarios related to card placement.
 */
public class DeckTest {

  @Test
  public void testDrawCardSuccessfullyReturnsCard() {
    List<Card> cards = new ArrayList<>();
    Card card1 = new Card("Card1", 1, 3, Player.RED, new boolean[3][3]);
    Card card2 = new Card("Card2", 2, 2, Player.BLUE, new boolean[3][3]);
    cards.add(card1);
    cards.add(card2);

    Deck deck = new Deck(cards);

    Card drawnCard = deck.drawCard();

    Assert.assertNotNull(drawnCard);
    Assert.assertEquals("Card1", drawnCard.getName());
    Assert.assertEquals(1, deck.cardsRemaining());
  }

  @Test
  public void testDrawCardUntilDeckEmpty() {
    List<Card> cards = new ArrayList<>();
    Card card1 = new Card("Card1", 1, 3, Player.RED, new boolean[3][3]);
    cards.add(card1);

    Deck deck = new Deck(cards);

    Card firstDrawnCard = deck.drawCard();

    Assert.assertNotNull(firstDrawnCard);
    Assert.assertEquals("Card1", firstDrawnCard.getName());

    Assert.assertNull(deck.drawCard());
    Assert.assertEquals(0, deck.cardsRemaining());
  }

  @Test
  public void testDrawCardFromEmptyDeck() {
    List<Card> cards = new ArrayList<>();
    Deck deck = new Deck(cards);

    Assert.assertNull(deck.drawCard());
    Assert.assertEquals(0, deck.cardsRemaining());
  }

  @Test
  public void testDeckSizeRemainsConsistent() {
    List<Card> cards = new ArrayList<>();
    Card card1 = new Card("Card1", 1, 3, Player.RED, new boolean[3][3]);
    Card card2 = new Card("Card2", 2, 4, Player.BLUE, new boolean[3][3]);
    cards.add(card1);
    cards.add(card2);

    Deck deck = new Deck(cards);

    Assert.assertEquals(2, deck.size());
    deck.drawCard();
    Assert.assertEquals(2, deck.size());
    deck.drawCard();
    Assert.assertEquals(2, deck.size());
  }

  @Test
  public void testCardsRemainingDecreasesCorrectly() {
    List<Card> cards = new ArrayList<>();
    Card card1 = new Card("Card1", 1, 3, Player.RED, new boolean[3][3]);
    Card card2 = new Card("Card2", 2, 4, Player.BLUE, new boolean[3][3]);
    cards.add(card1);
    cards.add(card2);

    Deck deck = new Deck(cards);

    Assert.assertEquals(2, deck.cardsRemaining());
    deck.drawCard();
    Assert.assertEquals(1, deck.cardsRemaining());
    deck.drawCard();
    Assert.assertEquals(0, deck.cardsRemaining());
  }
}