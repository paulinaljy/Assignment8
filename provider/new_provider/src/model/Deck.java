package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a deck of cards belonging to a single player.
 * The deck can be read from a config file.
 */
public class Deck {
  private final List<Card> cards;
  private int currentIndex;

  /**
   * Constructs a Deck from an existing list of Cards.
   */
  public Deck(List<Card> cards) {
    this.cards = new ArrayList<>(cards);
    this.currentIndex = 0;
  }

  /**
   * Draws the next card from the deck.
   *
   * @return If no cards remain, returns null.
   */
  public Card drawCard() {
    if (currentIndex >= cards.size()) {
      return null;
    }
    Card c = cards.get(currentIndex);
    currentIndex++;
    return c;
  }

  /**
   * Gets the total number of cards in this deck.
   */
  public int size() {
    return cards.size();
  }

  /**
   * Returns how many cards remain to be drawn.
   */
  public int cardsRemaining() {
    return cards.size() - currentIndex;
  }
}