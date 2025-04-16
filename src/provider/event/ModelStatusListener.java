package cs3500.pawnsboard.provider.event;

/**
 * Interface for objects that want to be notified of model status changes.
 */
public interface ModelStatusListener {

  /**
   * Called when the model's status changes (e.g., turn changes or game ends).
   *
   * @param event contains the type of change and any associated message
   */
  void modelStatusChanged(ModelStatusEvent event);
}