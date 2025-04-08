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
import cs3500.pawnsboard.provider.players.Player;
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
            selectedRow = row;
            selectedCol = col;
            actionListener.playerActionPerformed(
              new PlayerActionEvent(ActionType.SELECT_CELL, row, col));
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

    // Draw each row from top to bottom
    for (int r = 0; r < rows; r++) {
      int redRowScore = model.calculateRowScore(Player.RED, r);
      int blueRowScore = model.calculateRowScore(Player.BLUE, r);
      if (redRowScore > blueRowScore) {
        g2.setColor(Color.RED);
      } else {
        g2.setColor(Color.BLACK);
      }
      g2.drawString(String.valueOf(redRowScore),
              this.cell_width / 2, r * cell_height + cell_height / 2);

      if (redRowScore < blueRowScore) {
        g2.setColor(Color.BLUE);
      } else {
        g2.setColor(Color.BLACK);
      }
      g2.drawString(String.valueOf(blueRowScore),
              this.cell_width * (model.getColumnCount() + 1) + (this.cell_width / 2),
              r * cell_height + cell_height / 2);

      for (int c = 0; c < cols; c++) {
        int x = (c + 1) * cell_width;
        int y = r * cell_height;

        Color bg;
        Player owner = model.getBoard().getCell(r, c).getOwner();
        if (owner == Player.RED) {
          bg = new Color(255, 200, 200); // pinkish
        } else if (owner == Player.BLUE) {
          bg = new Color(200, 200, 255); // light blue
        } else {
          bg = new Color(180, 180, 180); // neutral gray
        }

        // If this cell is selected, change its color 
        if (r == selectedRow && c == selectedCol) {
          bg = bg.brighter();
        }

        g2.setColor(bg);
        g2.fillRect(x, y, cell_width, cell_height);

        // Pawns as circles
        int pawnCount = model.getBoard().getCell(r, c).getPawnCount();
        if (pawnCount > 0) {
          Player pawnOwner = model.getBoard().getCell(r, c).getOwner();
          if (pawnOwner == Player.RED) {
            g2.setColor(Color.RED);
          }
          else if (pawnOwner == Player.BLUE) {
            g2.setColor(Color.BLUE);
          }
          else {
            g2.setColor(Color.BLACK);
          }
          int circleSize = 30;
          int centerX = x + cell_width / 2 - circleSize / 2;
          int centerY = y + cell_height / 2 - circleSize / 2;
          g2.fillOval(centerX, centerY, circleSize, circleSize);

          g2.setColor(Color.WHITE);
          String pStr = String.valueOf(pawnCount);
          FontMetrics fm = g2.getFontMetrics();
          int txtW = fm.stringWidth(pStr);
          int txtH = fm.getAscent();
          g2.drawString(pStr, centerX + (circleSize - txtW) / 2,
                  centerY + (circleSize + txtH) / 2 - 2);
        }

        Card card = model.getCardAt(r, c);
        if (card != null) {
          g2.setColor(Color.BLACK);
          String valStr = String.valueOf(card.getValue());
          Font oldFont = g2.getFont();
          g2.setFont(oldFont.deriveFont(24f));
          FontMetrics fm = g2.getFontMetrics();
          int txtW = fm.stringWidth(valStr);
          int txtH = fm.getAscent();
          int strX = x + (cell_width - txtW) / 2;
          int strY = y + (cell_height + txtH) / 2 - 4;
          g2.drawString(valStr, strX, strY);
          g2.setFont(oldFont);
        }

        g2.setColor(Color.GRAY);
        g2.drawRect(x, y, cell_width, cell_height);
      }
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
