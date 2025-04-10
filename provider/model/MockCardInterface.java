package cs3500.pawnsboard.provider.model;

import cs3500.pawnsboard.provider.players.Player;

public interface MockCardInterface {
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
   * Applies the influence from the card.
   *
   * @param board the board where this method will take place
   * @param placedRow the row the card will be placed in
   * @param placedCol the column the card will be placed in
   * @param currentPlayer the player placing the card
   */
  void applyInfluence(Board board, int placedRow, int placedCol, Player currentPlayer);
}