package cs5004.animation.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 * A class represents an Oval object in the animation.
 */
public class Oval extends AbstractShape {

  /**
   * Constructor for the oval.
   * @param width is width.
   * @param height is height.
   * @param shapePosition the 2d position of the oval.
   * @param c is the color.
   */
  public Oval(int width, int height, Point2D shapePosition, Color c) {
    super(width, height, shapePosition, c);
    this.typeofShape = "Oval";
  }

  /**
   * initializing starting point (0,0).
   */
  public Oval() {
    super();
    this.typeofShape = "Oval";
  }

  @Override
  public InterfaceShape duplicate() {
    return new Ellipse(this.width, this.height,
            new Point2D.Double(this.shapePosition.getX(), this.shapePosition.getY()),
            new Color(this.c.getRGB()));
  }

  @Override
  public void draw(Graphics2D graphics2D) {
    if (this.shapeOrientation == 0) {
      graphics2D.fillOval((int) this.shapePosition.getX(),
              (int) this.shapePosition.getY(), this.width, this.height);
    }
    else {
      Graphics2D gg = (Graphics2D) graphics2D.create();
      gg.rotate(Math.toRadians(this.shapeOrientation),
              this.shapePosition.getX() + ((double) this.width) / 2,
              this.shapePosition.getY() + ((double) this.height) / 2);
      gg.fillOval((int) this.shapePosition.getX(),
              (int) this.shapePosition.getY(), this.width, this.height);
      gg.dispose();
    }
  }
}
