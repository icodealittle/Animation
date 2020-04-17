package cs5004.animation.view;

import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

import cs5004.animation.model.ShapeGetter;

/**
 * An abstract class that represents a panel for a visual view that paints read-only shapes
 *     onto itself for display.
 */
public abstract class AAnimationDrawingPanel extends JPanel implements IDrawAnimationPanel {

  protected List<ShapeGetter> shapes;

  protected AAnimationDrawingPanel() {
    super();
    this.shapes = null;
  }

  @Override
  public void paintComponent(Graphics graphics) {
    super.paintComponent(graphics);
    if (this.shapes != null) {
      this.drawShape(graphics);
    }
  }

  @Override
  public void draw(List<ShapeGetter> shapes) {
    this.shapes = shapes;
    this.repaint();
  }

  /**
   * A helper method that draw shapes to any
   *     extending class method.
   * @param graphics the shapes.
   */
  protected abstract void drawShape(Graphics graphics);
}
