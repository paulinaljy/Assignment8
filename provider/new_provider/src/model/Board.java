package model;


import players.Player;

/**
 * The board for a game of Pawns Board.
 */
public class Board {
  private final Cell[][] grid;
  final int rows;
  final int cols;

  /**
   * Constructs a Board with the specified number of rows and columns. Columns must be odd and
   * greater than 1; rows must be positive.
   *
   * @param rows the number of rows in the board
   * @param cols the number of columns in the board (must be odd and > 1)
   * @throws IllegalArgumentException if the dimensions are invalid
   */
  public Board(int rows, int cols) {
    if (rows <= 0) {
      throw new IllegalArgumentException("Number of rows must be greater than 0.");
    }
    if (cols <= 1 || cols % 2 == 0) {
      throw new IllegalArgumentException("Number of columns must be odd and greater than 1.");
    }
    this.rows = rows;
    this.cols = cols;
    grid = new Cell[rows][cols];

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        grid[r][c] = new Cell();
      }
    }
  }

  /**
   * Checks if the given row and column indices are valid for this board.
   *
   * @param row the row index
   * @param col the column index
   * @return true if the cell exists within the board, false otherwise.
   */
  public boolean isValidCell(int row, int col) {
    return (row >= 0 && row < rows) && (col >= 0 && col < cols);
  }

  /**
   * Returns the cell at the specified row and column.
   *
   * @param row the row index
   * @param col the column index
   * @return the Cell object at that position.
   * @throws IndexOutOfBoundsException if the cell indices are out of bounds.
   */
  public Cell getCell(int row, int col) {
    if (!isValidCell(row, col)) {
      throw new IndexOutOfBoundsException("Cell indices are out of bounds.");
    }
    return grid[row][col];
  }

  /**
   * Initializes the board's starting state by placing one pawn in each cell of the
   * first column for the Red player and one pawn in each cell of the last column for the
   * Blue player.
   *
   * @param red  the Red player
   * @param blue the Blue player
   */
  public void initializeInitialPawns(Player red, Player blue) {
    for (int r = 0; r < rows; r++) {
      grid[r][0].setPawns(1, red);
      grid[r][cols - 1].setPawns(1, blue);
    }
  }

  /**
   * Returns the number of rows in the board.
   *
   * @return the number of rows.
   */
  public int getRows() {
    return rows;
  }

  /**
   * Returns the number of columns in the board.
   *
   * @return the number of columns.
   */
  public int getCols() {
    return cols;
  }

  /**
   * Attempts to place a card on the specified cell. The cell must be owned by the current player
   * and contain enough pawns to cover the card's cost. Once the card is placed, the pawns in the
   * cell are removed and the card's influence is applied.
   *
   * @param row the row index where the card is to be placed
   * @param col the column index where the card is to be placed
   * @param card the card to be placed
   * @param currentPlayer the player who is placing the card
   * @throws IllegalArgumentException if the cell is invalid or doesn't meet placement requirements
   */
  public void placeCard(int row, int col, Card card, Player currentPlayer) {

    if (!isValidCell(row, col)) {
      throw new IllegalArgumentException("Invalid cell coordinates.");
    }

    Cell cell = getCell(row, col);

    if (cell.hasCard()) {
      throw new IllegalArgumentException("Cell already contains a card.");
    }

    if (cell.getOwner() != currentPlayer || cell.getPawnCount() < card.getCost()) {
      throw new IllegalArgumentException("Cannot place card: insufficient pawns or "
              + "cell not owned by the player.");
    }

    cell.setCard(card);

    card.applyInfluence(this, row, col, currentPlayer);
  }
}