package cs3500.pawnsboard.provider.model;

import cs3500.pawnsboard.provider.players.Player;

/**
 * A cell in the game of Pawns Board.
 */
public class Cell {
  private int pawnCount;
  private Player owner;
  private Card card;

  /**
   * Constructs a new Cell object. By default, the cell is initialized as empty, with no owner and
   * no card.
   */
  public Cell() {
    this.pawnCount = 0;
    this.owner = null;
    this.card = null;
  }

  /**
   * Checks if the cell is empty.
   *
   * @return whether the cell is empty
   */
  public boolean isEmpty() {
    return card == null && pawnCount == 0;
  }

  /**
   * Checks if the cell has a card.
   *
   * @return whether the cell has a card
   */
  public boolean hasCard() {
    return card != null;
  }

  /**
   * Gets the number of pawns on the cell.
   *
   * @return the number of pawns on the cell
   */
  public int getPawnCount() {
    return pawnCount;
  }

  /**
   * Gets the owner of the cell. The owner is whoever has their card or pawn on the cell.
   *
   * @return the owner of the cell
   */
  public Player getOwner() {
    return owner;
  }

  /**
   * Places a card on the cell. When a card is placed, all pawns are removed.
   *
   * @param card the card placed on the cell
   * @throws IllegalArgumentException if the card being placed is null
   */
  public void setCard(Card card) {
    if (card == null) {
      throw new IllegalArgumentException("Card cannot be null when placing a card.");
    }
    this.card = card;
    this.pawnCount = 0;
    this.owner = card.getOwner();
  }

  /**
   * Gets the card in the cell. This card can be null if there are no cards in this cell.
   *
   * @return the card in the cell
   */
  public Card getCard() {
    return card;
  }

  /**
   * Sets the number of pawns in the cell. The number cannot be greater than 3.
   *
   * @param pawnCount the number of pawns to be set in the cell
   * @param owner the owner of the pawns
   * @throws IllegalStateException if the cell already contains a card
   * @throws IllegalArgumentException if the number of pawns is negative or greater than 3
   */
  public void setPawns(int pawnCount, Player owner) {
    if (hasCard()) {
      throw new IllegalStateException("Cannot set pawns on a cell that already has a card.");
    }
    if (pawnCount < 0 || pawnCount > 3) {
      throw new IllegalArgumentException("Pawn count must be between 0 and 3.");
    }
    this.pawnCount = pawnCount;
    this.owner = (pawnCount > 0) ? owner : null;
  }

  // Increments the pawn count by one if the current owner already owns the cell.
  // If the cell is empty or owned by the opponent, it resets to one pawn with the current owner.

  /**
   * Increments the pawn count by one if the current owner already owns the cell. If the cell is
   * empty or owned by the opponent, it resets to one pawn with the current owner.
   *
   * @param currentOwner the player currently incrementing the pawn count
   * @throws IllegalStateException if there is already a card in the cell
   */
  public void incrementPawnCount(Player currentOwner) {
    if (hasCard()) {
      throw new IllegalStateException("Cannot increment pawns on a cell that already has a card.");
    }
    if (this.owner != null && this.owner.equals(currentOwner)) {
      if (pawnCount < 3) {
        pawnCount++;
      }
    } else {
      pawnCount = 1;
      owner = currentOwner;
    }
  }

  /**
   * Clears the cell.
   */
  public void clear() {
    pawnCount = 0;
    owner = null;
    card = null;
  }

  /**
   * Adds the specified number of pawns to the cell.
   *
   * @param i the number of pawns to add; can be positive or negative
   */
  public void addPawns(int i) {
    this.pawnCount += i;
  }

  /**
   * Sets the owner of the cell. The owner is the player who has influence over the cell, such as
   * through card placement or pawns.
   *
   * @param player the player to be set as the owner of the cell
   */
  public void setOwner(Player player) {
    this.owner = player;
  }
}