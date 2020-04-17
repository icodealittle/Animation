package cs5004.animation.model;

import org.junit.Before;
import org.junit.Test;
import java.awt.Color;
import java.awt.geom.Point2D;

import static org.junit.Assert.assertEquals;

/**
 * JUnit tests for all the shapes (RECTANGLE,OVAL, AND ELLIPSE).
 */
public class InterfaceShapeTest {
  private Rectangle rect;
  private Ellipse ellipse;
  private Oval oval;

  @Before
  public void setUp() {
    rect = new Rectangle(1,3, new Point2D.Double(4,8), Color.BLUE);
    ellipse = new Ellipse(2,2, new Point2D.Double(1,1), Color.GREEN);
    oval =  new Oval(6,4,new Point2D.Double(2,5), Color.RED);
  }

  @Test(expected = NullPointerException.class)
  public void testNullPosition() {
    oval = new Oval(4,5,null, Color.GREEN);
    rect = new Rectangle(1,3, null, Color.GREEN);
    ellipse = new Ellipse(2,2, null, Color.RED);

  }

  @Test (expected = NullPointerException.class)
  public void testNullColor() {
    rect = new Rectangle(1,3, new Point2D.Double(4,8), null);
    ellipse = new Ellipse(2,2, new Point2D.Double(1,1), null);
    oval =  new Oval(6,4,new Point2D.Double(2,5), null);
  }

  @Test
  public void testGetShape() {
    assertEquals(ellipse.getShapeType(), "Ellipse");
    assertEquals(rect.getShapeType(), "Rectangle");
    assertEquals(oval.getShapeType(), "Oval");
  }

  @Test
  public void testGetColor() {
    assertEquals(oval.getColor(),Color.RED);
    assertEquals(ellipse.getColor(), Color.GREEN);
    assertEquals(rect.getColor(), Color.BLUE);
  }

  @Test
  public void testGetPosition() {
    assertEquals(oval.getPosition(), new Point2D.Double(2,5));
    assertEquals(ellipse.getPosition(), new Point2D.Double(1,1));
    assertEquals(rect.getPosition(), new Point2D.Double(4,8));
  }


  @Test
  public void testGetWidthAndHeight() {
    assertEquals(ellipse.getHeight(), 2);
    assertEquals(rect.getHeight(), 3);
    assertEquals(ellipse.getWidth(), 2);
    assertEquals(rect.getWidth(), 1);
    assertEquals(oval.getHeight(), 4);
    assertEquals(oval.getWidth(), 6);
  }

  @Test
  public void testSetWidth() {
    this.oval.setShapeW(2);
    this.ellipse.setShapeW(5);
    this.rect.setShapeW(10);
    assertEquals(ellipse.getWidth(), 5);
    assertEquals(oval.getWidth(), 2);
    assertEquals(rect.getWidth(), 10);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBadHeightAndWidth() {
    this.rect.setShapeW(-10);
    this.oval.setShapeW(-2);
    this.ellipse.setShapeW(-5);
    this.ellipse.setShapeH(-6);
    this.oval.setShapeH(-1);
    this.rect.setShapeH(-10);
  }

  @Test
  public void testSetHeight() {
    this.oval.setShapeH(10);
    this.ellipse.setShapeH(2);
    this.rect.setShapeH(9);
    assertEquals(rect.getHeight(), 9);
    assertEquals(ellipse.getHeight(), 2);
    assertEquals(oval.getHeight(), 10);
  }

  @Test
  public void testSetPosition() {
    this.oval.setShapePosition(new Point2D.Double(10,20));
    assertEquals(oval.getPosition(), new Point2D.Double(10,20));
    this.ellipse.setShapePosition(new Point2D.Double(20,20));
    assertEquals(ellipse.getPosition(), new Point2D.Double(20,20));
    this.rect.setShapePosition(new Point2D.Double(25,15));
    assertEquals(rect.getPosition(), new Point2D.Double(25,15));
  }

  @Test (expected = NullPointerException.class)
  public void testBadSetPosition() {
    this.ellipse.setShapePosition(null);
    this.rect.setShapePosition(null);
    this.oval.setShapePosition(null);
  }

  @Test
  public void testSetColor() {
    this.ellipse.setShapeColor(Color.RED);
    assertEquals(ellipse.getColor(), Color.RED);
    this.oval.setShapeColor(Color.WHITE);
    assertEquals(oval.getColor(), Color.WHITE);
    this.rect.setShapeColor(Color.BLACK);
    assertEquals(rect.getColor(), Color.BLACK);
  }

  @Test (expected = NullPointerException.class)
  public void testBadSetColor() {
    this.oval.setShapeColor(null);
    this.rect.setShapeColor(null);
    this.ellipse.setShapeColor(null);
  }

}