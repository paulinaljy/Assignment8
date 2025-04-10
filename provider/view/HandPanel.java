package cs3500.pawnsboard.provider.view;

import cs3500.pawnsboard.provider.model.ModelActionInterface;
import cs3500.pawnsboard.provider.players.Player;
import cs3500.pawnsboard.provider.event.PlayerActionListener;
import cs3500.pawnsboard.provider.event.PlayerActionEvent;
import cs3500.pawnsboard.provider.event.ActionType;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.Color;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.List;

import javax.swing.JPanel;

/**
 * Create a panel to illustrates hands of players.
 */
public class HandPanel extends JPanel {

  private final ModelActionInterface model;
  private PlayerActionListener actionListener;
  private int selectedCard = -1;
  private final Player playerColor;

  private int card_width;
  private int card_height;
  private static final int CENTER_MARGIN = 10;

  /**
   * Create such panel for hands.
   * @param model the given game model.
   */
  public HandPanel(ModelActionInterface model, Player playerColor) {
    this.model = model;
    this.playerColor = playerColor;

    // Set fixed size for the panel
    this.setPreferredSize(new Dimension(1000, 200));

    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (actionListener != null) {
          int cardWidth = getWidth() / model.getHand(playerColor).getCards().size();
          int cardIndex = e.getX() / cardWidth;

          if (cardIndex >= 0 && cardIndex < model.getHand(playerColor).getCards().size()) {
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
            selectedCard = cardIndex;
            actionListener.playerActionPerformed(
                    new PlayerActionEvent(ActionType.SELECT_CARD, cardIndex));
            repaint();
          }
        }
      }
    });
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    List<Card> cards = model.getHand(playerColor).getCards();
    int cardWidth = getWidth() / cards.size();
    int cardHeight = getHeight();

    for (int i = 0; i < cards.size(); i++) {
      Card card = cards.get(i);

      // Card background
      if (i == selectedCard && model.getCurrentPlayer() == playerColor) {
        g2d.setColor(Color.YELLOW);
      } else if (playerColor == Player.RED) {
        g2d.setColor(new Color(255, 200, 200));
      } else {
        g2d.setColor(new Color(200, 200, 255));
      }
      g2d.fillRect(i * cardWidth, 0, cardWidth - 1, cardHeight - 1);

      // Draw card info at top
      g2d.setColor(Color.BLACK);
      String name = card.getName();
      String costValue = "Cost: " + card.getCost() + ", Value: " + card.getValue();
      FontMetrics metrics = g2d.getFontMetrics();
      int nameX = i * cardWidth + (cardWidth - metrics.stringWidth(name)) / 2;
      int costX = i * cardWidth + (cardWidth - metrics.stringWidth(costValue)) / 2;
      g2d.drawString(name, nameX, 20);
      g2d.drawString(costValue, costX, 40);

      // Draw influence grid with dark background
      boolean[][] influence = card.getInfluenceGrid();
      int gridSize = Math.min(cardWidth, cardHeight - 50) / 5;
      int startX = i * cardWidth + (cardWidth - gridSize * 5) / 2;
      int startY = 50;

      // Draw dark background for grid
      g2d.setColor(Color.DARK_GRAY);
      g2d.fillRect(startX, startY, gridSize * 5, gridSize * 5);

      for (int row = 0; row < 5; row++) {
        for (int col = 0; col < 5; col++) {
          int x = startX + col * gridSize;
          int y = startY + row * gridSize;

          // Draw influenced cells
          if (influence[row][col]) {
            if (playerColor == Player.RED) {
              g2d.setColor(Color.CYAN);
            } else {
              g2d.setColor(Color.ORANGE);
            }
            g2d.fillRect(x + 1, y + 1, gridSize - 1, gridSize - 1);
          }

          // Highlight center cell
          if (row == 2 && col == 2) {
            g2d.setColor(playerColor == Player.RED ? Color.RED : Color.BLUE);
            g2d.fillRect(x + 1, y + 1, gridSize - 1, gridSize - 1);
          }

          // Draw cell border
          g2d.setColor(Color.BLACK);
          g2d.drawRect(x, y, gridSize, gridSize);
        }
      }

      // Draw card border
      g2d.setColor(Color.BLACK);
      g2d.drawRect(i * cardWidth, 0, cardWidth - 1, cardHeight - 1);
    }
  }

  /**
   * Convert a mouse X,Y to a card index, or -1 if no card.
   */
  public int getCardIndexFromPixel(int mouseX, int mouseY) {
    Player current = model.getCurrentPlayer();
    List<Card> hand = model.getHand(current).getCards();

    int x = 0;
    int y = 0;
    for (int i = 0; i < hand.size(); i++) {
      Rectangle rect = new Rectangle(x, y, card_width, card_height);
      if (rect.contains(mouseX, mouseY)) {
        return i;
      }
      x += card_width;
    }
    return -1;
  }

  public void setActionListener(PlayerActionListener listener) {
    this.actionListener = listener;
  }

  public void clearSelectedCard() {
    selectedCard = -1;
    repaint();
  }
}

