package cs3500.pawnsboard.provider.model;

import cs3500.pawnsboard.model.QueensBlood;

public class PlayerOneToPlayerTwoModel implements GameModel {
  private final QueensBlood player1Model;

  public PlayerOneToPlayerTwoModel(QueensBlood player1Model) {
    if (player1Model == null) {
      throw new IllegalArgumentException("Player 1 model cannot be null");
    }
    this.player1Model = player1Model;
  }

  @Override
  public void placeCard(int row, int col, Card card) {
    int cardIdx = card.getCardIdx(); // get card index
    player1Model.placeCardInPosition(cardIdx, row, col);
  }

  @Override
  public void passTurn() {
    player1Model.pass();
  }

  @Override
  public Board getBoard() {
    return null;
  }

  @Override
  public Player getCurrentPlayer() {
    return null;
  }

  @Override
  public Player getOpponent() {
    return null;
  }

  @Override
  public int calculateScore(Player player) {
    return 0;
  }

  @Override
  public int calculateRowScore(Player player, int row) {
    return 0;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public Hand getHand(Player p) {
    return null;
  }

  @Override
  public int getRowCount() {
    return 0;
  }

  @Override
  public int getColumnCount() {
    return 0;
  }

  @Override
  public Card getCardAt(int row, int col) {
    return null;
  }

  @Override
  public boolean canPlace(int row, int col, Card card) {
    return false;
  }

  @Override
  public boolean canPlace(int row, int col, int cardIndex) {
    return false;
  }

  @Override
  public Player getWinner() {
    return null;
  }

  @Override
  public void gameStart(int initialHandSize) {

  }
}
