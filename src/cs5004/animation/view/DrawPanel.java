package cs5004.animation.view;

import java.awt.Graphics;
import java.awt.Graphics2D;

import cs5004.animation.model.ShapeGetter;

/**
 * A constructor that represents a drawing panel for the visual view,
 *     which draws the shapes at each tick of an animation.
 */
public class DrawPanel extends AAnimationDrawingPanel implements IDrawAnimationPanel {

  /**
   * A default constructor for drawing panel.
   */
  public DrawPanel() {
    super();
  }

  @Override
  protected void drawShape(Graphics graphics) {
    Graphics2D graphics2D = (Graphics2D) graphics;
    for (ShapeGetter s : this.shapes) {
      graphics2D.setColor(s.getColor());
      s.draw(graphics2D);
    }
  }
}
