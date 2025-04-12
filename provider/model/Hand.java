package cs3500.pawnsboard.provider.model;

import java.util.List;

public interface Hand {
  /**
   * Gets all cards in this hand as a list.
   */
  List<Card> getCards();

  /**
   * Adds a card to the hand.
   * @param c card to be added
   */
  void addCard(Card c);

  /**
   * Removes a card from this hand (if present).
   * @param c card to be removed
   */
  void removeCard(Card c);

  /**
   * Returns whether this hand contains the given card
   * @param card card to compare to
   * @return boolean whether this hand has the given card
   */
  boolean containsCard(Card card);

  /**
   * Returns the number of cards in the hand.
   */
  int size();

  /**
   * Returns a copy of the player's hand.
   * @return copy of hand
   */
  Hand copy();
}