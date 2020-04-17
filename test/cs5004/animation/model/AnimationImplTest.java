package cs5004.animation.model;

import org.junit.Test;

import java.awt.Color;
import java.awt.geom.Point2D;

import static org.junit.Assert.assertEquals;

/**
 * JUnit test for AnimationImplTest.
 */
public class AnimationImplTest {

  @Test
  public void testCheckGetStartTime() {
    AnimationInterface move = new AnimationImpl("M", 1, 10, 10, 10, 30,
            0, 255, 0, 5, 20, 10, 10, 30, 0, 255, 0);
    assertEquals(move.getShapeStartTime(), 1);
  }

  @Test
  public void testCheckGetEndTime() {
    AnimationInterface move = new AnimationImpl("M", 1, 10,
            10, 10, 30, 0, 255, 0, 5, 20, 10,
            10, 30, 0, 255, 0);
    AnimationInterface move1 = new AnimationImpl("M", 20, 10, 10,
            10, 30, 0, 255, 0, 25, 70, 10, 10,
            30, 0, 255, 0);
    assertEquals(move.getShapeEndTime(), 5);
    assertEquals(move1.getShapeEndTime(), 25);
  }

  @Test
  public void testCheckSetState() {
    AnimationInterface move = new AnimationImpl("M", 1, 10, 10, 10, 30,
            0, 255, 0, 5, 20, 10, 10, 30, 0, 255, 0);
    Rectangle rectangle = new Rectangle(1, 3, new Point2D.Double(4, 8),
            Color.BLUE);
    move.setState(2, rectangle);
    assertEquals(rectangle.getPosition(), new Point2D.Double(12, 10));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCheckBadState() {
    AnimationInterface move = new AnimationImpl("M", 1, 10, 10, 10, 30,
            0, 255, 0, 5, 20, 10, 10, 30, 0, 255, 0);
    Rectangle rectangle = new Rectangle(1, 3, new Point2D.Double(4, 8),
            Color.BLUE);
    move.setState(-4, rectangle);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCheckBadColor() {
    AnimationInterface move = new AnimationImpl("M", 1, 10, 10, 10, 30,
            0, 255, -10, 5, 20, 10, 10, 30, -1, 255, 0);
    AnimationInterface move1 = new AnimationImpl("M", 1, 10, 10, 10, 30,
            -10, 255, 0, 5, 20, 10, 10, 30, 0, -255, 0);
    AnimationInterface move2 = new AnimationImpl("M", 1, 10, 10, 10, 30,
            0, -5, 0, 5, 20, 10, 10, 30, 0, 255, -255);
  }


}

