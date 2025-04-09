package controller;

import event.ModelStatusEvent;
import event.ModelStatusListener;
import event.PlayerActionEvent;
import event.PlayerActionListener;
import model.Card;
import model.ModelActionInterface;
import players.Playable;
import players.Player;
import view.PawnsView;

/**
 * Controller for a single (RED or BLUE) player in the Pawns Board game.
 * Manages interactions among the model, view, and a Playable (human or AI).
 */
public class PawnsBoardController implements PawnsBoardControllerInterface,
    PlayerActionListener, ModelStatusListener {

  private final ModelActionInterface model;
  private final PawnsView view;
  private final Playable player;
  private final Player actualPlayerEnum;

  private int selectedCardIndex = -1;
  private int selectedRow = -1;
  private int selectedCol = -1;

  /**
   * Full constructor for multi-player games.
   *  @param model the game model
   *  @param player the AI or human player
   *  @param view the PawnsView for this player
   *  @param actualPlayerEnum which player color (RED or BLUE) this controller manages
   */
  public PawnsBoardController(ModelActionInterface model, Playable player,
                              PawnsView view, Player actualPlayerEnum) {
    this.model = model;
    this.view = view;
    this.player = player;
    this.actualPlayerEnum = actualPlayerEnum;
  }

  /**
   * Initializes the controller by registering it as a listener
   * to both the model and the player, and the view.
   */
  @Override
  public void startGame() {
    if (this.model instanceof ModelActionInterface) {
      ((ModelActionInterface) this.model).addModelStatusListener(this);
    }

    this.view.addPlayerActionListener(this);

    if (this.player != null) {
      this.player.addActionListener(this);
    }
  }

  /**
   * Reacts to user or AI actions (select card, select cell, confirm, pass).
   */
  @Override
  public void playerActionPerformed(PlayerActionEvent event) {
    if (!isOurTurn()) {
      view.showErrorMessage("It's not your turn!");
      return;
    }

    switch (event.getActionType()) {
      case SELECT_CARD:
        selectedCardIndex = event.getCardIndex();
        view.refresh();
        break;

      case SELECT_CELL:
        selectedRow = event.getRow();
        selectedCol = event.getCol();
        view.refresh();
        break;

      case CONFIRM:
        handleConfirmAction();
        break;

      case PASS:
        handlePassAction();
        break;

      default:
        break;
    }
  }

  /**
   * Reacts to model status changes.
   * When the model notifies that it's our turn, we can call player.takeTurn().
   */
  @Override
  public void modelStatusChanged(ModelStatusEvent event) {
    switch (event.getStatus()) {
      case TURN_CHANGED:
        view.refresh();
        if (isOurTurn() && player != null) {
          player.takeTurn();
        }
        break;

      case GAME_OVER:
        view.refresh();
        view.showGameOver(event.getMessage());
        break;

      default:
        break;
    }
  }

  /**
   * Called when the user/AI attempts to confirm a placement.
   * We need a selected card and a selected cell.
   */
  private void handleConfirmAction() {
    try {
      if (selectedCardIndex < 0) {
        throw new IllegalStateException("No card selected!");
      }
      if (selectedRow < 0 || selectedCol < 0) {
        throw new IllegalStateException("No cell selected!");
      }

      Card card = model.getHand(actualPlayerEnum).getCards().get(selectedCardIndex);

      model.placeCard(selectedRow, selectedCol, card);

      clearSelections();
      view.refresh();

    } catch (Exception e) {
      view.showErrorMessage(e.getMessage());
    }
  }

  /**
   * Called when the user/AI passes the turn.
   */
  private void handlePassAction() {
    try {
      model.passTurn();
      clearSelections();
      view.refresh();
    } catch (Exception e) {
      view.showErrorMessage(e.getMessage());
    }
  }

  /**
   * Checks if the model's current player matches our assigned player color.
   */
  private boolean isOurTurn() {
    if (actualPlayerEnum == null) {
      return false;
    }
    return model.getCurrentPlayer() == actualPlayerEnum;
  }

  /**
   * Resets any stored selections for card or board cell.
   */
  private void clearSelections() {
    selectedCardIndex = -1;
    selectedRow = -1;
    selectedCol = -1;
    view.clearHighlights();
  }
}
