package cs3500.pawnsboard;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;

import cs3500.pawnsboard.controller.PawnsBoardPlayerController;
import cs3500.pawnsboard.model.GameCard;
import cs3500.pawnsboard.controller.PawnsBoardDeckConfig;
import cs3500.pawnsboard.model.PawnsBoardBuilder;
import cs3500.pawnsboard.model.QueensBlood;
import cs3500.pawnsboard.player.GamePlayer;
import cs3500.pawnsboard.player.HumanPlayer;
import cs3500.pawnsboard.player.MachinePlayer;
import cs3500.pawnsboard.provider.model.ModelActionAdapter;
import cs3500.pawnsboard.provider.model.ModelActionInterface;
import cs3500.pawnsboard.provider.model.Player;
import cs3500.pawnsboard.provider.view.ViewAdapter;
import cs3500.pawnsboard.strategy.ControlBoard;
import cs3500.pawnsboard.strategy.FillFirst;
import cs3500.pawnsboard.strategy.MaxRowScore;
import cs3500.pawnsboard.view.PawnsBoardFrame;

/**
 * Represents a PawnsBoard game.
 */
public final class PawnsBoardGame {
  final static int DEFAULT_WIDTH = 5;
  final static int DEFAULT_HEIGHT = 3;

  /**
   * Runs the PawnsBoardGame given the arguments. Initialize a model with a given board width and
   * column. Reads a deck configuration file and creates a deck of card for player 1 and player
   * 2. Starts the game with the two decks of card and hand size of 5. Displays the GUI view of each
   * player, each with their respective hand. Player 1 window is displayed on the left and player 2
   * window is displayed on the right.
   * @param args an array of string with the arguments entered by the user
   */
  public static void main(String[] args) throws IOException {
    PawnsBoardBuilder builder = new PawnsBoardBuilder();
    String p1DeckPath = getArgument(args, 0, "");
    String p2DeckPath = getArgument(args, 1, "");
    String player1Type = getArgument(args, 2, "");
    String player2Type = getArgument(args, 3, "");
    builder.setWidth(getArgumentValue(getArgument(args, 4, Integer.toString(DEFAULT_WIDTH)),
            DEFAULT_WIDTH));
    builder.setHeight(getArgumentValue(getArgument(args, 5,
                    Integer.toString(DEFAULT_HEIGHT)), DEFAULT_HEIGHT));
    try {
      QueensBlood model1 = builder.build();
      ModelActionInterface model2 = new ModelActionAdapter(model1);
      File p1config = new File(p1DeckPath);
      File p2config = new File(p2DeckPath);
      List<GameCard> p1Deck = new PawnsBoardDeckConfig().loadDeckConfig(new FileReader(p1config));
      List<GameCard> p2Deck = new PawnsBoardDeckConfig().loadDeckConfig(new FileReader(p2config));
      model1.startGame(p1Deck, p2Deck, 5, false);
      PawnsBoardFrame view1 = new PawnsBoardFrame(model1, 1);
      cs3500.pawnsboard.provider.view.PawnsBoardFrame view2 =
              new cs3500.pawnsboard.provider.view.PawnsBoardFrame(model2, Player.BLUE);
      view2.setLocation(view1.getX() + view1.getWidth(), view1.getY());
      GamePlayer player1 = getGamePlayer(player1Type, model1, 1);
      GamePlayer player2 = getGamePlayer(player2Type, model1, 2);
      PawnsBoardPlayerController control1 = new PawnsBoardPlayerController(model1, player1, view1);
      PawnsBoardPlayerController control2 = new PawnsBoardPlayerController(model1, player2,
              new ViewAdapter(view2, model1));
      control1.playGame();
      control2.playGame();
      control1.itsYourTurn();
    } catch (Exception e) {
      System.out.print(e.getMessage());
    }
  }

  /**
   * Returns the argument based on the given arguments, argument number, and default value.
   * @param args arguments given in main method
   * @param argNum argument number
   * @param defaultValue default value
   * @return argument
   */
  private static String getArgument(String[] args, int argNum, String defaultValue) {
    if (argNum < args.length) {
      return args[argNum];
    } else {
      return defaultValue;
    }
  }

  /**
   * Returns the argument integer value based on the given string. If the given string is not valid,
   * the default value is returned.
   * @param argValue the argument value, either the first or second input entered
   * @param defaultValue the default board size value for either width or height
   * @return an integer value representing either the board width or height
   */
  private static int getArgumentValue(String argValue, int defaultValue) {
    try {
      return Integer.parseInt(argValue);
    } catch (InputMismatchException e) {
      return defaultValue;
    }
  }

  /**
   * Returns the player based on the given playerType, model, and player ID. If the string is
   * "human", returns a new HumanPlayer. If the string is "strategy1", returns a MachinePlayer with
   * the FillFirst strategy. If the string is "strategy2", returns a MachinePlayer with the
   * MaxRowScore strategy. If the string is "strategy3", returns a MachinePlayer with the
   * ControlBoard strategy.
   * @param playerType the player type, either "human", "strategy1", "strategy2", or "strategy3"
   * @param model the model of the game
   * @param playerID the corresponding player ID of the player
   * @return a GamePlayer either HumanPlayer or MachinePlayer
   */
  private static GamePlayer getGamePlayer(String playerType, QueensBlood model, int playerID) {
    switch (playerType) {
      case "human":
        return new HumanPlayer(model, playerID);
      case "strategy1":
        return new MachinePlayer(model, new FillFirst(), playerID);
      case "strategy2":
        return new MachinePlayer(model, new MaxRowScore(), playerID);
      case "strategy3":
        return new MachinePlayer(model, new ControlBoard(), playerID);
      default: throw new IllegalArgumentException("Player type must be human or machine with " +
              "specified strategy");
    }
  }
}

