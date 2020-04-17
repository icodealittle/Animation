package cs5004.animation.model;

import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * A class for commands that implements all methods from the CommandInterface.
 */
public class AnimationImpl implements AnimationInterface {

  private String typeShape;
  protected int startShapeTime;
  private int startXPoint;
  private int startYPoint;
  private int startShapeWidth;
  private int startShapeHeight;
  private Color startShapeColor;
  protected int endShapeTime;
  private int endXPoint;
  private int endYPoint;
  private int endShapeWidth;
  private int endShapeHeight;
  private Color endShapeColor;
  private int startShapeOrient;
  private int endShapeOrient;

  /**
   * Constructor for MCommand that initializes each of the fields of the command.
   * @param type represents what type of command this is.
   * @param t1 represents starting time of the command.
   * @param x1 represents the starting x coordinate of the shape in the command.
   * @param y1 represents the starting y coordinate of the shape in this command.
   * @param w1 represents the starting width of the shape in this command.
   * @param h1 represents the starting height of the shape in this command.
   * @param r1 represents the starting value for red of the shape.
   * @param g1 represents the starting value for green of the shape.
   * @param b1 represents the starting value for blue of the shape.
   * @param t2 represents the ending time of the command.
   * @param x2 represents the ending x coordinate of the shape in this command.
   * @param y2 represents the ending y coordinate of the shape in this command.
   * @param w2 represents the ending width of the shape in this command.
   * @param h2 represents the ending height of the shape.
   * @param r2 represents the ending red value for the shape.
   * @param g2 represents the ending green value for the shape.
   * @param b2 represents the ending blue value for the shape.
   * @throws IllegalArgumentException when the start time or end time is negative, if the width
   *         or height is negative at any point, or if the start time is after the end time.
   */
  public AnimationImpl(String type, int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1,
                       int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2)
          throws IllegalArgumentException {
    if (t1 > t2 || t1 < 0) {
      throw new IllegalArgumentException("Invalid time.");
    }

    if (w1 < 0 || w2 < 0) {
      throw new IllegalArgumentException("Width cannot be negative.");
    }

    if (h1 < 0 || h2 < 0) {
      throw new IllegalArgumentException("Height cannot be negative.");
    }

    if (r1 < 0 || r2 < 0 || g1 < 0 || g2 < 0 || b1 < 0 || b2 < 0
            || r1 > 255 || r2 > 255 || g1 > 255 || g2 > 255 || b1 > 255 || b2 > 255) {
      throw new IllegalArgumentException("RGB values have to be between 0 to 255.");
    }

    this.typeShape = type;
    this.startShapeTime = t1;
    this.startXPoint = x1;
    this.startYPoint = y1;
    this.startShapeWidth = w1;
    this.startShapeHeight = h1;
    this.startShapeColor = new Color(r1, g1, b1);
    this.endShapeTime = t2;
    this.endXPoint = x2;
    this.endYPoint = y2;
    this.endShapeWidth = w2;
    this.endShapeHeight = h2;
    this.endShapeColor = new Color(r2, g2, b2);
  }

  @Override
  public int getShapeStartTime() {
    return this.startShapeTime;
  }

  @Override
  public int getShapeEndTime() {
    return this.endShapeTime;
  }

  @Override
  public String getCommandType() {
    return this.typeShape;
  }

  @Override
  public int getShapeStartXPoint() {
    return startXPoint;
  }

  @Override
  public int getShapeStartYPoint() {
    return startYPoint;
  }

  @Override
  public int getShapeStartW() {
    return startShapeWidth;
  }

  @Override
  public int getShapeStartH() {
    return startShapeHeight;
  }

  @Override
  public int getShapeEndXPoint() {
    return endXPoint;
  }

  @Override
  public int getShapeEndYPoint() {
    return endYPoint;
  }

  @Override
  public int getShapeEndW() {
    return endShapeWidth;
  }

  @Override
  public int getShapeEndH() {
    return endShapeHeight;
  }

  @Override
  public Color getShapeStartColor() {
    return startShapeColor;
  }

  @Override
  public Color getShapeEndColor() {
    return endShapeColor;
  }

  @Override
  public int getEndShapeOrientation() {
    return this.startShapeOrient;
  }

  @Override
  public int getStartOrientation() {
    return this.endShapeOrient;
  }

  @Override
  public InterfaceShape setState(int time, InterfaceShape shape) {
    if (time < this.startShapeTime || time > this.endShapeTime) {
      throw new IllegalArgumentException("Invalid time");
    }
    else if (this.startShapeTime == this.endShapeTime) {
      shape.setShapePosition(new Point2D.Double(this.endXPoint, this.endYPoint));
      shape.setShapeW(this.endShapeWidth);
      shape.setShapeH(this.endShapeHeight);
      shape.setShapeColor(this.endShapeColor);
      return shape;
    }

    int newX = this.findPoint(time, this.startXPoint, this.endXPoint);
    int newY = this.findPoint(time, this.startYPoint, this.endYPoint);
    shape.setShapePosition(new Point2D.Double(newX, newY));

    shape.setShapeW(this.findPoint(time, this.startShapeWidth, this.endShapeWidth));
    shape.setShapeH(this.findPoint(time, this.startShapeHeight, this.endShapeHeight));

    int newRed = this.findPoint(time, this.startShapeColor.getRed(),
            this.endShapeColor.getRed());
    int newGreen = this.findPoint(time, this.startShapeColor.getGreen(),
            this.endShapeColor.getGreen());
    int newBlue = this.findPoint(time, this.startShapeColor.getBlue(),
            this.endShapeColor.getBlue());
    shape.setShapeColor(new Color(newRed, newGreen, newBlue));

    return shape;
  }

  /**
   * This will find the difference between a start and point.
   * @param time is time.
   * @param start start time.
   * @param end end time.
   * @return the end point.
   */
  protected int findPoint(int time, int start, int end) {
    if (this.startShapeTime == this.endShapeTime) {
      return end;
    } else {
      return ((end - start) * (time - this.startShapeTime))
              / (this.endShapeTime - this.startShapeTime) + start;
    }
  }

}
