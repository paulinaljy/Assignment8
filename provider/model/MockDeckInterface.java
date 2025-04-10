package cs3500.pawnsboard.provider.model;

public interface MockDeckInterface {
  /**
   * Draws the next card from the deck.
   *
   * @return If no cards remain, returns null.
   */
  Card drawCard();

  /**
   * Gets the total number of cards in this deck.
   */
  int size();

  /**
   * Returns how many cards remain to be drawn.
   */
  int cardsRemaining();
}