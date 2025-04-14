package cs3500.pawnsboard.provider.model;

import java.awt.*;
import java.util.Objects;

import cs3500.pawnsboard.model.GameCard;
import cs3500.pawnsboard.model.Position;

/**
 * Represents an adapter for a card used in the provider view. This class implements the provider's
 * generic Card interface, while composing our model implementation of a card, which is a GameCard.
 * This adapter has behaviors including getting name, cost, value, owner, and influence grid of the
 * card.
 *
 */
public class CardAdapter implements Card {
  private final GameCard card;

  /**
   * Initializes a CardAdapter with our GameCard.
   * @param card our GameCard used in the game
   */
  public CardAdapter(GameCard card) {
    if (card == null) {
      throw new IllegalArgumentException("Card cannot be null");
    }
    this.card = card;
  }
  @Override
  public String getName() {
    return card.getName();
  }

  @Override
  public int getCost() {
    return card.getCost();
  }

  @Override
  public int getValue() {
    return card.getValue();
  }

  @Override
  public Player getOwner() {
    if (card.getOwnedColor().equals(Color.RED)) {
      return Player.RED;
    } else if (card.getOwnedColor().equals(Color.BLUE)){
      return Player.BLUE;
    } else {
      return null;
    }
  }

  @Override
  public boolean[][] getInfluenceGrid() {
    boolean[][] grid = new boolean[5][5];
    for (Position pos : card.getPositions()) {
      int row = pos.getRowDelta() + 2;
      int col = pos.getColDelta() + 2;
      if (row >= 0 && row < 5 && col >= 0 && col < 5) {
        grid[row][col] = true;
      }
    }
    return grid;
  }

  @Override
  public Card copy() {
    return new CardAdapter(card);
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof CardAdapter)) {
      return false;
    }
    CardAdapter that = (CardAdapter) other;
    return this.card == that.card;
  }

  @Override
  public int hashCode() {
    return Objects.hash(card);
  }
}
