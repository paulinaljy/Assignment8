import java.util.List;

import controller.PawnsBoardController;
import model.Card;
import model.Deck;
import model.DeckReader;
import players.HumanPlayer;
import model.ModelActionInterface;
import model.PawnsBoardModel;
import players.Playable;
import players.Player;
import players.Strategy1Player;
import players.Strategy2Player;
import players.Strategy3Player;
import view.PawnsBoardFrame;
import view.PawnsView;

/**
 * The class to start the board game.
 */
public final class PawnsBoardGame {

  /**
   * The main method to execute the game simulation. It initializes the game components,
   * implements the game loop, and displays the game state and results.
   * @param args the command-line arguments (not utilized in this program).
   */
  public static void main(String[] args) {
    if (args.length != 4) {
      System.out.println("Usage: java -jar pawnsboard.jar <red_deck_path> " +
              "<blue_deck_path> <red_player> <blue_player>");
      System.out.println("Player types: human, strategy1, strategy2, strategy3");
      System.exit(1);
    }

    String redDeckPath = args[0];
    String blueDeckPath = args[1];
    String redPlayerType = args[2];
    String bluePlayerType = args[3];

    // Read decks
    DeckReader redReader = new DeckReader(redDeckPath, Player.RED);
    List<Card> redCards = redReader.readDeck();
    Deck redDeck = new Deck(redCards);

    DeckReader blueReader = new DeckReader(blueDeckPath, Player.BLUE);
    List<Card> blueCards = blueReader.readDeck();
    Deck blueDeck = new Deck(blueCards);

    // Create the model
    PawnsBoardModel mutableModel = new PawnsBoardModel(5, 7, redDeck, blueDeck);
    ModelActionInterface model = mutableModel;

    Playable redPlayer = createPlayer(redPlayerType, model, Player.RED);
    Playable bluePlayer = createPlayer(bluePlayerType, model, Player.BLUE);

    // Create two views
    PawnsView redView = new PawnsBoardFrame(model, Player.RED);
    PawnsView blueView = new PawnsBoardFrame(model, Player.BLUE);

    // Create two controllers
    PawnsBoardController redController =
            new PawnsBoardController(model, redPlayer, redView, Player.RED);
    PawnsBoardController blueController =
            new PawnsBoardController(model, bluePlayer, blueView, Player.BLUE);

    // Start both controllers
    redController.startGame();
    blueController.startGame();

    // Make windows visible
    redView.makeVisible();
    blueView.makeVisible();

    // Start the game with initial hands
    mutableModel.gameStart(5);
  }

  private static Playable createPlayer(String playerType,
                                       ModelActionInterface model, Player color) {
    switch (playerType.toLowerCase()) {
      case "human":
        return new HumanPlayer();
      case "strategy1":
        return new Strategy1Player(model, color);
      case "strategy2":
        return new Strategy2Player(model, color);
      case "strategy3":
        return new Strategy3Player(model, color);
      default:
        throw new IllegalArgumentException("Unknown player type: " + playerType);
    }
  }
}