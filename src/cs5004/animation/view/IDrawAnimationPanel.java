package cs5004.animation.view;

import java.util.List;

import cs5004.animation.model.ShapeGetter;

/**
 * A drawing panel for an animation. The draw() method paints the given shapes on the panel.
 */
public interface IDrawAnimationPanel {

  /**
   * Paints the given list of shapes onto the panel on the screen.
   * @param shapes is the list of shapes that are painted on the screen.
   */
  void draw(List<ShapeGetter> shapes);
}
