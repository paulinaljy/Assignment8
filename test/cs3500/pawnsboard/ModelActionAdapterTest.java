package cs3500.pawnsboard.test.cs3500.pawnsboard;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import cs3500.pawnsboard.MockPawnsBoardView;
import cs3500.pawnsboard.controller.DeckConfiguration;
import cs3500.pawnsboard.controller.PawnsBoardDeckConfig;
import cs3500.pawnsboard.controller.PawnsBoardPlayerController;
import cs3500.pawnsboard.model.Cell;
import cs3500.pawnsboard.model.EmptyCell;
import cs3500.pawnsboard.model.GameCard;
import cs3500.pawnsboard.model.ModelActions;
import cs3500.pawnsboard.model.Pawns;
import cs3500.pawnsboard.model.PawnsBoardModel;
import cs3500.pawnsboard.model.Position;
import cs3500.pawnsboard.model.QueensBlood;
import cs3500.pawnsboard.player.GamePlayer;
import cs3500.pawnsboard.player.HumanPlayer;
import cs3500.pawnsboard.provider.model.Board;
import cs3500.pawnsboard.provider.model.BoardAdapter;
import cs3500.pawnsboard.provider.model.Card;
import cs3500.pawnsboard.provider.model.CardAdapter;
import cs3500.pawnsboard.provider.model.CellAdapter;
import cs3500.pawnsboard.provider.model.Hand;
import cs3500.pawnsboard.provider.model.HandAdapter;
import cs3500.pawnsboard.provider.model.ModelActionAdapter;
import cs3500.pawnsboard.provider.model.ModelActionInterface;
import cs3500.pawnsboard.provider.model.Player;
import cs3500.pawnsboard.view.PawnsBoardView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Represents examples and tests for ModelActionAdapter.
 */
public class ModelActionAdapterTest {
  private List<ArrayList<Cell>> board;
  private Position leftSecurity;
  private Position rightSecurity;
  private Position topSecurity;
  private Position bottomSecurity;
  private ArrayList<Position> securityInfluenceGrid;
  private EmptyCell emptyCell;
  private Pawns redPawns;
  private Pawns bluePawns;
  private GameCard security;
  private GameCard bee;
  private GameCard sweeper;
  private GameCard crab;
  private GameCard queen;
  private GameCard mandragora;
  private GameCard trooper;
  private GameCard cavestalker;
  private GameCard lobber;
  private QueensBlood game1;
  private ArrayList<GameCard> p1Deck;
  private ArrayList<GameCard> p2Deck;
  private cs3500.pawnsboard.model.Player player1;
  private cs3500.pawnsboard.model.Player player2;
  private DeckConfiguration deckConfig;
  private ModelActions observer1;
  private ModelActions observer2;

