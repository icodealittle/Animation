package cs5004.animation.model;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * An Animation model interface that returns values or sets values for the model.
 */
public interface IAnimationModel {

  /**
   * A getter that return the Y values of the model.
   * @return the Y values of the model in pixels.
   */
  int getX();

  /**
   * A getter that return the X values of the model.
   * @return the X values of the model in pixels.
   */
  int getY();

  /**
   * A getter that return the height of the model.
   * @return the height of the model in pixels.
   */
  int getHeight();

  /**
   * A getter that return the width of the model.
   * @return the width of the model in pixels.
   */
  int getWidth();

  /**
   * A getter that return each of the shapes in the map.
   * @return the map linking ID to each unique shape.
   */
  LinkedHashMap<String, ShapeGetter> getShapes();

  /**
   * A getter that return the outputs the list of shapes at their instantaneous state
   *     in the animation based on the given time.
   * @param time in ticks
   * @return the list of shapes.
   */
  List<ShapeGetter> getShapeState(int time);

  /**
   * A getter that return all of the commands from the model.
   * @return the map linking the list of commands to the ID of the shape.
   */
  LinkedHashMap<String, List<AnimationInterface>> getCommands();

  /**
   * A getter that return the final tick of the last command run in this model.
   * @return the final tick of the model.
   */
  int getFinalTick();
}
