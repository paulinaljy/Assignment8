package strategies;

import java.util.List;
import model.GameModel;
import model.Card;
import model.Hand;
import players.Player;

/**
 * The MaximizeRowScoreStrategy class is an implementation of the ComputerStrategy interface
 * designed to analyze the game model and execute moves aimed at maximizing the current player's
 * row score. This strategy primarily focuses on surpassing or equaling the opponent's score in
 * specific rows.
 * Core Strategy:
 * 1. Iterates over the rows of the game board in top-down order.
 * 2. For rows where the current player's score does not already exceed the opponent's score:
 *    - Analyzes the player's hand for a card that can potentially bring the current player's
 *      row score to be at least equal to the opponent's score if placed in a valid cell.
 *    - If such a legal move is found, executes the move by placing the card on the board.
 * 3. Returns true immediately upon making a successful move.
 * 4. If no valid move is found across all rows and cards, returns false.
 * Assumptions:
 * - It is assumed that playing a card contributes its value directly to the total row score
 *   for the current player. Any additional effects of the card or complex game interactions
 *   are not taken into account.
 * - The strategy does not simulate potential future moves, but operates based on the immediate
 *   impact of card placement.
 * Limitations:
 * - This strategy may not account for potential long-term consequences or benefits of certain
 *   moves. Its scope is limited to achieving row parity or superiority during the current turn.
 */
public class MaximizeRowScoreStrategy implements ComputerStrategy {

  /**
   * This strategy iterates over rows (top-down). For each row where the current player's
   * score is less than or equal to the opponent's score, the strategy checks the current player's
   * hand. It looks for the first legal play (card and cell in that row) where adding the card’s
   * value would bring the player's score in that row to be at least equal to the opponent's.
   * If such a move exists, it is executed immediately.
   * Note: This simplistic approach assumes that the effect of playing a card in the row is to
   * simply add the card’s value to the row score. If your game uses a more complex system,
   * you may need to simulate the play to compute the hypothetical new score.
   */
  @Override
  public boolean makeMove(GameModel model) {
    Player currentPlayer = model.getCurrentPlayer();
    Player opponent = model.getOpponent();
    int rowCount = model.getRowCount();
    
    // Iterate rows from top-down.
    for (int row = 0; row < rowCount; row++) {
      int currentRowScore = model.calculateRowScore(currentPlayer, row);
      int opponentRowScore = model.calculateRowScore(opponent, row);
      
      // Only consider moves on rows where current score is not beating opponent already.
      if (currentRowScore <= opponentRowScore) {
        Hand hand = model.getHand(currentPlayer);
        List<Card> cards = hand.getCards();
        
        // Try each card from the player's hand.
        for (Card card : cards) {
          // Check each column in the given row.
          for (int col = 0; col < model.getColumnCount(); col++) {
            if (model.canPlace(row, col, card)) {
              // For our purposes, we assume that playing a card adds its value to the row score.
              int newRowScore = currentRowScore + card.getValue();
              if (newRowScore >= opponentRowScore) {
                model.placeCard(row, col, card);
                return true;
              }
            }
          }
        }
      }
    }
    
    // No move was found meeting the criteria.
    return false;
  }
}