  @Before
  public void setup() {
    leftSecurity = new Position(0, -1); // (2,1)
    rightSecurity = new Position(0, 1); // (2,3)
    topSecurity = new Position(-1, 0); // (1,2)
    bottomSecurity = new Position(1, 0); // (3,2)
    securityInfluenceGrid = new ArrayList<Position>(Arrays.asList(topSecurity, leftSecurity,
            rightSecurity, bottomSecurity));

    Position topMandragora = new Position(-1, 0); // (1,2)
    Position right1Mandragora = new Position(0, 1); // (2,3)
    Position right2Mandragora = new Position(0, 2); // (2,4)
    ArrayList<Position> mandragoraInfluenceGrid = new ArrayList<Position>(Arrays.asList(
            topMandragora, right1Mandragora, right2Mandragora));

    Position topBee = new Position(-2, 0);
    Position bottomBee = new Position(2, 0);
    ArrayList<Position> beeInfluenceGrid = new ArrayList<Position>(Arrays.asList(
            topBee, bottomBee));

    Position top1Sweeper = new Position(-1, -1);
    Position top2Sweeper = new Position(-1, 0);
    Position bottom1Sweeper = new Position(1, -1);
    Position bottom2Sweeper = new Position(1, 0);
    ArrayList<Position> sweeperInfluenceGrid = new ArrayList<Position>(Arrays.asList(
            top1Sweeper, top2Sweeper, bottom1Sweeper, bottom2Sweeper));

    Position leftCrab = new Position(0, -1); // (2,1)
    Position rightCrab = new Position(0, 1); // (2,3)
    Position topCrab = new Position(-1, 0); // (1,2)
    ArrayList<Position> crabInfluenceGrid = new ArrayList<Position>(
            Arrays.asList(topCrab, leftCrab, rightCrab));

    Position topQueen = new Position(-2, 0); // (0,2)
    ArrayList<Position> queenInfluenceGrid = new ArrayList<Position>(Arrays.asList(topQueen));

    Position top1Trooper = new Position(-2, 0);
    Position top2Trooper = new Position(-1, 1);
    Position rightTrooper = new Position(0, 1);
    Position bottom1Trooper = new Position(1, 0);
    Position bottom2Trooper = new Position(2, 0);
    ArrayList<Position> trooperInfluenceGrid = new ArrayList<Position>(Arrays.asList(
            top1Trooper, top2Trooper, rightTrooper, bottom1Trooper, bottom2Trooper));

    Position topCaveStalker = new Position(-2, 0);
    Position right1CaveStalker = new Position(-1, 1);
    Position right2CaveStalker = new Position(0, 1);
    Position right3CaveStalker = new Position(1, 1);
    Position bottomCaveStalker = new Position(2, 0);
    ArrayList<Position> cavestalkerInfluenceGrid = new ArrayList<Position>(Arrays.asList(
            topCaveStalker, right1CaveStalker, right2CaveStalker,
            right3CaveStalker, bottomCaveStalker));

    Position rightLobber = new Position(0, 2);
    ArrayList<Position> lobberInfluenceGrid = new ArrayList<Position>(Arrays.asList(rightLobber));

    emptyCell = new EmptyCell();
    redPawns = new Pawns(Color.red);
    bluePawns = new Pawns(Color.blue);

    security = new GameCard("Security", GameCard.Cost.ONE, 1,
            securityInfluenceGrid);
    bee = new GameCard("Bee", GameCard.Cost.ONE, 1, beeInfluenceGrid);
    sweeper = new GameCard("Sweeper", GameCard.Cost.TWO, 2, sweeperInfluenceGrid);
    crab = new GameCard("Crab", GameCard.Cost.ONE, 1, crabInfluenceGrid);
    queen = new GameCard("Queen", GameCard.Cost.ONE, 1, queenInfluenceGrid);
    mandragora = new GameCard("Mandragora", GameCard.Cost.ONE, 1,
            mandragoraInfluenceGrid);
    trooper = new GameCard("Trooper", GameCard.Cost.TWO, 3, trooperInfluenceGrid);
    cavestalker = new GameCard("Cavestalker", GameCard.Cost.THREE, 4,
            cavestalkerInfluenceGrid);
    lobber = new GameCard("Lobber", GameCard.Cost.TWO, 1, lobberInfluenceGrid);

    p1Deck = new ArrayList<GameCard>(Arrays.asList(security, bee, sweeper, crab, mandragora, queen,
            trooper, cavestalker, lobber, security, bee, sweeper, crab, mandragora, queen, trooper,
            cavestalker, lobber));

    p2Deck = new ArrayList<GameCard>(Arrays.asList(security, bee, sweeper, crab, mandragora, queen,
            trooper, cavestalker, lobber, security, bee, sweeper, crab, mandragora, queen,
            trooper, cavestalker, lobber));

    ArrayList<Cell> row = new ArrayList<Cell>(Arrays.asList(redPawns, emptyCell, emptyCell,
            emptyCell, bluePawns));
    board = new ArrayList<ArrayList<Cell>>(Arrays.asList(row, row, row));

    deckConfig = new PawnsBoardDeckConfig();

    game1 = new PawnsBoardModel(5, 3, new Random(6), deckConfig);
    player1 = new cs3500.pawnsboard.model.Player(Color.RED, p1Deck, 5, new Random(6), true);
    player2 = new cs3500.pawnsboard.model.Player(Color.BLUE, p2Deck, 5, new Random(6), true);

    PawnsBoardView view1 = new MockPawnsBoardView(game1, 1);
    GamePlayer humanPlayer1 = new HumanPlayer(game1, 1);
    observer1 = new PawnsBoardPlayerController(game1, humanPlayer1, view1);
    PawnsBoardView view2 = new MockPawnsBoardView(game1, 2);
    GamePlayer humanPlayer2 = new HumanPlayer(game1, 2);
    observer2 = new PawnsBoardPlayerController(game1, humanPlayer2, view2);
  }

