package cs3500.pawnsboard.provider.model;

public interface Cell {
  /**
   * Gets the card in the cell. This card can be null if there are no cards in this cell.
   *
   * @return the card in the cell
   */
  Card getCard();

  /**
   * Gets the owner of the cell. The owner is whoever has their card or pawn on the cell.
   *
   * @return the owner of the cell
   */
  Player getOwner();

  /**
   * Gets the number of pawns on the cell.
   *
   * @return the number of pawns on the cell
   */
  int getPawnCount();

  /**
   * Adds the specified number of pawns to the cell.
   *
   * @param i the number of pawns to add; can be positive or negative
   */
  void addPawns(int i);

  /**
   * Removes a pawn from the cell.
   */
  void removePawn();

  /**
   * Checks if the cell is empty.
   *
   * @return whether the cell is empty
   */
  boolean isEmpty();

  /**
   * Places a card on the cell. When a card is placed, all pawns are removed.
   *
   * @param card the card placed on the cell
   * @throws IllegalArgumentException if the card being placed is null
   */
  void placeCard(Card card);

  /**
   * Returns a copy of the cell.
   * @return a copy of the cell
   */
  Cell copy();

  /**
   * Checks if the cell has a card.
   *
   * @return whether the cell has a card
   */
  boolean hasCard();
}