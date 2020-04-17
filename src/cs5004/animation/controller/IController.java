package cs5004.animation.controller;

/**
 * A interface that represents a controller of the animation.
 */
public interface IController {

  /**
   * Controller plays the current animation.
   */
  void play();

  /**
   * This starts the animation.
   */
  void start();

  /**
   * This is a getter for the first tick speed. It will start the animation
   * at that first tick. The default tick is 0.
   * @return first tick.
   */
  int getStartingTick();

}
