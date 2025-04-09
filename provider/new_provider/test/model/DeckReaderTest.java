package model;


import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.junit.Assert;

import players.Player;

/**
 * Unit tests for the DeckReader class to ensure proper functionality and behavior.
 */

public class DeckReaderTest {

  @Test(expected = RuntimeException.class)
  public void testFileNotFound() {
    Player player = Player.RED;
    DeckReader reader = new DeckReader("nonexistent_file.txt", player);
    reader.readDeck();
  }

  @Test
  public void testValidDeckReading() throws IOException {
    File tempFile = File.createTempFile("validDeck", ".txt");
    try (FileWriter writer = new FileWriter(tempFile)) {
      writer.write(
              "CardA 2 3\n"
                + "XXXXX\n"
                + "XXXIX\n"
                + "XXCXX\n"
                + "XXXIX\n"
                + "XXXXX\n"
      );
    }

    Player owner = Player.BLUE;
    DeckReader reader = new DeckReader(tempFile.getAbsolutePath(), owner);
    List<Card> deck = reader.readDeck();

    Assert.assertEquals(1, deck.size());

    Card card = deck.get(0);

    Assert.assertEquals("CardA", card.getName());
    Assert.assertEquals(2, card.getCost());
    Assert.assertEquals(3, card.getValue());
    Assert.assertEquals(owner, card.getOwner());
  }

  @Test(expected = RuntimeException.class)
  public void testInvalidCardLineFormat() throws IOException {
    File tempFile = File.createTempFile("invalidCardLine", ".txt");
    try (FileWriter writer = new FileWriter(tempFile)) {
      writer.write(
              "CardA 2\n"
                + "XXXIX\n"
                + "XXCXX\n"
                + "XXXIX\n"
                + "XXCXX\n"
                + "XXXIX\n"
      );
    }

    Player owner = Player.RED;
    DeckReader reader = new DeckReader(tempFile.getAbsolutePath(), owner);
    reader.readDeck();
  }

  @Test(expected = RuntimeException.class)
  public void testInvalidGridLineLength() throws IOException {
    File tempFile = File.createTempFile("invalidGridLine", ".txt");
    try (FileWriter writer = new FileWriter(tempFile)) {
      writer.write(
              "CardA 2 3\n"
                + "XXXXX\n"
                + "XXCXX\n"
                + "XXXIX\n"
                + "XXXX\n"
      );
    }

    Player owner = Player.RED;
    DeckReader reader = new DeckReader(tempFile.getAbsolutePath(), owner);
    reader.readDeck();
  }

  @Test(expected = RuntimeException.class)
  public void testMissingCenterGrid() throws IOException {
    File tempFile = File.createTempFile("missingCenterGrid", ".txt");
    try (FileWriter writer = new FileWriter(tempFile)) {
      writer.write(
              "CardA 2 3\n"
                + "XXXIX\n"
                + "XXCXX\n"
                + "XXXXX\n"
                + "XXCXX\n"
                + "XXXIX\n"
      );
    }

    Player owner = Player.RED;
    DeckReader reader = new DeckReader(tempFile.getAbsolutePath(), owner);
    reader.readDeck();
  }

  @Test(expected = RuntimeException.class)
  public void testAdditionalCenterOutsideGrid() throws IOException {
    File tempFile = File.createTempFile("additionalCenterOutside", ".txt");
    try (FileWriter writer = new FileWriter(tempFile)) {
      writer.write(
              "CardA 2 3\n"
                + "XXXCX\n"
                + "XXCXX\n"
                + "XXXIX\n"
                + "XXCXX\n"
                + "XXXIX\n"
      );
    }

    Player owner = Player.RED;
    DeckReader reader = new DeckReader(tempFile.getAbsolutePath(), owner);
    reader.readDeck();
  }

  @Test(expected = IllegalStateException.class)
  public void testDuplicateCardAddition() throws IOException {
    File tempFile = File.createTempFile("duplicateCards", ".txt");
    try (FileWriter writer = new FileWriter(tempFile)) {
      writer.write(
              "CardA 2 3\n"
                + "XXXXX\n"
                + "XXXXX\n"
                + "XXCXX\n"
                + "XXXXX\n"
                + "XXXXX\n"
                + "CardA 2 3\n"
                + "XXXXX\n"
                + "XXXXX\n"
                + "XXCXX\n"
                + "XXXXX\n"
                + "XXXXX\n"
                + "CardA 2 3\n"
                + "XXXXX\n"
                + "XXXXX\n"
                + "XXCXX\n"
                + "XXXXX\n"
                + "XXXXX\n"
      );
    }

    Player owner = Player.BLUE;
    DeckReader reader = new DeckReader(tempFile.getAbsolutePath(), owner);
    reader.readDeck();
  }

  @Test(expected = RuntimeException.class)
  public void testNotEnoughGridLines() throws IOException {
    File tempFile = File.createTempFile("notEnoughGridLines", ".txt");
    try (FileWriter writer = new FileWriter(tempFile)) {
      writer.write(
              "CardA 2 3\n"
                + "XXXIX\n"
                + "XXCXX\n"
      );
    }

    Player owner = Player.BLUE;
    DeckReader reader = new DeckReader(tempFile.getAbsolutePath(), owner);
    reader.readDeck();
  }
}