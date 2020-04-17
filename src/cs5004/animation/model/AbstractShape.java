package cs5004.animation.model;

import java.awt.Color;
import java.awt.geom.Point2D;

import static java.util.Objects.requireNonNull;

/**
 * A class that represent of an abstract shape that implement an shape interface.
 * The InterfaceShape class contain mainly the shape setter methods, whereas the ShapeGetter
 *     interface contains all the getter method.
 */
public abstract class AbstractShape implements InterfaceShape {

  protected String typeofShape;
  protected int width;
  protected int height;
  protected Point2D shapePosition;
  protected Color c;
  protected int shapeOrientation;

  /**
   * An inheritance class that provides special functionality to its derived classes that
   *     must not be visible to other classes within the same package.
   * @param width the shape width.
   * @param height the shape height.
   * @param shapePosition the position of the shape.
   * @param c the color of the shape.
   */
  protected AbstractShape(int width, int height, Point2D shapePosition, Color c) {
    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("Cannot have a negative width or height");
    }
    this.width = width;
    this.height = height;
    this.shapePosition = requireNonNull(shapePosition);
    this.c = requireNonNull(c);
  }

  protected AbstractShape() {
    this(0, 0, new Point2D.Double(0, 0), new Color(0));
  }

  @Override
  public void setShapePosition(Point2D shapePoint) {
    this.shapePosition = requireNonNull(shapePoint);
  }

  @Override
  public void setShapeColor(Color color) {
    this.c = requireNonNull(color);
  }

  @Override
  public void setShapeW(int width) {
    if (width < 0) {
      throw new IllegalArgumentException("Only positive numbers for width");
    }
    this.width = width;
  }

  @Override
  public void setShapeH(int height) {
    if (height < 0) {
      throw new IllegalArgumentException("Only positive numbers for height");
    }
    this.height = height;
  }

  @Override
  public boolean equals(Object otherShape) {
    if (otherShape == this) {
      return true;
    }
    if (!(otherShape instanceof AbstractShape)) {
      return false;
    }
    AbstractShape anotherShape = (AbstractShape) otherShape;
    return this.typeofShape.equals(anotherShape.getShapeType())
            && this.width == anotherShape.getWidth()
            && this.height == anotherShape.getHeight()
            && this.shapePosition.getX() == anotherShape.getPosition().getX()
            && this.shapePosition.getY() == anotherShape.getPosition().getY()
            && this.shapeOrientation == anotherShape.getOrientation()
            && this.c.getRGB() == anotherShape.getColor().getRGB();
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + this.typeofShape.hashCode();
    result = 31 * result + this.width;
    result = 31 * result + this.height;
    result = 31 * result + this.shapeOrientation;
    long xLong = Double.doubleToLongBits(this.shapePosition.getX());
    result = 31 * result + (int) (xLong ^ (xLong >>> 32));
    long yLong = Double.doubleToLongBits(this.shapePosition.getX());
    result = 31 * result + (int) (yLong ^ (yLong >>> 32));
    result = 31 * result + this.getColor().getRGB();
    return result;
  }

  @Override
  public String getShapeType() {
    return this.typeofShape;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getOrientation() {
    return this.shapeOrientation;
  }

  @Override
  public Point2D getPosition() {
    return new Point2D.Double(this.shapePosition.getX(), this.shapePosition.getY());
  }

  @Override
  public Color getColor() {
    return new Color(this.c.getRGB());
  }

}
