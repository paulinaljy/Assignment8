package view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import event.PlayerActionListener;

/**
 * A graphical view for Pawns Board.
 */
public interface PawnsView {
  /**
   * Make the view visible (call JFrame.setVisible(true)).
   */
  void makeVisible();

  /**
   * Repaint/refresh the view.
   */
  void refresh();

  /**
   * Add a MouseListener to capture board/hand clicks.
   */
  void addMouseListener(MouseListener ml);

  /**
   * Add a KeyListener to capture pass/confirm (or other) key events.
   */
  void addKeyListener(KeyListener kl);

  /**
   * Clear any highlighted cell/card.
   */
  void clearHighlights();

  /**
   * Registers a PlayerActionListener that will be notified when
   * the user attempts a move or passes their turn.
   */
  void addPlayerActionListener(PlayerActionListener pal);

  /**
   * Show an error dialog or message to the user.
   */
  void showErrorMessage(String message);

  /**
   * Show a pop-up or other UI message indicating the game is over.
   */
  void showGameOver(String message);
}
