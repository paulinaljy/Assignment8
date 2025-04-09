package model;

import org.junit.Test;
import java.util.Collections;

import players.Player;


/**
 * Unit tests for the PawnsBoardModel class to ensure proper functionality and behavior.
 */

public class PawnsBoardModelTest {

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceCardNotInHand() {
    int rows = 5;
    int cols = 5;
    int initialHandSize = 0;

    Deck redDeck = new Deck(Collections.singletonList(new Card("Red Card", 1, 3,
            Player.RED, new boolean[3][3])));
    Deck blueDeck = new Deck(Collections.singletonList(new Card("Blue Card", 1, 3,
            Player.BLUE, new boolean[3][3])));

    PawnsBoardModel model = new PawnsBoardModel(rows, cols, redDeck, blueDeck);

    Card invalidCard = new Card("Invalid Card", 1, 2, Player.RED, new boolean[3][3]);
    model.placeCard(0, 0, invalidCard);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceCardWhenGameOver() {
    int rows = 5;
    int cols = 5;
    int initialHandSize = 1;

    Deck redDeck = new Deck(Collections.singletonList(new Card("Red Card", 1, 3,
            Player.RED, new boolean[3][3])));
    Deck blueDeck = new Deck(Collections.singletonList(new Card("Blue Card", 1, 3,
            Player.BLUE, new boolean[3][3])));

    PawnsBoardModel model = new PawnsBoardModel(rows, cols, redDeck, blueDeck);

    model.passTurn();
    model.passTurn();

    Card cardToPlace = new Card("Red Card", 1, 3, Player.RED, new boolean[3][3]);
    model.placeCard(0, 0, cardToPlace);
  }
}