package cs3500.pawnsboard.provider.model;

import java.awt.*;
import java.util.Objects;

import cs3500.pawnsboard.model.GameCard;

/**
 * Represents an adapter for a cell used in the provider view. This class implements the provider's
 * generic Cell interface, while composing our model implementation of the cell. This adapter has
 * behaviors including checking if a cell is empty, has a card, getting the pawn count, card, and
 * owner of the cell.
 *
 * <p>Note: Some methods (e.g., {@code placeCard} {@code addPawns} {@code removePawns}) are not
 * implemented since they are not required for view operations.
 */
public class CellAdapter implements Cell {
  private final cs3500.pawnsboard.model.Cell cell;

  /**
   * Initializes a CellAdapter with our Cell.
   * @param cell our Cell used in the game
   */
  public CellAdapter(cs3500.pawnsboard.model.Cell cell) {
    if (cell == null) {
      throw new IllegalArgumentException("Cell cannot be null");
    }
    this.cell = cell;
  }

  @Override
  public boolean isEmpty() {
    return !cell.isCardPlaceable() && !cell.isGameCard();
  }

  @Override
  public void placeCard(Card card) {
    // not needed for view
  }

  @Override
  public Cell copy() {
    return new CellAdapter(cell);
  }

  @Override
  public boolean hasCard() {
    return cell.isGameCard();
  }

  @Override
  public int getPawnCount() {
    return cell.getValue();
  }

  @Override
  public Player getOwner() {
    if (cell.getOwnedColor().equals(Color.RED)) {
      return Player.RED;
    } else if (cell.getOwnedColor().equals(Color.BLUE)){
      return Player.BLUE;
    } else {
      return null;
    }
  }

  @Override
  public Card getCard() {
    if (cell.isGameCard()) {
      return new CardAdapter((GameCard)cell);
    } else {
      return null;
    }
  }

  @Override
  public void addPawns(int i) {
    // not needed for view
  }

  @Override
  public void removePawn() {
    // not needed for view
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof CellAdapter)) {
      return false;
    }
    CellAdapter that = (CellAdapter) other;
    return this.cell == that.cell;
  }

  @Override
  public int hashCode() {
    return Objects.hash(cell);
  }
}
