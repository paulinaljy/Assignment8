package cs3500.pawnsboard.provider.strategies;

import java.util.List;

import cs3500.pawnsboard.provider.model.GameModel;
import cs3500.pawnsboard.provider.players.Player;

/**
 * A (simplified) minimax strategy that selects the move that minimizes the maximum benefit 
 * that the opponent can achieve with their next move. This is done by, for each legal move 
 * candidate for the current player, simulating the move on a copy of the board and then 
 * enumerating all possible opponent moves to see which of those yields the highest cell ownership 
 * for the opponent. The current player then chooses the move that minimizes that maximum.
 */
public class MinimaxStrategy implements ComputerStrategy {

  @Override
  public boolean makeMove(GameModel model) {
    Player currentPlayer = model.getCurrentPlayer();
    Player opponent = model.getOpponent();
    Hand currentHand = model.getHand(currentPlayer);
    List<Card> currentCards = currentHand.getCards();
    Hand opponentHand = model.getHand(opponent);
    List<Card> opponentCards = opponentHand.getCards();
    int rows = model.getRowCount();
    int cols = model.getColumnCount();

    // For each candidate move by the current player, determine the best outcome for the opponent.
    // We will choose the move that minimizes the opponent's maximum result.
    int bestEvaluation = Integer.MAX_VALUE;
    int bestRow = -1;
    int bestCol = -1;
    Card bestCard = null;

    // Enumerate each candidate move (using order that breaks ties as specified).
    for (Card card : currentCards) {
      for (int row = 0; row < rows; row++) {
        for (int col = 0; col < cols; col++) {
          if (model.canPlace(row, col, card)) {
            // Simulate current move on a copy of the board.
            var boardCopy = BoardSimulationUtil.copyBoard(model.getBoard());
            card.applyInfluence(boardCopy, row, col, currentPlayer);
            // Now, determine what is the best move the opponent can make from this state.
            int opponentBest = simulateOpponentBest(boardCopy, opponent, opponentCards, rows, cols);
            // We want to choose the move that minimizes the opponent's maximum benefit.
            if (opponentBest < bestEvaluation) {
              bestEvaluation = opponentBest;
              bestRow = row;
              bestCol = col;
              bestCard = card;
            }
          }
        }
      }
    }

    if (bestCard != null) {
      model.placeCard(bestRow, bestCol, bestCard);
      return true;
    }
    // No legal move was found.
    return false;
  }

  /**
   * Simulates all legal moves for the opponent (on the given board state) and returns the maximum
   * number of cells the opponent can control after making one move.
   * Note: For simplicity, the legality check here is rudimentary.
   *
   * @param board  the board state after the current player's move.
   * @param opponent the opponent player.
   * @param oppCards the list of cards in the opponent's hand.
   * @param rows    the number of rows in the board.
   * @param cols    the number of columns in the board.
   * @return the maximum cell ownership value the opponent can achieve.
   */
  private int simulateOpponentBest(model.Board board, Player opponent,
                                   List<Card> oppCards, int rows, int cols) {
    int best = 0;
    // For each opponent card.
    for (Card oppCard : oppCards) {
      for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
          // Only consider cells that do not already have a card.
          if (!board.getCell(r, c).hasCard()) {
            // A rudimentary legality check: assume shot is legal if the cell has enough pawns.
            // (For a full simulation you would replicate canPlace's logic.)
            if (board.getCell(r, c).getPawnCount() >= oppCard.getCost()) {
              var boardCopy = BoardSimulationUtil.copyBoard(board);
              oppCard.applyInfluence(boardCopy, r, c, opponent);
              int controlled = BoardSimulationUtil.countCellsOwned(boardCopy, opponent);
              if (controlled > best) {
                best = controlled;
              }
            }
          }
        }
      }
    }
    return best;
  }
}