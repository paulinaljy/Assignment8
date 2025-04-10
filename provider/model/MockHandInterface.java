package cs3500.pawnsboard.provider.model;

import java.util.List;

public interface MockHandInterface {
  void addCard(Card c);

  /**
   * Removes a card from this hand (if present).
   */
  void removeCard(Card c);

  /**
   * Gets all cards in this hand as a list.
   */
  List<Card> getCards();

  /**
   * Returns the number of cards in the hand.
   */
  int size();
}