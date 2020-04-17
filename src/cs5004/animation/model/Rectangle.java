package cs5004.animation.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 * A class represents an Rectangle object in the animation.
 */
public class Rectangle extends AbstractShape {
  /**
   * Constructor for the Rectangle.
   * @param width is width.
   * @param height is height.
   * @param shapePosition the 2d position of the Rectangle.
   * @param c is the color.
   */
  public Rectangle(int width, int height, Point2D shapePosition, Color c) {
    super(width, height, shapePosition, c);
    this.typeofShape = "Rectangle";
  }

  /**
   * initializing starting point (0,0).
   */
  public Rectangle() {
    super();
    this.typeofShape = "Rectangle";
  }

  @Override
  public void draw(Graphics2D graphics2D) {
    if (this.shapeOrientation == 0) {
      graphics2D.fillRect((int) this.shapePosition.getX(),
              (int) this.shapePosition.getY(), this.width, this.height);
    } else {
      Graphics2D g = (Graphics2D) graphics2D.create();
      g.rotate(Math.toRadians(this.shapeOrientation),
              this.shapePosition.getX() + ((double) this.width) / 2,
              this.shapePosition.getY() + ((double) this.height / 2));
      g.fillRect((int) this.shapePosition.getX(),
              (int) this.getPosition().getY(), this.width, this.height);
      g.dispose();
    }
  }

  @Override
  public InterfaceShape duplicate() {
    return new Rectangle(this.width, this.height,
            new Point2D.Double(this.shapePosition.getX(), this.shapePosition.getY()),
            new Color(this.c.getRGB()));
  }
}
