package cs3500.pawnsboard.provider.model;

public interface Cell {
  /**
   * Checks if the cell is empty.
   *
   * @return whether the cell is empty
   */
  boolean isEmpty();

  /**
   * Checks if the cell has a card.
   *
   * @return whether the cell has a card
   */
  boolean hasCard();

  /**
   * Gets the number of pawns on the cell.
   *
   * @return the number of pawns on the cell
   */
  int getPawnCount();

  /**
   * Gets the owner of the cell. The owner is whoever has their card or pawn on the cell.
   *
   * @return the owner of the cell
   */
  Player getOwner();

  /**
   * Places a card on the cell. When a card is placed, all pawns are removed.
   *
   * @param card the card placed on the cell
   * @throws IllegalArgumentException if the card being placed is null
   */
  void setCard(Card card);

  /**
   * Gets the card in the cell. This card can be null if there are no cards in this cell.
   *
   * @return the card in the cell
   */
  Card getCard();

  /**
   * Sets the number of pawns in the cell. The number cannot be greater than 3.
   *
   * @param pawnCount the number of pawns to be set in the cell
   * @param owner the owner of the pawns
   * @throws IllegalStateException if the cell already contains a card
   * @throws IllegalArgumentException if the number of pawns is negative or greater than 3
   */
  void setPawns(int pawnCount, Player owner);

  /**
   * Increments the pawn count by one if the current owner already owns the cell. If the cell is
   * empty or owned by the opponent, it resets to one pawn with the current owner.
   *
   * @param currentOwner the player currently incrementing the pawn count
   * @throws IllegalStateException if there is already a card in the cell
   */
  void incrementPawnCount(Player currentOwner);

  /**
   * Clears the cell.
   */
  void clear();

  /**
   * Adds the specified number of pawns to the cell.
   *
   * @param i the number of pawns to add; can be positive or negative
   */
  void addPawns(int i);

  /**
   * Sets the owner of the cell. The owner is the player who has influence over the cell, such as
   * through card placement or pawns.
   *
   * @param player the player to be set as the owner of the cell
   */
  void setOwner(Player player);
}