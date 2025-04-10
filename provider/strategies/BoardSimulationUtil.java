package cs3500.pawnsboard.provider.strategies;

import cs3500.pawnsboard.provider.players.Player;

/**
 * Utility methods to simulate moves on a copy of the board.
 */
public class BoardSimulationUtil {

  /**
   * Creates a deep copy of the given board.
   *
   * @param original the original board.
   * @return a copy of the board.
   */
  public static Board copyBoard(Board original) {
    Board copy = new Board(original.getRows(), original.getCols());
    for (int r = 0; r < original.getRows(); r++) {
      for (int c = 0; c < original.getCols(); c++) {
        Cell originalCell = original.getCell(r, c);
        Cell copyCell = copy.getCell(r, c);
        if (originalCell.hasCard()) {
          // Use the card as-is (cards are effectively immutable for our purposes).
          copyCell.setCard(originalCell.getCard());
        } else if (!originalCell.isEmpty()) {
          copyCell.setPawns(originalCell.getPawnCount(), originalCell.getOwner());
        }
      }
    }
    return copy;
  }

  /**
   * Counts the number of cells owned by the given player on the board.
   * A cell is considered owned if it contains either a card whose owner is the player or
   * pawns belonging to that player.
   *
   * @param board  the board to inspect.
   * @param player the player whose ownership is being counted.
   * @return the number of cells controlled by the player.
   */
  public static int countCellsOwned(Board board, Player player) {
    int count = 0;
    for (int r = 0; r < board.getRows(); r++) {
      for (int c = 0; c < board.getCols(); c++) {
        Cell cell = board.getCell(r, c);
        if (cell.hasCard()) {
          if (cell.getCard().getOwner() == player) {
            count++;
          }
        } else if (!cell.isEmpty()) {
          if (cell.getOwner() == player) {
            count++;
          }
        }
      }
    }
    return count;
  }
}