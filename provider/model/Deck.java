package cs3500.pawnsboard.provider.model;

import java.util.List;

public class Deck implements MockDeckInterface {
  private final List<Card> playersDeck;

  public Deck(List<Card> playersDeck) {
    if (playersDeck == null) {
      throw new IllegalArgumentException("Deck cannot be null");
    }
    this.playersDeck = playersDeck;
  }
  @Override
  public Card drawCard() {
    return this.playersDeck.get(0);
  }

  @Override
  public int size() {
    return this.playersDeck.size();
  }

  @Override
  public int cardsRemaining() {
    return this.playersDeck.size();
  }
}
