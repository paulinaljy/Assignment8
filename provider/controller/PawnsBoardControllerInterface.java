package cs3500.pawnsboard.provider.controller;


/**
 * The interface for the controller in the PawnsBoard game.
 * It declares the entry point for starting the game.
 */
public interface PawnsBoardControllerInterface {

  /**
   * Starts the game. Implementations should handle the game loop and
   * delegate actions to the model.
   */
  void startGame();
}