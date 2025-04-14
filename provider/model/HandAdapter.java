package cs3500.pawnsboard.provider.model;
import cs3500.pawnsboard.model.GameCard;
import cs3500.pawnsboard.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents an adapter for a hand used in the provider view. This class implements the provider's
 * generic Hand interface, while composing our model implementation of a player. This adapter has
 * behaviors including checking if this hand contains a card, getting the cards in this hand, and
 * getting the size of the hand.
 *
 * <p>Note: Some methods (e.g., {@code addCard} {@code removeCard}) are not implemented since they
 * are not required for view operations.
 */
public class HandAdapter implements Hand {
  private final Player player;

  /**
   * Initializes a HandAdapter with our Player.
   * @param player our player used in the game
   */
  public HandAdapter(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    }
    this.player = player;
  }

  @Override
  public void addCard(Card c) {
    // not supported by view
  }

  @Override
  public void removeCard(Card c) {
    // not supported by view
  }

  @Override
  public boolean containsCard(Card card) {
    for (GameCard c : player.getHand()) {
      Card cardAdapter = new CardAdapter(c);
      if (cardAdapter.equals(card)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public List<Card> getCards() {
    List<Card> newCards = new ArrayList<Card>();
    for (GameCard card : player.getHand()) {
      newCards.add(new CardAdapter(card));
    }
    return newCards;
  }

  @Override
  public int size() {
    return player.getHandSize();
  }

  @Override
  public Hand copy() {
    return new HandAdapter(player);
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof HandAdapter)) {
      return false;
    }
    HandAdapter that = (HandAdapter) other;
    return this.player == that.player;
  }

  @Override
  public int hashCode() {
    return Objects.hash(player);
  }
}
