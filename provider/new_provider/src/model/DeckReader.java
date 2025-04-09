package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import players.Player;

/**
 * Reads a deck configuration file and returns a list of Card objects for a given owner.
 * Format per card:
 *   CARD_NAME COST VALUE
 *   ROW_0 (5 chars)
 *   ROW_1 (5 chars)
 *   ROW_2 (5 chars)
 *   ROW_3 (5 chars)
 *   ROW_4 (5 chars)
 * 'I' => influences that position (true)
 * 'X' => no influence (false)
 * 'C' => the center, also no influence (false)
 */
public class DeckReader {
  private final File file;
  private final Player owner;

  public DeckReader(String filePath, Player owner) {
    this.file = new File(filePath);
    this.owner = Objects.requireNonNull(owner);
  }

  /**
   * Reads the file and returns a list of Cards for the specified owner.
   *
   * @throws RuntimeException if file is not found or the format is invalid
   */
  public List<Card> readDeck() {
    List<Card> result = new ArrayList<>();
    try (Scanner sc = new Scanner(file)) {

      while (sc.hasNextLine()) {
        if (!sc.hasNextLine()) {
          break;
        }
        String line = sc.nextLine().trim();
        if (line.isEmpty()) {
          if (!sc.hasNextLine()) {
            break;
          }
          line = sc.nextLine().trim();
        }

        String[] parts = line.split("\\s+");
        if (parts.length < 3) {
          throw new RuntimeException("Invalid card line: " + line);
        }
        String name = parts[0];
        int cost = Integer.parseInt(parts[1]);
        int value = Integer.parseInt(parts[2]);

        boolean[][] influenceGrid = new boolean[5][5];
        boolean centerFound = false;

        for (int r = 0; r < 5; r++) {
          if (!sc.hasNextLine()) {
            throw new RuntimeException("Not enough lines for card grid: " + name);
          }
          String rowStr = sc.nextLine();
          if (rowStr.length() != 5) {
            throw new RuntimeException("Grid line must have length 5: " + rowStr);
          }

          for (int c = 0; c < 5; c++) {
            char ch = rowStr.charAt(c);
            if (r == 2 && c == 2) {
              if (ch != 'C') {
                throw new RuntimeException("Center of grid must be 'C' for card: " + name);
              }
              centerFound = true;
              influenceGrid[r][c] = false;
            } else {
              if (ch == 'I') {
                influenceGrid[r][c] = true;
              }
              else if (ch == 'X' || ch == 'C') {
                if (ch == 'C') {
                  throw new RuntimeException("Found 'C' outside center for card: " + name);
                }
                influenceGrid[r][c] = false;
              }
              else {
                throw new RuntimeException("Unexpected character in grid: " + ch);
              }
            }
          }
        }

        if (!centerFound) {
          throw new RuntimeException("No center 'C' found for card: " + name);
        }

        Card newCard = new Card(name, cost, value, owner, influenceGrid);

        int duplicates = 0;
        for (Card existing : result) {
          if (existing.equals(newCard)) {
            duplicates++;
          }
        }
        if (duplicates >= 2) {
          throw new IllegalStateException("Cannot add a 3rd copy of card: " + name);
        }

        result.add(newCard);
      }
    } catch (FileNotFoundException e) {
      throw new RuntimeException("File not found: " + file.getAbsolutePath(), e);
    }
    return result;
  }
}
