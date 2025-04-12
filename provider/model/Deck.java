package cs3500.pawnsboard.provider.model;

import java.util.List;

public interface Deck {
  /**
   * Draws the next card from the deck.
   *
   * @return If no cards remain, returns null.
   */
  Card drawCard();

  /**
   * Returns whether this deck is empty.
   * @return boolean whether this deck is empty
   */
  boolean isEmpty();

  /**
   * Shuffles this deck.
   */
  void shuffle();

  /**
   * Returns how many cards remain to be drawn.
   */
  int remainingCards();

  /**
   * Returns the cards in this deck.
   * @return list of cards
   */
  List<Card> getCards();

  /**
   * Returns a copy of this deck.
   * @return copy of deck
   */
  Deck copy();
}