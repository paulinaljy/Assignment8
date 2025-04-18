package cs3500.pawnsboard.provider.model;

import java.awt.*;

import cs3500.pawnsboard.model.GameCard;
import cs3500.pawnsboard.model.QueensBlood;
import cs3500.pawnsboard.model.ReadOnlyCell;
import cs3500.pawnsboard.provider.event.ModelStatusListener;

/**
 * Represents an adapter for the model used in the provider view. This class implements the
 * provider's generic ModelActionInterface interface, while composing our pawns board model
 * implementation. This adapter has behaviors including getting the board, current player, opponent,
 * hand, width and height of the board, winner of the game, and calculating the score.
 *
 * <p>Note: Some methods (e.g., {@code placeCard} {@code passTurn} {@code gameStart}) are not
 * implemented since they are not required for view operations.
 */
public class ModelActionAdapter implements ModelActionInterface {
  private final QueensBlood model;
  private ModelStatusListener observer;

  /**
   * Initializes a ModelActionAdapter with our QueensBlood model.
   * @param model our model used in the game
   */
  public ModelActionAdapter(QueensBlood model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
    this.observer = null;
  }

  @Override
  public void addModelStatusListener(ModelStatusListener listener) {
    this.observer = listener;
  }

  @Override
  public void placeCard(int row, int col, Card card) {
    // not needed for view
  }

  @Override
  public void passTurn() {
    model.pass();
  }

  @Override
  public Board getBoard() {
    return new BoardAdapter(model.getBoard());
  }

  @Override
  public Player getCurrentPlayer() {
    if (model.getCurrentPlayerID() == 1) {
      return Player.RED;
    } else {
      return Player.BLUE;
    }
  }

  @Override
  public Player getOpponent() {
    if (model.getCurrentPlayerID() == 1) {
      return Player.BLUE;
    } else {
      return Player.RED;
    }
  }

  @Override
  public int calculateScore(Player player) {
    if (player == Player.RED) {
      return model.getPlayerTotalScore(1);
    } else {
      return model.getPlayerTotalScore(2);
    }
  }

  @Override
  public int calculateRowScore(Player player, int row) {
    if (player == Player.RED) {
      return model.getP1RowScore(row);
    } else {
      return model.getP2RowScore(row);
    }
  }

  @Override
  public boolean isGameOver() {
    return model.isGameOver();
  }

  @Override
  public Hand getHand(Player player) {
    cs3500.pawnsboard.model.Player currentPlayer = model.getPlayerByColor(Color.RED);
    if (player.equals(Player.BLUE)) {
      currentPlayer = model.getPlayerByColor(Color.BLUE);
    }
    return new HandAdapter(currentPlayer);
  }

  @Override
  public int getRowCount() {
    return model.getHeight();
  }

  @Override
  public int getColumnCount() {
    return model.getWidth();
  }

  @Override
  public Card getCardAt(int row, int col) {
    ReadOnlyCell cell = model.getCellAt(row, col);
    if (cell.isGameCard()) {
      return new CardAdapter((GameCard)cell);
    } else {
      return null;
    }
  }

  @Override
  public boolean canPlace(int row, int col, Card card) {
    ReadOnlyCell centerCell = model.getCellAt(row, col);

    // if there are no pawns (empty cell or game card)
    if (!centerCell.isCardPlaceable()) {
      return false;
    }
    // if the given card and position does not contain the same color pawn as the player
    if (!(centerCell.getOwnedColor().equals(model.getCurrentPlayer().getColor()))) {
      throw new IllegalStateException("You do not own these pawns. Cannot add card. ");
    }
    // if the player does not have enough pawns to cover the cost of the card
    int cardIdx = model.getCurrentPlayer().getHand().indexOf(card);
    if (centerCell.getValue() < model.getCurrentPlayer().getCard(cardIdx).getCost()) {
      throw new IllegalStateException("You do not have enough pawns to cover the cost of this " +
              "card. ");
    }
    return true;
  }

  @Override
  public boolean canPlace(int row, int col, int cardIndex) {
    if (cardIndex < 0 || cardIndex >= model.getCurrentPlayer().getHandSize()){
      return false;
    }
    return true;
  }

  @Override
  public Player getWinner() {
    if (model.getWinner() == null) {
      return null;
    } else if (model.getWinner().getColor().equals(Color.RED)) {
      return Player.RED;
    } else {
      return Player.BLUE;
    }
  }

  @Override
  public void gameStart(int initialHandSize) {
    // not needed for view
  }
}
