package cs3500.pawnsboard.provider.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;

import cs3500.pawnsboard.provider.event.PlayerActionListener;
import cs3500.pawnsboard.provider.event.PlayerActionEvent;
import cs3500.pawnsboard.provider.event.ActionType;
import cs3500.pawnsboard.provider.model.ModelActionInterface;
import cs3500.pawnsboard.provider.players.Player;

import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Main JFrame implementing PawnsView.
 * It has:
 * - A status label at the top
 * - A BoardPanel in the center
 * - A HandPanel at the bottom
 */
public class PawnsBoardFrame extends JFrame implements PawnsView {

  private final ModelActionInterface model;
  private final JLabel statusLabel;
  private final BoardPanel boardPanel;
  private final HandPanel handPanel;
  private PlayerActionListener actionListener;

  /**
   * Create the whole game board.
   *
   * @param model the given game model.
   */
  public PawnsBoardFrame(ModelActionInterface model, Player playerColor) {
    super("Pawns Board - " + playerColor);
    this.model = model;

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    // Top status label
    statusLabel = new JLabel("Player: " + model.getCurrentPlayer(), SwingConstants.CENTER);
    this.add(statusLabel, BorderLayout.NORTH);

    // Board in center
    boardPanel = new BoardPanel(model, playerColor);
    this.add(boardPanel, BorderLayout.CENTER);

    // Hand at bottom
    handPanel = new HandPanel(model, playerColor);
    this.add(handPanel, BorderLayout.SOUTH);

    this.setSize(1000, 650);

    this.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (actionListener != null) {
          if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            actionListener.playerActionPerformed(
                    new PlayerActionEvent(ActionType.CONFIRM));
          } else if (e.getKeyCode() == KeyEvent.VK_SPACE ||
                  e.getKeyCode() == KeyEvent.VK_P) {
            actionListener.playerActionPerformed(
                    new PlayerActionEvent(ActionType.PASS));
          }
        }
        requestFocusInWindow();
      }
    });

    this.setFocusable(true);
    this.requestFocusInWindow();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void refresh() {
    statusLabel.setText("Player: " + model.getCurrentPlayer());
    boardPanel.repaint();
    handPanel.repaint();
  }

  @Override
  public void addMouseListener(java.awt.event.MouseListener ml) {
    boardPanel.addMouseListener(ml);
    handPanel.addMouseListener(ml);
  }

  @Override
  public void addKeyListener(KeyListener kl) {
    super.addKeyListener(kl);
  }

  @Override
  public void clearHighlights() {
    boardPanel.clearSelectedCell();
    handPanel.clearSelectedCard();
    refresh();
  }

  @Override
  public void addPlayerActionListener(PlayerActionListener pal) {
    this.actionListener = pal;
    boardPanel.setActionListener(pal);
    handPanel.setActionListener(pal);
  }

  @Override
  public void showErrorMessage(String message) {
    JOptionPane.showMessageDialog(
            this,
            message,
            "Error",
            JOptionPane.ERROR_MESSAGE
    );
  }

  @Override
  public void showGameOver(String message) {
    JOptionPane.showMessageDialog(
            this,
            message,
            "Game Over",
            JOptionPane.INFORMATION_MESSAGE
    );
    statusLabel.setText("Game Over: " + message);

    boardPanel.setEnabled(false);
    handPanel.setEnabled(false);
  }
}
