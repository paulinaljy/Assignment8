package cs3500.pawnsboard.provider.model;

public interface Card {
  /**
   * Gets the name of the card.
   *
   * @return the name of the card
   */
  String getName();

  /**
   * Gets the cost of the card.
   *
   * @return the cost of the card
   */
  int getCost();

  /**
   * Gets the value of the card.
   *
   * @return the value of the card
   */
  int getValue();

  /**
   * Gets the owner of the card.
   *
   * @return the owner of the card
   */
  Player getOwner();

  /**
   * Get the influence grid of the card.
   * @return the influence grid of the card.
   */
  boolean[][] getInfluenceGrid();

  /**
   * Returns a copy of the card.
   * @return a copy of the game card
   */
  Card copy();
}