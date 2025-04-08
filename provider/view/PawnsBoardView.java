package cs3500.pawnsboard.provider.view;


import cs3500.pawnsboard.provider.model.Board;
import cs3500.pawnsboard.provider.model.Cell;
import cs3500.pawnsboard.provider.model.ModelActionInterface;
import cs3500.pawnsboard.provider.players.Player;

/**
 * The view for a game of PawnsBoard.
 */
public class PawnsBoardView {

  private final ModelActionInterface model;

  /**
   * Constructs the textual view using the given game model.
   *
   * @param model the GameModel object representing the game state.
   */
  public PawnsBoardView(ModelActionInterface model) {
    this.model = model;
  }

  /**
   * Builds a string representation of the current board state. Each row starts with the red
   * player's row score, then the cell representations, and ends with the blue player's row score.
   *
   * <p>Example output for a 3-row board:
   *
   * <p>2 RR__3 0
   *
   * <p>0 2__1B 1
   *
   * <p>0 11_1B 3
   *
   * @return the textual representation of the board.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    Board board = model.getBoard();
    int rows = board.getRows();
    int cols = board.getCols();

    for (int r = 0; r < rows; r++) {
      int redRowScore = 0;
      int blueRowScore = 0;

      StringBuilder rowStr = this.buildString(cols, board, r, redRowScore, blueRowScore);
      StringBuilder full = new StringBuilder();
      full.append(redRowScore).append(" ").append(rowStr).append(" ").append(blueRowScore);

      sb.append(full).append("\n");
    }

    int redScore = model.calculateScore(Player.RED);
    int blueScore = model.calculateScore(Player.BLUE);
    sb.append(String.format("Current Scores => RED: %d, BLUE: %d\n", redScore, blueScore));

    if (!model.isGameOver()) {
      sb.append("Current Turn: ").append(model.getCurrentPlayer()).append("\n");
    } else {
      sb.append("GAME OVER!\n");
    }

    return sb.toString();
  }

  private StringBuilder buildString(int cols, Board board,
                                    int r, int redRowScore, int blueRowScore) {
    StringBuilder rowStr = new StringBuilder();
    for (int c = 0; c < cols; c++) {
      Cell cell = board.getCell(r, c);
      if (cell.hasCard()) {
        if (cell.getCard().getOwner() == Player.RED) {
          rowStr.append("R");
          redRowScore += cell.getCard().getValue();
        } else {
          rowStr.append("B");
          blueRowScore += cell.getCard().getValue();
        }
      } else if (!cell.isEmpty()) {
        rowStr.append(cell.getPawnCount());
      } else {
        rowStr.append("_");
      }
    }
    return rowStr;
  }
}

