package cs3500.pawnsboard.provider.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple class to store the cards a player currently holds in their hand.
 */
public class Hand {
  private final List<Card> cards;

  public Hand() {
    this.cards = new ArrayList<>();
  }

  /**
   * Adds a card to this hand.
   */
  public void addCard(Card c) {
    cards.add(c);
  }

  /**
   * Removes a card from this hand (if present).
   */
  public void removeCard(Card c) {
    cards.remove(c);
  }

  /**
   * Gets all cards in this hand as a list.
   */
  public List<Card> getCards() {
    return cards;
  }

  /**
   * Returns the number of cards in the hand.
   */
  public int size() {
    return cards.size();
  }
}