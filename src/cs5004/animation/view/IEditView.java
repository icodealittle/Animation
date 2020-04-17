package cs5004.animation.view;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.List;

import cs5004.animation.model.ShapeGetter;

/**
 * A constructor represent a view for an animation that has a GUI for editing the animation.
 */
public interface IEditView extends IView {

  /**
   * Sets the width of the display for the animation to run on.
   * @param width the new width to set the animation display
   */
  void setWidth(int width);

  /**
   * Sets the height of the display for the animation to run on.
   * @param height the new width to set the animation display
   */
  void setHeight(int height);

  /**
   * Adds a listener that receives action events from button presses.
   * @param actionListener the object that should receive events when buttons are pressed
   */
  void addButtonListener(ActionListener actionListener);

  /**
   *A setter that return the set speed for the animation.
   * @param tickSpeed the tick per second of how fast the shape move.
   */
  void setSpeed(double tickSpeed);

  /**
   * Displays the given list of shapes on the GUI. Used to send a new frame of the animation.
   * @param shapes the shapes to display in the view
   */
  void display(List<ShapeGetter> shapes);

  /**
   * Updates the slider to the given tick.
   * @param tick sets the slider to the given percent of the animation.
   */
  void setSlider(double tick);

  /**
   * Displays an error pop up on the screen with the given string so the user can see that an
   *     error occurred and what might have caused it.
   * @param s the error message to be displayed.
   */
  void displayError(String s);

  /**
   * Adds a listener that receives property change events from new user input.
   * @param listener the object that should receive events when properties are updated
   */
  void addPropertyListener(PropertyChangeListener listener);
}
