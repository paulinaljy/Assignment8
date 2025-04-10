package cs3500.pawnsboard.provider.model;

import cs3500.pawnsboard.provider.players.Player;

public class BoardCell implements MockCellInterface {
  private int pawnsCount;
  private Player owner;
  private Card card;

  public BoardCell() {
    this.pawnsCount = 0;
    this.owner = null;
    this.card = null;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public boolean hasCard() {
    if (card == null) {
      return false;
    }
    return true;
  }

  @Override
  public int getPawnCount() {
    return this.pawnsCount;
  }

  @Override
  public Player getOwner() {
    return this.owner;
  }

  @Override
  public void setCard(Card card) {
    this.card = card;
    this.pawnsCount = 0;
    this.owner = card.getOwner();
  }

  @Override
  public Card getCard() {
    return this.card;
  }

  @Override
  public void setPawns(int pawnCount, Player owner) {

  }

  @Override
  public void incrementPawnCount(Player currentOwner) {

  }

  @Override
  public void clear() {

  }

  @Override
  public void addPawns(int i) {

  }

  @Override
  public void setOwner(Player player) {

  }
}
