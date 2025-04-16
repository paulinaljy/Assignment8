package cs3500.pawnsboard.provider.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import cs3500.pawnsboard.provider.model.ModelActionInterface;
import cs3500.pawnsboard.provider.model.Player;
import cs3500.pawnsboard.provider.model.Card;
import cs3500.pawnsboard.provider.event.PlayerActionListener;
import cs3500.pawnsboard.provider.event.PlayerActionEvent;
import cs3500.pawnsboard.provider.event.ActionType;

import javax.swing.JPanel;

/**
 * Draws the Pawns Board grid with:
 *  - A white left margin that shows row-scores for Red
 *  - Pink or blue backgrounds for owned cells
 *  - Pawns as circles with the count inside
 *  - Cards as a big number
 */
public class BoardPanel extends JPanel {

  private final ModelActionInterface model;
  private PlayerActionListener actionListener;
  private int selectedRow = -1;
  private int selectedCol = -1;
  private final Player playerColor;

  private int cell_width;
  private int cell_height;

  /**
   * Create the panel with a given game model.
   * @param model the game model.
   * @param playerColor the color of the player.
   */
  public BoardPanel(ModelActionInterface model, Player playerColor) {
    this.model = model;
    this.playerColor = playerColor;

    // optional fixed size
    int w = model.getColumnCount() * cell_width;
    int h = model.getRowCount() * cell_height;
    this.setPreferredSize(new Dimension(w, h));
    this.setBackground(Color.WHITE);
    int row = model.getRowCount();
    int col = model.getColumnCount();
    this.cell_width = 1000 / (col + 2);
    this.cell_height = 400 / (row);

    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (actionListener != null) {
          int cellWidth = getWidth() / (model.getColumnCount() + 2);
          int cellHeight = getHeight() / model.getRowCount();

          int col = (e.getX() / cellWidth) - 1;
          int row = e.getY() / cellHeight;

          if (row >= 0 && row < model.getRowCount()
                  && col >= 0 && col < model.getColumnCount()) {
            if (model.isGameOver()) {
              try {
                model.placeCard(0, 0, null);
              } catch (IllegalStateException ex) {
                actionListener.playerActionPerformed(
                        new PlayerActionEvent(ActionType.PASS));
              }
              return;
            }
            if (model.getCurrentPlayer() != playerColor) {
              actionListener.playerActionPerformed(
                      new PlayerActionEvent(ActionType.PASS));
              return;
            }
            if (row == selectedRow && col == selectedCol) {
              selectedRow = -1;  // Deselect if same cell
              selectedCol = -1;
              actionListener.playerActionPerformed(
                      new PlayerActionEvent(ActionType.SELECT_CELL, -1, -1));
            } else {
              selectedRow = row;
              selectedCol = col;
              actionListener.playerActionPerformed(
                      new PlayerActionEvent(ActionType.SELECT_CELL, row, col));
            }
            repaint();
          }
        }
      }
    });
  }

  public void setActionListener(PlayerActionListener listener) {
    this.actionListener = listener;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    int rows = model.getRowCount();
    int cols = model.getColumnCount();

    // Process each row
    for (int r = 0; r < rows; r++) {
      // Draw the scores for this row (to the left and right)
      drawRowScores(g2, r, cols);

      // Draw each cell in the row
      for (int c = 0; c < cols; c++) {
        drawCell(g2, r, c);
      }
    }
  }

  /**
   * Draws the row scores for both players.
   * Red score is drawn on the left and Blue on the right.
   */
  private void drawRowScores(Graphics2D g2, int row, int totalCols) {
    int redRowScore = model.calculateRowScore(Player.RED, row);
    int blueRowScore = model.calculateRowScore(Player.BLUE, row);

    // Set and draw red score
    g2.setColor(redRowScore > blueRowScore ? Color.RED : Color.BLACK);
    g2.drawString(String.valueOf(redRowScore),
            cell_width / 2, row * cell_height + cell_height / 2);

    // Set and draw blue score
    g2.setColor(redRowScore < blueRowScore ? Color.BLUE : Color.BLACK);
    g2.drawString(String.valueOf(blueRowScore),
            cell_width * (totalCols + 1) + (cell_width / 2),
            row * cell_height + cell_height / 2);
  }

  /**
   * Draws an individual cell at the given row and column.
   * This includes the cell background, any pawn graphics,
   * any card information, and finally the border.
   */
  private void drawCell(Graphics2D g2, int row, int col) {
    // Calculate the top-left corner of the cell
    int x = (col + 1) * cell_width;
    int y = row * cell_height;

    // Determine the cell's background color based on its owner
    Color bg = getCellBackgroundColor(row, col);
    // If the cell is selected, brighten the background
    if (row == selectedRow && col == selectedCol) {
      bg = bg.brighter();
    }

    // Fill cell background
    g2.setColor(bg);
    g2.fillRect(x, y, cell_width, cell_height);

    // Draw pawns inside the cell, if any
    drawPawnInCell(g2, row, col, x, y);

    // Draw a card in the cell, if present
    drawCardInCell(g2, row, col, x, y);

    // Draw cell border
    g2.setColor(Color.GRAY);
    g2.drawRect(x, y, cell_width, cell_height);
  }

  /**
   * Determines the background color for a cell based on its owner.
   */
  private Color getCellBackgroundColor(int row, int col) {
    Player owner = model.getBoard().getCell(row, col).getOwner();
    if (owner == Player.RED) {
      return new Color(255, 200, 200); // pinkish
    } else if (owner == Player.BLUE) {
      return new Color(200, 200, 255); // light blue
    } else {
      return new Color(180, 180, 180); // neutral gray
    }
  }

  /**
   * Draws a pawn (as a circle with a number inside) if there is at least one pawn on the cell.
   */
  private void drawPawnInCell(Graphics2D g2, int row, int col, int x, int y) {
    int pawnCount = model.getBoard().getCell(row, col).getPawnCount();
    if (pawnCount > 0) {
      // Determine the pawn color based on its owner
      Player pawnOwner = model.getBoard().getCell(row, col).getOwner();
      if (pawnOwner == Player.RED) {
        g2.setColor(Color.RED);
      } else if (pawnOwner == Player.BLUE) {
        g2.setColor(Color.BLUE);
      } else {
        g2.setColor(Color.BLACK);
      }

      // Define the size and position for the pawn circle
      int circleSize = 30;
      int centerX = x + cell_width / 2 - circleSize / 2;
      int centerY = y + cell_height / 2 - circleSize / 2;
      g2.fillOval(centerX, centerY, circleSize, circleSize);

      // Draw the pawn count in white, centered in the circle
      g2.setColor(Color.WHITE);
      String pStr = String.valueOf(pawnCount);
      FontMetrics fm = g2.getFontMetrics();
      int txtW = fm.stringWidth(pStr);
      int txtH = fm.getAscent();
      g2.drawString(pStr, centerX + (circleSize - txtW) / 2,
              centerY + (circleSize + txtH) / 2 - 2);
    }
  }

  /**
   * Draws a card's value in the cell if a card is present.
   */
  private void drawCardInCell(Graphics2D g2, int row, int col, int x, int y) {
    Card card = model.getCardAt(row, col);
    if (card != null) {
      g2.setColor(Color.BLACK);
      String valStr = String.valueOf(card.getValue());
      // Temporarily set a larger font size for the card's value
      Font oldFont = g2.getFont();
      g2.setFont(oldFont.deriveFont(24f));
      FontMetrics fm = g2.getFontMetrics();
      int txtW = fm.stringWidth(valStr);
      int txtH = fm.getAscent();
      int strX = x + (cell_width - txtW) / 2;
      int strY = y + (cell_height + txtH) / 2 - 4;
      g2.drawString(valStr, strX, strY);
      // Restore the previous font
      g2.setFont(oldFont);
    }
  }


  /**
   * To de select a cell.
   */
  public void clearSelectedCell() {
    this.selectedRow = -1;
    this.selectedCol = -1;
    repaint();
  }

  /**
   *  Convert a mouse y => col.
   * @param my mouse y.
   * @return col
   */
  public int getRowFromPixel(int my) {
    int row = my / cell_height;
    if (row < 0 || row >= model.getRowCount()) {
      return -1;
    }
    return row;
  }

  /**
   *  Convert a mouse x => row.
   * @param mx mouse y.
   * @return row.
   */
  public int getColFromPixel(int mx) {
    if (mx < 0) {
      return -1;
    }
    int col = (mx / cell_width) - 1;
    if (col < 0 || col >= model.getColumnCount()) {
      return -1;
    }
    return col;
  }
}
