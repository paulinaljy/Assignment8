package strategies;

import model.GameModel;

/**
 * The ComputerStrategy interface defines the contract for implementing automated strategies
 * in a game. A ComputerStrategy is responsible for analyzing the current state of the game
 * and executing a move based on its specific decision-making logic.
 * The primary method, makeMove, allows a strategy implementation to interact with the game
 * model, evaluate possible moves, and decide on the best course of action. If no valid moves
 * exist, the strategy indicates that the computer must pass its turn.
 * Implementing classes should encapsulate their specific logic for selecting moves,
 * potentially including heuristics, prioritization, or other decision-making criteria.
 */
public interface ComputerStrategy {

  /**
   * Attempts to perform a move on the board using the computer’s strategy. If a move is found and
   * executed, returns true. If no move is available (meaning the computer must pass),
   * returns false.
   *
   * @param model the current GameModel
   * @return true if a move was executed; false if no move was found
   */
  boolean makeMove(GameModel model);
}