  // TESTS FOR MODEL ACTION ADAPTER
  @Test
  public void testInvalidConstructor() {
    assertThrows(IllegalArgumentException.class, () -> {
      new ModelActionAdapter(null);
    });
  }

  @Test
  public void testGetBoard() {
    ModelActionInterface model = new ModelActionAdapter(game1);
    Board boardAdapter = new BoardAdapter(board);
    assertEquals(boardAdapter.toString(), model.getBoard().toString());
  }

  @Test
  public void testGetCurrentPlayer() {
    ModelActionInterface model = new ModelActionAdapter(game1);
    game1.startGame(p1Deck, p2Deck, 5, false);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);
    assertEquals(Player.RED, model.getCurrentPlayer());

    game1.pass();
    assertEquals(Player.BLUE, model.getCurrentPlayer());
  }

  @Test
  public void testGetOpponent() {
    ModelActionInterface model = new ModelActionAdapter(game1);
    game1.startGame(p1Deck, p2Deck, 5, false);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);
    assertEquals(Player.BLUE, model.getOpponent());

    game1.pass();
    assertEquals(Player.RED, model.getOpponent());
  }

  @Test
  public void testCalculateScore() {
    ModelActionInterface model = new ModelActionAdapter(game1);
    game1.startGame(p1Deck, p2Deck, 5, false);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);
    assertEquals(0, model.calculateScore(Player.RED));

    game1.pass();
    game1.placeCardInPosition(0, 0, 4);
    assertEquals(1, model.calculateScore(Player.BLUE));
  }

  @Test
  public void testCalculateRowScore() {
    ModelActionInterface model = new ModelActionAdapter(game1);
    game1.startGame(p1Deck, p2Deck, 5, false);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);
    assertEquals(0, model.calculateRowScore(Player.RED, 0));

    game1.pass();
    game1.placeCardInPosition(0, 0, 4);
    assertEquals(1, model.calculateRowScore(Player.BLUE, 0));
  }

  @Test
  public void testIsGameOver() {
    ModelActionInterface model = new ModelActionAdapter(game1);
    game1.startGame(p1Deck, p2Deck, 5, false);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);
    assertFalse(model.isGameOver());

    game1.pass();
    game1.pass();
    assertTrue(model.isGameOver());
  }

  @Test
  public void testGetHand() {
    ModelActionInterface model = new ModelActionAdapter(game1);
    game1.startGame(p1Deck, p2Deck, 5, false);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);

    assertEquals(new HandAdapter(player1).toString(), model.getHand(Player.RED).toString());
    assertEquals(new HandAdapter(player2).toString(), model.getHand(Player.BLUE).toString());
  }

  @Test
  public void testGetRowCount() {
    ModelActionInterface model = new ModelActionAdapter(game1);
    assertEquals(3, model.getRowCount());
  }

  @Test
  public void testGetColCount() {
    ModelActionInterface model = new ModelActionAdapter(game1);
    assertEquals(5, model.getColumnCount());
  }

  @Test
  public void testGetCardAt() {
    ModelActionInterface model = new ModelActionAdapter(game1);
    game1.startGame(p1Deck, p2Deck, 5, false);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);

    game1.placeCardInPosition(0, 0, 0);
    assertEquals(new CardAdapter(security), model.getCardAt(0, 0));
    assertEquals(null, model.getCardAt(1, 0));
    assertEquals(null, model.getCardAt(0, 3));
  }

  @Test
  public void testCanPlaceCard() {
    ModelActionInterface model = new ModelActionAdapter(game1);
    game1.startGame(p1Deck, p2Deck, 5, false);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);

    assertFalse(model.canPlace(0, 1, new CardAdapter(security)));
  }

  @Test
  public void testCanPlaceCardIdx() {
    ModelActionInterface model = new ModelActionAdapter(game1);
    game1.startGame(p1Deck, p2Deck, 5, false);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);

    assertFalse(model.canPlace(0, 0, 6));
    assertTrue(model.canPlace(0, 0, 1));
  }

  @Test
  public void testGetWinnerTie() {
    ModelActionInterface model = new ModelActionAdapter(game1);
    game1.startGame(p1Deck, p2Deck, 5, false);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);

    assertEquals(null, model.getWinner());
  }

  @Test
  public void testGetWinnerPlayer1() {
    ModelActionInterface model = new ModelActionAdapter(game1);
    game1.startGame(p1Deck, p2Deck, 5, false);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);

    game1.placeCardInPosition(0, 0, 0);

    assertEquals(Player.RED, model.getWinner());
  }

  @Test
  public void testGetWinnerPlayer2() {
    ModelActionInterface model = new ModelActionAdapter(game1);
    game1.startGame(p1Deck, p2Deck, 5, false);
    game1.subscribe(observer1, 1);
    game1.subscribe(observer2, 2);

    game1.pass();
    game1.placeCardInPosition(0, 0, 4);

    assertEquals(Player.BLUE, model.getWinner());
  }

  // TESTS FOR BOARD ADAPTER
  @Test
  public void testBoardInvalidConstructor() {
    assertThrows(IllegalArgumentException.class, () -> {
      new BoardAdapter(null);
    });
  }

  @Test
  public void testBoardIsValidPlacement() {
    Board boardAdapter = new BoardAdapter(board);
    assertTrue(boardAdapter.isValidPlacement(0, 0));
    assertFalse(boardAdapter.isValidPlacement(-1, 1));
    assertFalse(boardAdapter.isValidPlacement(3, 1));
    assertFalse(boardAdapter.isValidPlacement(2, -2));
    assertFalse(boardAdapter.isValidPlacement(0, 5));
  }

  @Test
  public void testBoardCalculateRowScore() {
    Board boardAdapter = new BoardAdapter(board);
    assertEquals(0, boardAdapter.calculateRowScore(Player.RED, 0));
  }

  @Test
  public void testBoardCopy() {
    Board boardAdapter = new BoardAdapter(board);
    assertEquals(boardAdapter, boardAdapter.copy());
  }

  @Test
  public void testBoardGetCell() {
    Board boardAdapter = new BoardAdapter(board);
    CellAdapter cellAdapter = new CellAdapter(redPawns);
    assertEquals(cellAdapter, boardAdapter.getCell(0, 0));
  }

  @Test
  public void testBoardGetRows() {
    Board boardAdapter = new BoardAdapter(board);
    assertEquals(3, boardAdapter.getRows());
  }

  @Test
  public void testBoardGetCols() {
    Board boardAdapter = new BoardAdapter(board);
    assertEquals(5, boardAdapter.getCols());
  }

  // TESTS FOR CARD ADAPTER
  @Test
  public void testCardInvalidConstructor() {
    assertThrows(IllegalArgumentException.class, () -> {
      new CardAdapter(null);
    });
  }

  @Test
  public void testCardGetName() {
    Card cardAdapter = new CardAdapter(security);
    assertEquals("Security", cardAdapter.getName());
  }

  @Test
  public void testCardGetCost() {
    Card cardAdapter = new CardAdapter(security);
    assertEquals(1, cardAdapter.getCost());
  }

  @Test
  public void testCardGetValue() {
    Card cardAdapter = new CardAdapter(security);
    assertEquals(1, cardAdapter.getValue());
  }

  @Test
  public void testCardGetOwner() {
    security.setColor(Color.RED);
    Card cardAdapter1 = new CardAdapter(security);
    assertEquals(Player.RED, cardAdapter1.getOwner());

    mandragora.setColor(Color.BLUE);
    Card cardAdapter2 = new CardAdapter(mandragora);
    assertEquals(Player.BLUE, cardAdapter2.getOwner());

    Card cardAdapter3 = new CardAdapter(bee);
    assertEquals(null, cardAdapter3.getOwner());
  }

  @Test
  public void testCardGetInfluenceGrid() {
    boolean[][] grid = new boolean[5][5];
    grid[0][2] = true;
    grid[4][2] = true;
    Card cardAdapter = new CardAdapter(bee);
    assertEquals(grid, cardAdapter.getInfluenceGrid());
  }

  @Test
  public void testCardCopy() {
    Card cardAdapter = new CardAdapter(security);
    assertEquals(cardAdapter, cardAdapter.copy());
  }

  // TESTS FOR CELL ADAPTER
  @Test
  public void testCellInvalidConstructor() {
    assertThrows(IllegalArgumentException.class, () -> {
      new CellAdapter(null);
    });
  }

  @Test
  public void testCellIsEmpty() {
    cs3500.pawnsboard.provider.model.Cell emptyCellAdapter = new CellAdapter(emptyCell);
    assertTrue(emptyCellAdapter.isEmpty());

    cs3500.pawnsboard.provider.model.Cell pawnsCellAdapter = new CellAdapter(redPawns);
    assertFalse(pawnsCellAdapter.isEmpty());

    cs3500.pawnsboard.provider.model.Cell cardCellAdapter = new CellAdapter(security);
    assertFalse(cardCellAdapter.isEmpty());
  }

  @Test
  public void testCellCopy() {
    cs3500.pawnsboard.provider.model.Cell emptyCellAdapter = new CellAdapter(emptyCell);
    assertEquals(emptyCellAdapter, emptyCellAdapter.copy());
  }

  @Test
  public void testCellHasCard() {
    cs3500.pawnsboard.provider.model.Cell emptyCellAdapter = new CellAdapter(emptyCell);
    assertFalse(emptyCellAdapter.hasCard());

    cs3500.pawnsboard.provider.model.Cell pawnsCellAdapter = new CellAdapter(redPawns);
    assertFalse(pawnsCellAdapter.hasCard());

    cs3500.pawnsboard.provider.model.Cell cardCellAdapter = new CellAdapter(security);
    assertTrue(cardCellAdapter.hasCard());
  }

  @Test
  public void testCellGetPawnCount() {
    cs3500.pawnsboard.provider.model.Cell emptyCellAdapter = new CellAdapter(emptyCell);
    assertEquals(0, emptyCellAdapter.getPawnCount());

    cs3500.pawnsboard.provider.model.Cell pawnsCellAdapter = new CellAdapter(redPawns);
    assertEquals(1, pawnsCellAdapter.getPawnCount());
  }

  @Test
  public void testCellGetOwner() {
    cs3500.pawnsboard.provider.model.Cell emptyCellAdapter = new CellAdapter(emptyCell);
    assertEquals(null, emptyCellAdapter.getOwner());

    cs3500.pawnsboard.provider.model.Cell pawnsCellAdapter = new CellAdapter(redPawns);
    assertEquals(Player.RED, pawnsCellAdapter.getOwner());

    security.setColor(Color.BLUE);
    cs3500.pawnsboard.provider.model.Cell cardCellAdapter = new CellAdapter(security);
    assertEquals(Player.BLUE, cardCellAdapter.getOwner());
  }

  @Test
  public void testCellGetCard() {
    cs3500.pawnsboard.provider.model.Cell emptyCellAdapter = new CellAdapter(emptyCell);
    assertEquals(null, emptyCellAdapter.getCard());

    cs3500.pawnsboard.provider.model.Cell pawnsCellAdapter = new CellAdapter(redPawns);
    assertEquals(null, pawnsCellAdapter.getCard());

    cs3500.pawnsboard.provider.model.Cell cardCellAdapter = new CellAdapter(security);
    assertEquals(new CardAdapter(security), cardCellAdapter.getCard());
  }

  // TESTS FOR HAND ADAPTER
  @Test
  public void testHandInvalidConstructor() {
    assertThrows(IllegalArgumentException.class, () -> {
      new HandAdapter(null);
    });
  }

  @Test
  public void testHandContainsCard() {
    Hand handAdapter = new HandAdapter(player1);
    assertTrue(handAdapter.containsCard(new CardAdapter(queen)));
    assertFalse(handAdapter.containsCard(new CardAdapter(security)));
  }

  @Test
  public void testHandGetCards() {
    Hand handAdapter = new HandAdapter(player1);
    Card cavestalkerAdapter = new CardAdapter(cavestalker);
    Card beeAdapter = new CardAdapter(bee);
    Card sweeperAdapter = new CardAdapter(sweeper);
    Card mandragoraAdapter = new CardAdapter(mandragora);
    Card queenAdapter = new CardAdapter(queen);
    ArrayList<Card> cards = new ArrayList<Card>(Arrays.asList(cavestalkerAdapter, beeAdapter,
            sweeperAdapter, mandragoraAdapter, queenAdapter));

    assertEquals(cards, handAdapter.getCards());
  }

  @Test
  public void testHandSize() {
    Hand handAdapter = new HandAdapter(player1);
    assertEquals(5, handAdapter.size());
  }

  @Test
  public void testHandCopy() {
    Hand handAdapter = new HandAdapter(player1);
    assertEquals(handAdapter, handAdapter.copy());
  }
}
