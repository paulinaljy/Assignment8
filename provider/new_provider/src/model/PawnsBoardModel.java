package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import players.Player;
import event.ModelStatus;
import event.ModelStatusEvent;
import event.ModelStatusListener;

/**
 * The model for a game of Pawns Board.
 */
public class PawnsBoardModel implements ModelActionInterface {
  private final Board board;
  private final Player red;
  private final Player blue;
  private Player currentPlayer;

  private final Map<Player, Deck> decks;
  private final Map<Player, Hand> hands;

  private boolean passed;
  private boolean gameOver;
  private boolean gameStart;

  private final List<ModelStatusListener> modelStatusListeners = new ArrayList<>();

  /**
   * Constructs a Game model with a board of the given dimensions and the two players. Red always
   * goes first.
   *
   * @param rows     number of rows for the board
   * @param cols     number of columns for the board (must be odd and > 1)
   * @param redDeck  deck for Red (created from configuration files)
   * @param blueDeck deck for Blue (created from configuration files)
   */
  public PawnsBoardModel(int rows, int cols,
                         Deck redDeck, Deck blueDeck) {
    if (rows < 1 || cols < 1) {
      throw new IllegalArgumentException("rows and cols must be greater than 0");
    }
    if (redDeck == null) {
      throw new IllegalArgumentException("redDeck must not be null");
    }
    if (blueDeck == null) {
      throw new IllegalArgumentException("blueDeck must not be null");
    }
    // Initialize players
    this.red = Player.RED;
    this.blue = Player.BLUE;
    this.currentPlayer = red;

    // Initialize Board
    this.board = new Board(rows, cols);
    board.initializeInitialPawns(red, blue);

    // Initialize decks and hands
    this.decks = new HashMap<>();
    decks.put(red, redDeck);
    decks.put(blue, blueDeck);

    this.hands = new HashMap<>();
    hands.put(red, new Hand());
    hands.put(blue, new Hand());

    this.passed = false;
    this.gameOver = false;
    this.gameStart = false;
  }

  @Override
  public void gameStart(int initialHandSize) {
    if (this.isGameOver()) {
      throw new IllegalStateException("Game has already ended");
    }
    if (this.gameStart) {
      throw new IllegalStateException("Game has already started");
    }
    if (initialHandSize <= 0) {
      throw new IllegalArgumentException("Initial hand size must be greater than 0");
    }

    Hand red = hands.get(this.red);
    Hand blue = hands.get(this.blue);
    Deck reds = decks.get(this.red);
    Deck blues = decks.get(this.blue);
    for (int i = 0; i < initialHandSize; i++) {
      red.addCard(reds.drawCard());
      blue.addCard(blues.drawCard());
    }

    gameStart = true;
  }


  @Override
  public Board getBoard() {
    return board;
  }


  @Override
  public Player getCurrentPlayer() {
    return currentPlayer;
  }


  @Override
  public Player getOpponent() {
    return currentPlayer == red ? blue : red;
  }


  @Override
  public void placeCard(int row, int col, Card card) {
    if (gameOver) {
      throw new IllegalStateException("Game has already ended");
    }
    if (!gameStart) {
      throw new IllegalStateException("Game has already started");
    }
    if (row < 0 || col < 0 || row >= board.getRows() || col >= board.getCols()) {
      throw new IllegalArgumentException("Invalid row or col");
    }

    // Delegate to the Board's method which now takes currentPlayer
    board.placeCard(row, col, card, currentPlayer);

    // Remove the card from the current player's hand
    Hand hand = hands.get(currentPlayer);
    hand.removeCard(card);
    Deck deck = decks.get(currentPlayer);
    hand.addCard(deck.drawCard());

    // Switch turn
    switchTurn();
    this.passed = false;
  }


