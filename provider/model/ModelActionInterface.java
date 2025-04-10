package cs3500.pawnsboard.provider.model;

import cs3500.pawnsboard.provider.players.Player;
import cs3500.pawnsboard.provider.event.ModelStatusListener;

/**
 * Interface for a mutable game model with actions and state queries.
 * This interface extends the read-only contract by including the ability
 * to register model status listeners as well as methods for modifying the game
 * state such as placing a card or passing a turn.
 */
public interface ModelActionInterface extends ReadonlyPawnsBoardModel {

  /**
   * Registers a listener that will be notified whenever the model's status changes.
   *
   * @param listener the listener to add
   */
  void addModelStatusListener(ModelStatusListener listener);

  /**
   * Attempts to place a card on the specified cell.
   * Delegates to the Board's placement mechanisms. On success, the game state is updated.
   *
   * @param row  the row index for placement.
   * @param col  the column index for placement.
   * @param card the card to place.
   * @throws IllegalArgumentException if the move is illegal.
   */
  void placeCard(int row, int col, Card card);

  /**
   * The current player passes their turn.
   * This method should update the game state and notify any registered listeners.
   */
  void passTurn();

  /**
   * Returns the current game board.
   *
   * @return the board
   */
  Board getBoard();

  /**
   * Returns the player whose turn it currently is.
   *
   * @return the current player
   */
  Player getCurrentPlayer();

  /**
   * Returns the opponent of the current player.
   *
   * @return the opponent player
   */
  Player getOpponent();

  /**
   * Calculates the total score for the given player.
   *
   * @param player the player for whom to calculate the score
   * @return the calculated score
   */
  int calculateScore(Player player);

  /**
   * Calculates the score for a specific row for the given player.
   *
   * @param player the player for whom to calculate the row score
   * @param row    the row index
   * @return the calculated row score
   */
  int calculateRowScore(Player player, int row);

  /**
   * Indicates whether the game has ended.
   *
   * @return true if the game is over, false otherwise
   */
  boolean isGameOver();

  /**
   * Returns the hand (set of available cards) for the given player.
   *
   * @param player the player whose hand is returned
   * @return the player's hand
   */
  Hand getHand(Player player);

  /**
   * Returns the total number of rows on the board.
   *
   * @return the row count
   */
  int getRowCount();

  /**
   * Returns the total number of columns on the board.
   *
   * @return the column count
   */
  int getColumnCount();

  /**
   * Retrieves the card located at the specified cell on the board.
   *
   * @param row the row index
   * @param col the column index
   * @return the card at the given position
   */
  Card getCardAt(int row, int col);

  /**
   * Determines if a given card can be placed at the specified board location.
   *
   * @param row  the row index
   * @param col  the column index
   * @param card the card to be placed
   * @return true if the card can be placed, false otherwise
   */
  boolean canPlace(int row, int col, Card card);

  /**
   * Determines if the card at the given index in the player's
   * hand can be placed at the specified location.
   *
   * @param row       the row index
   * @param col       the column index
   * @param cardIndex the index of the card in the hand
   * @return true if the card can be placed, false otherwise
   */
  boolean canPlace(int row, int col, int cardIndex);

  /**
   * Determines and returns the winning player.
   * If no player has yet won, this method should return null.
   *
   * @return the winning player or null if there is none
   */
  Player getWinner();
}