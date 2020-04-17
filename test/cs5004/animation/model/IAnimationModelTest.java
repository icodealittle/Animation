package cs5004.animation.model;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * JUnit test for the animation builder and ANimationModel INterface.
 */
public class IAnimationModelTest {
  private AModel.AnimationModelBuilder builder;
  private IAnimationModel model;

  @Before
  public void setUp() {
    this.builder = new AModel.AnimationModelBuilder();
  }

  @Test
  public void testBuild() {
    this.builder = new AModel.AnimationModelBuilder();
    this.model = this.builder.build();
    assertEquals("", this.model.toString());
  }

  @Test
  public void testAddCommand() {
    this.builder.declareShape("R1", "Rectangle")
            .addMotion("R1", 0, 10, 10, 10, 30, 0, 255, 0, 5, 20,
                    10, 10, 30, 0, 255, 0);
    this.model = this.builder.build();
    assertEquals("Shape R1 Rectangle\n"
                    + "Motion R1 0 10.0 10.0 10 30 0 255 0    5 20.0 10.0 10 30 0 255 0",
            this.model.toString());
  }


  @Test(expected = IllegalArgumentException.class)
  public void testAddShapeExceptionsSameName() {
    this.builder.declareShape("R1", "Rectangle").declareShape("R1", "Ellipse");
  }

  @Test(expected = IllegalStateException.class)
  public void testAddCommandExceptionsOverlaps() {
    this.builder.declareShape("R1", "Rectangle")
            .addMotion("R1", 0, 5, 10, 10, 30, 0, 255, 0, 5, 20,
                    10, 10, 30, 0, 255, 0)
            .addMotion("R2", 5, 20, 10, 10, 30, 0, 255, 0, 10, 20,
                    20, 10, 30, 0, 255, 0);
    this.model = this.builder.build();
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidOverlap() {
    this.builder.declareShape("R1", "Rectangle")
            .addMotion("R1", 0, 5, 10, 10, 30, 0, 255, 0, 5, 20,
                    10, 10, 30, 0, 255, 0)
            .addMotion("R1", 3, 6, 10, 10, 30, 0, 255, 0, 7, 26,
                    15, 10, 30, 0, 255, 0);
    this.model = this.builder.build();
    assertEquals("Shape R1 Rectangle\n"
                    + "Motion R1 0 10.0 10.0 10 30 0 255 0    5 20.0 10.0 10 30 0 255 0",
            this.model.toString());
  }

  @Test
  public void testGetWidth() {
    this.model = this.builder.build();
    assertEquals(model.getWidth(), 1000);
  }

  @Test
  public void testGetFinalTick() {
    this.builder.declareShape("R1", "Rectangle")
            .addMotion("R1", 0, 10, 10, 10, 30, 0, 255, 0, 5,
                    20, 10, 10, 30, 0, 255, 0)
            .addMotion("R1", 5, 20, 10, 10, 30, 0, 255, 0, 10,
                    20, 20, 10, 30, 0, 255, 0)
            .declareShape("R2", "Ellipse")
            .addMotion("R2", 0, 1, 1, 20, 20, 255, 0, 0, 30,
                    20, 20, 20, 20, 255, 0, 0);
    this.model = this.builder.build();
    assertEquals(model.getFinalTick(), 30);
  }

  @Test
  public void testGetShapes() {
    this.builder.declareShape("R1", "Rectangle")
            .addMotion("R1", 0, 10, 10, 10, 30, 0, 255, 0, 5, 20,
                    10, 10, 30, 0, 255, 0)
            .addMotion("R1", 5, 20, 10, 10, 30, 0, 255, 0, 10, 20,
                    20, 10, 30, 0, 255, 0)
            .declareShape("R2", "Ellipse")
            .addMotion("R2", 0, 1, 1, 20, 20, 255, 0, 0, 10, 20,
                    20, 20, 20, 255, 0, 0);
    this.model = this.builder.build();
    LinkedHashMap<String, ShapeGetter> map = new LinkedHashMap<>();
    map.put("R1", new Rectangle());
    map.put("R2", new Ellipse());
    assertEquals(model.getShapes(), map);
  }

  @Test
  public void testGetCommands() {
    this.builder.declareShape("R1", "Rectangle")
            .addMotion("R1", 0, 10, 10, 10, 30, 0, 255, 0, 5, 20,
                    10, 10, 30, 0, 255, 0)
            .addMotion("R1", 5, 20, 10, 10, 30, 0, 255, 0, 10, 20,
                    20, 10, 30, 0, 255, 0)
            .declareShape("R2", "Ellipse")
            .addMotion("R2", 0, 1, 1, 20, 20, 255, 0, 0, 10, 20,
                    20, 20, 20, 255, 0, 0);
    this.model = this.builder.build();
    LinkedHashMap<String, List<AnimationInterface>> map = new LinkedHashMap<>();
    List<AnimationInterface> commands1 = new ArrayList<>();
    List<AnimationInterface> commands2 = new ArrayList<>();
    commands1.add(new AnimationImpl("Motion", 0, 10, 10, 10, 30, 0, 255, 0, 5,
            20, 10, 10, 30, 0, 255, 0));
    commands1.add(new AnimationImpl("Motion", 6, 20, 10, 10, 30, 0, 255, 0, 10,
            20, 20, 10, 30, 0, 255, 0));
    commands2.add(new AnimationImpl("Motion", 0, 1, 1, 20, 20, 255, 0, 0, 10,
            20, 20, 20, 20, 255, 0, 0));
    map.put("R1", commands1);
    map.put("R2", commands2);
    assertEquals(model.getCommands().get("R1").get(1).getShapeStartXPoint(),20 );
    assertEquals(model.getCommands().get("R2").get(0).getShapeEndW(),20 );
  }


}