package cs3500.pawnsboard.provider.model;

import java.util.ArrayList;
import java.util.List;

public class Hand implements MockHandInterface {
  private final List<Card> playersHand;

  public Hand() {
    this.playersHand = new ArrayList<Card>();
  }
  @Override
  public void addCard(Card c) {
    this.playersHand.add(c);
  }

  @Override
  public void removeCard(Card c) {
    this.playersHand.remove(c);
  }

  @Override
  public List<Card> getCards() {
    return new ArrayList<Card>(this.playersHand);
  }

  @Override
  public int size() {
    return this.playersHand.size();
  }
}
