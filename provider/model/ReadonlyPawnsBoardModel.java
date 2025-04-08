package model;

import players.Player;

/**
 * A read-only interface for the Pawns Board game model.
 * Provides only observation methods.
 */
public interface ReadonlyPawnsBoardModel {

  /**
   * Returns the game board.
   *
   * @return the Board object representing the game grid.
   */
  Board getBoard();

  /**
   * Returns the current player whose turn it is.
   *
   * @return the current Player.
   */
  Player getCurrentPlayer();

  /**
   * Returns the opponent of the current player.
   *
   * @return the opposing Player.
   */
  Player getOpponent();

  /**
   * Gets the total score of a given player.
   *
   * @param player the player whose score is checked
   * @return the score of the given player
   */
  int calculateScore(Player player);

  /**
   * Gets the score for a particular row for a given player.
   *
   * @param player the player whose row is being checked
   * @param row the row index to check
   * @return the score of the given player in that row
   */
  int calculateRowScore(Player player, int row);

  /**
   * Check whether the game is over.
   *
   * @return true if the game has ended.
   */
  boolean isGameOver();

  /**
   * Get the hand of the given player.
   *
   * @param p the player whose hand is being requested.
   * @return the player's hand.
   */
  Hand getHand(Player p);

  /**
   * Returns the number of rows in the game grid.
   *
   * @return the number of rows.
   */
  int getRowCount();

  /**
   * Returns the number of columns in the game grid.
   *
   * @return the number of columns.
   */
  int getColumnCount();

  /**
   * Returns the card (or pawn) in the cell at a given coordinate.
   *
   * @param row the row index to check.
   * @param col the column index to check.
   * @return the Card at the given coordinate, or null if none exists.
   */
  Card getCardAt(int row, int col);

  /**
   * Determines whether it is legal for the current player to play the given card at the
   * specified coordinate.
   *
   * @param row the row index.
   * @param col the column index.
   * @param card the card to validate.
   * @return true if playing the card is legal.
   */
  boolean canPlace(int row, int col, Card card);

  /**
   * Determines whether it is legal for the current player to play the card at the given index
   * in their hand at the specified coordinate.
   *
   * @param row the row index.
   * @param col the column index.
   * @param cardIndex the index of the card in the current player's hand.
   * @return true if playing the card is legal.
   */
  boolean canPlace(int row, int col, int cardIndex);

  /**
   * If the game is over, returns the winning player. If there is a tie or the game is not over,
   * returns null.
   *
   * @return the winning Player, or null if there is no winner.
   */
  Player getWinner();

  /**
   * Start the game.
   * @param initialHandSize the hand size of player.
   */
  void gameStart(int initialHandSize);
}