  @Override
  public void passTurn() {
    if (this.isGameOver()) {
      throw new IllegalStateException("Game has already ended");
    }
    if (!gameStart) {
      throw new IllegalStateException("Game hasn't already started");
    }
    if (this.passed) {
      this.gameOver = true;
      notifyGameOver();
    } else {
      this.passed = true;
      switchTurn();
    }
  }

  /**
   * Switches the player's turn.
   */
  private void switchTurn() {
    currentPlayer = (currentPlayer == red) ? blue : red;
    // Notify listeners that turn has changed
    for (ModelStatusListener listener : modelStatusListeners) {
      listener.modelStatusChanged(new ModelStatusEvent(ModelStatus.TURN_CHANGED,
              "Turn changed to " + currentPlayer));
    }
  }


  @Override
  public int calculateScore(Player player) {
    int total = 0;
    for (int row = 0; row < getRowCount(); row++) {
      total += calculateRowScore(player, row);
    }
    return total;
  }


  @Override
  public int calculateRowScore(Player player, int row) {
    int rowScore = 0;
    for (int col = 0; col < getColumnCount(); col++) {
      Cell cell = board.getCell(row, col);
      if (cell.hasCard() && cell.getCard().getOwner() == player) {
        // Adding the card's value to the row score.
        // Assuming card.getValue() returns the needed score.
        rowScore += cell.getCard().getValue();
      }
    }
    return rowScore;
  }


  @Override
  public boolean isGameOver() {
    return gameOver;
  }


  @Override
  public Hand getHand(Player p) {
    return hands.get(p);
  }


  @Override
  public int getRowCount() {
    // Updated to use Board.getRows()
    return board.getRows();
  }


  @Override
  public int getColumnCount() {
    // Updated to use Board.getCols()
    return board.getCols();
  }


  @Override
  public Card getCardAt(int row, int col) {
    return board.getCell(row, col).getCard();
  }


  @Override
  public boolean canPlace(int row, int col, Card card) {
    // If the cell is not valid, placing is not allowed.
    if (!board.isValidCell(row, col)) {
      return false;
    }

    Cell cell = board.getCell(row, col);

    // Check that the cell does not already have a card.
    if (cell.hasCard()) {
      return false;
    }

    // The cell must be owned by the current player.
    if (cell.getOwner() != currentPlayer) {
      return false;
    }

    // The pawn count must be sufficient to cover the cost of the card.
    return cell.getPawnCount() < card.getCost();
  }


  @Override
  public boolean canPlace(int row, int col, int cardIndex) {
    Hand hand = hands.get(currentPlayer);
    if (cardIndex < 0 || cardIndex >= hand.size()) {
      return false;
    }
    Card card = hand.getCards().get(cardIndex);
    return canPlace(row, col, card);
  }


  @Override
  public Player getWinner() {
    if (!gameOver) {
      throw new IllegalArgumentException("game is not end.");
    }

    int redScore = calculateScore(red);
    int blueScore = calculateScore(blue);

    if (redScore > blueScore) {
      return red;
    } else if (blueScore > redScore) {
      return blue;
    } else {
      return null; // It's a tie.
    }
  }

  @Override
  public void addModelStatusListener(ModelStatusListener listener) {
    modelStatusListeners.add(listener);
  }

  private String getGameOverMessage() {
    int redScore = calculateScore(Player.RED);
    int blueScore = calculateScore(Player.BLUE);
    String message = String.format("Game Over! Scores - RED: %d, BLUE: %d. ", redScore, blueScore);

    if (redScore > blueScore) {
      message += "RED wins!";
    } else if (blueScore > redScore) {
      message += "BLUE wins!";
    } else {
      message += "It's a tie!";
    }
    return message;
  }

  private void notifyGameOver() {
    if (modelStatusListeners != null) {
      String message = getGameOverMessage();
      for (ModelStatusListener listener : modelStatusListeners) {
        listener.modelStatusChanged(new ModelStatusEvent(ModelStatus.GAME_OVER, message));
      }
    }
  }
}