package cs5004.animation.view;

/**
 * Represents the display for the animation.
 */
public interface IView {

  /**
   * Plays the animation using the type of view that is currently initialized.
   */
  void play();

  /**
   * Gets the texts that represents the animation.
   * @return String that is the description for the animation.
   */
  String getText();

}
