package cs3500.pawnsboard.provider.view;

import cs3500.pawnsboard.model.QueensBlood;
import cs3500.pawnsboard.view.PawnsBoardView;
import cs3500.pawnsboard.view.ViewActions;

public class ViewAdapter implements PawnsBoardView {
  private final PawnsView view;
  private final QueensBlood model;

  public ViewAdapter(PawnsView view, QueensBlood model) {
    if (view == null) {
      throw new IllegalArgumentException("View cannot be null");
    }
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.view = view;
    this.model = model;
  }

  @Override
  public void refresh() {
    view.refresh();
  }

  @Override
  public void makeVisible() {
    view.makeVisible();
  }

  @Override
  public void subscribe(ViewActions observer) {
    PlayerActionListenerAdapter observerAdapter = new PlayerActionListenerAdapter(observer);
    this.view.addPlayerActionListener(observerAdapter);
  }

  @Override
  public void reset() {
    view.clearHighlights();
  }

  @Override
  public void displayMessage(String message, String title) {
    view.showErrorMessage(message);
  }

  private String getGameOverMessage() {
    int player1Score = model.getPlayerTotalScore(1);
    int player2Score = model.getPlayerTotalScore(2);
    String message = "Game Over! Scores - RED: " + player1Score + " BLUE: " + player2Score;

    if (player1Score > player2Score) {
      message += "RED wins!";
    } else if (player2Score > player1Score) {
      message += "BLUE wins!";
    } else {
      message += "It's a tie!";
    }
    return message;
  }

  @Override
  public void displayGameOver() {
    view.showGameOver(getGameOverMessage());
  }
}
