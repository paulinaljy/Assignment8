package cs3500.pawnsboard.provider.strategies;

import java.util.List;
import cs3500.pawnsboard.provider.model.GameModel;
import cs3500.pawnsboard.provider.players.Player;

/**
 * The FillFirstStrategy class implements the ComputerStrategy interface and defines a strategy
 * where the computer iterates through its hand and the game board in row-major order to find
 * and execute the first valid move. If no valid move is found, the computer passes its turn.
 */
public class FillFirstStrategy implements ComputerStrategy {

  /**
   * This strategy iterates over the current player's hand and the board cells (in row-major order)
   * and returns the first combination where the card can be played.
   */
  @Override
  public boolean makeMove(GameModel model) {
    Player currentPlayer = model.getCurrentPlayer();
    Hand hand = model.getHand(currentPlayer);
    List<Card> cards = hand.getCards();
    
    // Traverse hand cards in the order they occur.
    for (int cardIndex = 0; cardIndex < cards.size(); cardIndex++) {
      Card card = cards.get(cardIndex);
      // Traverse the board in row-major order.
      for (int row = 0; row < model.getRowCount(); row++) {
        for (int col = 0; col < model.getColumnCount(); col++) {
          if (model.canPlace(row, col, card)) {
            // This is the first legal play. Place the card.
            model.placeCard(row, col, card);
            return true;
          }
        }
      }
    }
    
    // If no move found, the computer must pass.
    return false;
  }
}