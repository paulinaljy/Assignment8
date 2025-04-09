package model;

import java.util.Objects;

import players.Player;

/**
 * The card for a game of Pawns Board.
 */
public class Card {
  private final String name;
  private final int cost;
  private final int value;
  private final Player owner;
  private final boolean[][] influenceGrid;

  /**
   * Constructs a Card object with the specified attributes.
   *
   * @param name the name of the card; cannot be null
   * @param cost the cost of the card in pawns; must be between 0 and 3 inclusive
   * @param value the value of the card in the game; cannot be negative
   * @param owner the owner of the card; cannot be null
   * @param influenceGrid a 2D boolean array representing the influence grid of the card
   * @throws IllegalArgumentException if cost or value is negative, or if cost is greater than 3
   * @throws NullPointerException if name or owner is null
   */
  public Card(String name, int cost, int value, Player owner, boolean[][] influenceGrid) {

    if (cost < 0 || value < 0) {
      throw new IllegalArgumentException("The cost and value of a card cannot be negative.");
    }
    if (cost > 3) {
      throw new IllegalArgumentException("Cards can only cost a maximum of 3 pawns.");
    }

    this.name = Objects.requireNonNull(name);
    this.cost = cost;
    this.value = value;
    this.owner = Objects.requireNonNull(owner);
    this.influenceGrid = influenceGrid;
  }

  /**
   * Gets the name of the card.
   *
   * @return the name of the card
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the cost of the card.
   *
   * @return the cost of the card
   */
  public int getCost() {
    return cost;
  }

  /**
   * Gets the value of the card.
   *
   * @return the value of the card
   */
  public int getValue() {
    return value;
  }

  /**
   * Gets the owner of the card.
   *
   * @return the owner of the card
   */
  public Player getOwner() {
    return owner;
  }

  /**
   * Get the influence grid of the card.
   * @return the influence grid of the card.
   */
  public boolean[][] getInfluenceGrid() {
    return influenceGrid;
  }

  /**
   * Applies the influence from the card.
   *
   * @param board the board where this method will take place
   * @param placedRow the row the card will be placed in
   * @param placedCol the column the card will be placed in
   * @param currentPlayer the player placing the card
   */
  public void applyInfluence(Board board, int placedRow, int placedCol, Player currentPlayer) {
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        if (!influenceGrid[i][j]) {
          continue;
        }

        int relativeRow = i - 2;
        int relativeCol = j - 2;

        if (currentPlayer == Player.BLUE) {
          relativeCol = -relativeCol;
        }

        int targetRow = placedRow + relativeRow;
        int targetCol = placedCol + relativeCol;

        if (!board.isValidCell(targetRow, targetCol)) {
          continue;
        }

        Cell targetCell = board.getCell(targetRow, targetCol);

        if (targetCell.hasCard()) {
          continue;
        }

        if (targetCell.isEmpty()) {
          targetCell.setPawns(1, currentPlayer);
        } else {
          if (targetCell.getOwner() == currentPlayer) {
            int newPawnCount = Math.min(3, targetCell.getPawnCount() + 1);
            targetCell.setPawns(newPawnCount, currentPlayer);
          } else {
            targetCell.setPawns(targetCell.getPawnCount(), currentPlayer);
          }
        }
      }
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Card)) {
      return false;
    }
    Card other = (Card) obj;
    return name.equals(other.name)
            && cost == other.cost
            && value == other.value
            && owner == other.owner;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, cost, value, owner);
  }
}