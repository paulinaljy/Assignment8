package cs3500.pawnsboard.provider.model;
import cs3500.pawnsboard.model.GameCard;
import cs3500.pawnsboard.model.Player;

import java.util.ArrayList;
import java.util.List;

public class HandAdapter implements Hand {
  private final Player player;

  public HandAdapter(Player player) {
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
}
