package cs3500.pawnsboard.provider.event;

/**
 * Event fired by the model when its status changes (e.g., turn changes or game ends).
 */
public class ModelStatusEvent {
  private final ModelStatus status;
  private final String message;

  /**
   * Creates a new model status event.
   *
   * @param status  the type of status change
   * @param message optional message with details (e.g., "RED wins!")
   */
  public ModelStatusEvent(ModelStatus status, String message) {
    this.status = status;
    this.message = message;
  }

  /**
   * Get the current model status.
   * @return the type of status change
   */
  public ModelStatus getStatus() {
    return status;
  }

  /**
   * Get the message should be print.
   * @return any additional message associated with the status change
   */
  public String getMessage() {
    return message;
  }
}