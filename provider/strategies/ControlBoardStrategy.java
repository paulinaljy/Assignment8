package cs3500.pawnsboard.provider.strategies;

import java.util.List;

import cs3500.pawnsboard.provider.model.GameModel;
import cs3500.pawnsboard.provider.players.Player;

/**
 * Strategy that selects a move (card and location) that gives the current player ownership 
 * of the most cells. In the event of a tie between board positions the strategy chooses the 
 * uppermost (and then leftmost) cell, and in the event of a tie between cards chooses the card 
 * that appears earliest in the hand.
 */
public class ControlBoardStrategy implements ComputerStrategy {

  @Override
  public boolean makeMove(GameModel model) {
    Player currentPlayer = model.getCurrentPlayer();
    Hand hand = model.getHand(currentPlayer);
    List<Card> cards = hand.getCards();
    int rows = model.getRowCount();
    int cols = model.getColumnCount();

    // Track the best candidate move.
    int bestOwnership = -1;
    int bestRow = -1;
    int bestCol = -1;
    Card bestCard = null;

    // Loop over cards in hand in order (leftmost/first card is preferred).
    for (Card card : cards) {
      // Scan board cells in row-major order (uppermost then leftmost).
      for (int row = 0; row < rows; row++) {
        for (int col = 0; col < cols; col++) {
          if (model.canPlace(row, col, card)) {
            // Create a simulation of the board.
            model.getBoard(); // original board reference
            // Copy board to simulate move
            // (We assume the board copy does not affect the model.)
            var boardCopy = BoardSimulationUtil.copyBoard(model.getBoard());
            // Simulate placing the card on the cloned board.
            // Note: using the same card.applyInfluence method.
            card.applyInfluence(boardCopy, row, col, currentPlayer);
            // Compute how many cells will be controlled.
            int controlled = BoardSimulationUtil.countCellsOwned(boardCopy, currentPlayer);
            // Update our best candidate move if we get an improvement.
            if (controlled > bestOwnership) {
              bestOwnership = controlled;
              bestRow = row;
              bestCol = col;
              bestCard = card;
            }
            // In case of tie, our loops guarantee we choose the uppermost-leftmost 
            // cell and the first (leftmost) card.
          }
        }
      }
    }

    // If we found an acceptable move, execute it.
    if (bestCard != null) {
      model.placeCard(bestRow, bestCol, bestCard);
      return true;
    }
    // No legal move found.
    return false;
  }
}