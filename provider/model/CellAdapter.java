package cs3500.pawnsboard.provider.model;

import java.awt.*;

import cs3500.pawnsboard.model.GameCard;
import cs3500.pawnsboard.model.Cell;

public class CellAdapter implements BoardCell {
  private final Cell cell;

  public CellAdapter(Cell cell) {
    this.cell = cell;
  }

  @Override
  public boolean isEmpty() {
    return !cell.isCardPlaceable() && !cell.isGameCard();
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
  public void setCard(Card card) {
    // not supported
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
  public void setPawns(int pawnCount, Player owner) {
    // not supported for view
  }

  @Override
  public void incrementPawnCount(Player currentOwner) {
    // not supported for view
  }

  @Override
  public void clear() {
    // not supported for view
  }

  @Override
  public void addPawns(int i) {
    // not supported for view
  }

  @Override
  public void setOwner(Player player) {
    // not supported for view
  }
}
