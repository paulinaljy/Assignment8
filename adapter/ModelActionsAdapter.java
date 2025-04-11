package cs3500.pawnsboard.adapter;

import cs3500.pawnsboard.model.ModelActions;
import cs3500.pawnsboard.provider.event.ActionType;
import cs3500.pawnsboard.provider.event.ModelStatus;
import cs3500.pawnsboard.provider.event.ModelStatusEvent;
import cs3500.pawnsboard.provider.event.ModelStatusListener;
import cs3500.pawnsboard.view.ViewActions;

public class ModelActionsAdapter implements ModelStatusListener {
  private final ModelActions adaptee;

  public ModelActionsAdapter(ModelActions adaptee) {
    this.adaptee = adaptee;
  }
  @Override
  public void modelStatusChanged(ModelStatusEvent event) {
    ModelStatus status = event.getStatus();
    switch (status) {
      case TURN_CHANGED -> adaptee.itsYourTurn();
      case GAME_OVER -> adaptee.processGameOver();
    }
  }
}
