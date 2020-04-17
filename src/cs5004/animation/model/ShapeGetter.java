package cs5004.animation.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 * A Constructor for ShapeGetter interface.
 */
public interface ShapeGetter {

  /**
   * A getter that return the type of shape this shape is.
   * @return String the is the type of Shape this shape is.
   */
  String getShapeType();

  /**
   * A getter that return the width this shape is.
   * @return int that is the width this shape is.
   */
  int getWidth();

  /**
   * A getter that return the height this shape is.
   * @return int that is the height this shape is.
   */
  int getHeight();

  /**
   * A getter that return the orientation this shape is.
   * @return int that is the orientation this shape is.
   */
  int getOrientation();

  /**
   * A getter that return the current position of the shape.
   * @return Point2D that is the X and Y coordinate of the shape.
   */
  Point2D getPosition();

  /**
   * A getter that return the color of the shape.
   * @return Color which is the color of the shape.
   */
  Color getColor();

  /**
   * Draws the shape onto the screen.
   * @param graphics2D The graphics needed to display the shape on the screen.
   */
  void draw(Graphics2D graphics2D);

  /**
   * Makes a deep copy of the current shape.
   *
   * @return an IShape identical to this one.
   */
  InterfaceShape duplicate();

  /**
   * Sets the position of this shape to the given position.
   *
   * @param shapePoint X Y coordinate of the position that the shape will be set to.
   */
  void setShapePosition(Point2D shapePoint);

  /**
   * Sets the color of this shape to the given shape.
   *
   * @param color is the RGB color that the shape will be set to.
   */
  void setShapeColor(Color color);

  /**
   * Sets the width of this shape to the given amount.
   *
   * @param width is the width of the shape that this shape should be changed to.
   */
  void setShapeW(int width);

  /**
   * Sets the height of this shape to the given amount.
   *
   * @param height is the height of the shape that this shape should be changed to.
   */
  void setShapeH(int height);
}
