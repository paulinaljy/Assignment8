package cs3500.pawnsboard.provider.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import cs3500.pawnsboard.controller.DeckConfiguration;
import cs3500.pawnsboard.model.Cell;
import cs3500.pawnsboard.model.QueensBlood;
import cs3500.pawnsboard.provider.players.Player;

public class PlayerOneToPlayerTwoModel implements GameModel {
  private final QueensBlood adaptee;
  private final Board board;
  private final Player player1;
  private final Player player2;
  private Player currentPlayer;
  private final List<Card> player1Hand;
  private final List<Card> player2Hand;
  private int p1TotalScore;
  private int p2TotalScore;

  public PlayerOneToPlayerTwoModel(QueensBlood player1Model, int width, int height,
                                   DeckConfiguration deckConfig) {
    if (player1Model == null) {
      throw new IllegalArgumentException("Player 1 model cannot be null");
    }
    this.adaptee = player1Model;
    this.board = new Board(width, height);
    this.player1 = Player.RED;
    this.player2 = Player.BLUE;
    this.currentPlayer = Player.RED;
    this.player1Hand = new ArrayList<Card>();
    this.player2Hand = new ArrayList<Card>();
    this.p1TotalScore = 0;
    this.p2TotalScore = 0;
  }

  @Override
  public void placeCard(int row, int col, Card card) {
    int cardIdx = card.getCardIdx(); // get card index
    adaptee.placeCardInPosition(cardIdx, row, col);
  }

  @Override
  public void passTurn() {
    adaptee.pass();
  }

  @Override
  public Board getBoard() {
    return this.board;
  }

  @Override
  public Player getCurrentPlayer() {
    return null;
  }

  @Override
  public Player getOpponent() {
    if (this.getCurrentPlayer() == Player.RED) {
      return Player.BLUE;
    } else {
      return Player.RED;
    }
  }

  @Override
  public int calculateScore(Player player) {
    if (player == Player.RED) {
      return adaptee.getPlayerTotalScore(1);
    } else {
      return adaptee.getPlayerTotalScore(2);
    }
  }

  @Override
  public int calculateRowScore(Player player, int row) {
    if (player == Player.RED) {
      return adaptee.getP1RowScore(row);
    } else {
      return adaptee.getP2RowScore(row);
    }
  }

  @Override
  public boolean isGameOver() {
    return adaptee.isGameOver();
  }

  @Override
  public Hand getHand(Player p) {
    return null;
  }

  @Override
  public int getRowCount() {
    return this.board.getRows();
  }

  @Override
  public int getColumnCount() {
    return this.board.getCols();
  }

  @Override
  public Card getCardAt(int row, int col) {
    return this.board.getCell(row, col).getCard();
  }

  @Override
  public boolean canPlace(int row, int col, Card card) {
    this.isGameStarted();
    if (!this.board.isValidCell(row, col)) {
      return false;
    }
    BoardCell centerCell = this.board.getCell(row, col);

    // if there are no pawns (empty cell or game card)
    if (centerCell.isEmpty() || centerCell.hasCard()) {
      return false;
    }
    // if the given card and position does not contain the same color pawn as the player
    if (centerCell.getOwner() != this.getCurrentPlayer()) {
      return false;
    }
    // if the player does not have enough pawns to cover the cost of the card
    return centerCell.getPawnCount() < card.getCost();
  }

  @Override
  public boolean canPlace(int row, int col, int cardIndex) {
    this.isGameStarted();
    Player currentPlayer = this.getCurrentPlayer();
    List<Card> currentPlayerHand = player2Hand;
    if (currentPlayer == Player.RED) {
      currentPlayerHand = player1Hand;
    }
    if (cardIndex < 0 || cardIndex >= player1Hand.size()) {
      return false;
    }
    Card card = currentPlayerHand.get(cardIndex);
    return this.canPlace(row, col, card);
  }

  private int getBoardSize() {
    int height = this.getRowCount();
    int width = this.getColumnCount();
    return height * width;
  }

  private int getTotalPlayerScore() {
    this.isGameStarted();
    int p1Score = 0;
    int p2Score = 0;
    for (int row = 0; row < this.getBoardSize(); row++) {
      int p1RowScore = calculateRowScore(Player.RED, row);
      int p2RowScore = calculateRowScore(Player.BLUE, row);
      if (p1RowScore == p2RowScore) {
        continue;
      } else if (p1RowScore > p2RowScore) {
        p1Score += p1RowScore; // p1 + score, p2 + 0
      } else {
        p2Score += p2RowScore; // p2 + score, p1 + 0
      }
    }
    this.p1TotalScore = p1Score;
    this.p2TotalScore = p2Score;
  }
  @Override
  public Player getWinner() {
    this.getTotalPlayerScore();
    if (this.p1TotalScore == this.p2TotalScore) {
      return null;
    }
    if (this.p1TotalScore > this.p2TotalScore) {
      return Player.RED;
    } else {
      return Player.BLUE;
    }
  }

  @Override
  public void gameStart(int initialHandSize) {
    if (gameStarted) {
      throw new IllegalStateException("Game already started");
    }
    if (initialHandSize <= 0) {
      throw new IllegalArgumentException("Hand size must be positive");
    }
  }
}
