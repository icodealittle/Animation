package cs5004.animation.model;

import java.awt.Color;

/**
 * An interface that represent a command on a shape during the animation without any mutable
 *     commands and shapes.
 */
public interface AnimationInterface {

  /**
   * A getter that return the appear time of the animation shape.
   * @return the appear time of the animation in an int form, so the time would in an integer form.
   */
  int getShapeStartTime();

  /**
   * A getter that return the disappear time of the animation shape.
   * @return the disappear time of the animation in an int form,
   *     so the time would in an integer form.
   */
  int getShapeEndTime();

  /**
   * A getter that return the type of this command in a String.
   * @return a String that represents the type of command it is.
   */
  String getCommandType();

  /**
   * A getter that return the starting X value of shape in his command as an int.
   * @return the starting X value of the shape in this command.
   */
  int getShapeStartXPoint();

  /**
   * A getter that return the starting Y value of shape in his command as an int.
   * @return the starting Y value of the shape in this command.
   */
  int getShapeStartYPoint();

  /**
   * A getter that return the starting width of shape in his command as an int.
   * @return the starting width of the shape in this command.
   */
  int getShapeStartW();

  /**
   * A getter that return the starting height of shape in his command as an int.
   * @return the starting height of the shape in this command.
   */
  int getShapeStartH();

  /**
   * A getter that return the ending X value of shape in his command as an int.
   * @return the ending X value of the shape in this command.
   */
  int getShapeEndXPoint();

  /**
   * A getter that return the ending Y value of shape in his command as an int.
   * @return the ending Y value of the shape in this command.
   */
  int getShapeEndYPoint();

  /**
   * A getter that return the sending width of shape in his command as an int.
   * @return the ending width of the shape in this command.
   */
  int getShapeEndW();

  /**
   * A getter that return the ending height of shape in his command as an int.
   * @return the ending height of the shape in this command.
   */
  int getShapeEndH();

  /**
   * A getter that return the start colour of shape in his command as an int.
   * @return the start colour of the shape in this command.
   */
  Color getShapeStartColor();

  /**
   * A getter that return the end colour of shape in his command as an int.
   * @return the end colour of the shape in this command.
   */
  Color getShapeEndColor();

  /**
   * A getter that return the orientation of this command which the ending orientation the shape.
   * @return int that represents the orientation in degrees
   */
  int getEndShapeOrientation();

  /**
   * A getter that return the orientation of this command which the starting orientation the shape.
   * @return int that represents the orientation in degrees
   */
  int getStartOrientation();

  /**
   * Mutates the shape to its instantaneous state at the given time after doing this command.
   * @param time in ticks that the shape gets changed to.
   * @param shape which is the starting shape before the command has been run to the given time.
   * @return InterfaceShape that is the mutated shape after the command has
   *     been run to the given time.
   */
  InterfaceShape setState(int time, InterfaceShape shape);

}
