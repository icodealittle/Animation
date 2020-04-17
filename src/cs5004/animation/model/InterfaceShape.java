package cs5004.animation.model;

import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * Shape Interface that extends all the methods from the ShapeGetter interface.
 */
public interface InterfaceShape extends ShapeGetter {

  /**
   * Make a copy of the shape.
   * @return the copied shape.
   */
  InterfaceShape duplicate();

  /**
   * A setter that return the position of this shape to the given position.
   *
   * @param shapePoint X Y coordinate of the position that the shape will be set to.
   */
  void setShapePosition(Point2D shapePoint);

  /**
   * A setter that return the color of this shape to the given position.
   *
   * @param color that the shape will be set to.
   */
  void setShapeColor(Color color);

  /**
   * A setter that return the width of this shape to the given position.
   *
   * @param width that the shape will be set to.
   */
  void setShapeW(int width);

  /**
   * A setter that return the height of this shape to the given position.
   *
   * @param height that the shape will be set to.
   */
  void setShapeH(int height);
}
