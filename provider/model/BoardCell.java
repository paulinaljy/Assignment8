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
    this.pawnsCount = pawnCount;
    this.owner = owner;
  }

  @Override
  public void incrementPawnCount(Player currentOwner) {
    if (this.owner == currentOwner) {
      this.pawnsCount += 1;
    } else {
      this.owner = currentOwner;
      this.pawnsCount = 1;
    }
  }

  @Override
  public void clear() {
    this.pawnsCount = 0;
    this.owner = null;
    this.card = null;
  }

  @Override
  public void addPawns(int i) {
    this.pawnsCount = this.pawnsCount + i;
  }

  @Override
  public void setOwner(Player player) {
    this.owner = player;
  }
}
