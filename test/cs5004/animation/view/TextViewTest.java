package cs5004.animation.view;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import cs5004.animation.EasyAnimator;
import cs5004.animation.model.AModel;
import cs5004.animation.model.IAnimationModel;

import static org.junit.Assert.assertEquals;

/**
 * JUnit test for the text view.
 */
public class TextViewTest {
  private final ByteArrayOutputStream out = new ByteArrayOutputStream();
  private AModel.AnimationModelBuilder builder;
  private IAnimationModel model;

  @Before
  public void setUp() {
    this.builder = new AModel.AnimationModelBuilder();
    System.setOut(new PrintStream(out));
  }

  @Test
  public void testGetText() {
    this.model = this.builder.build();
    TextualView view = new TextualView(model);
    assertEquals(view.getText(), "");
  }

  @Test
  public void testMain() {
    EasyAnimator.main(new String[]{"-view", "text", "-in", "./src/cs5004/animation/startercode/"
            + "smalldemo.txt"});
    assertEquals(out.toString(), "Shape R Rectangle\n"
            + "Nothing R 1 200 200 50 100 0 255 0 0    10 200 200 50 100 0 255 0 0\n"
            + "Motion R 10 200 200 50 100 0 255 0 0    50 300 300 50 100 0 255 0 0\n"
            + "Nothing R 50 300 300 50 100 0 255 0 0    51 300 300 50 100 0 255 0 0\n"
            + "Size R 51 300 300 50 100 0 255 0 0    70 300 300 25 100 0 255 0 0\n"
            + "Motion R 70 300 300 25 100 0 255 0 0    100 200 200 25 100 0 255 0 0\n"
            + "\n"
            + "Shape C Ellipse\n"
            + "Nothing C 6 440 70 120 60 0 0 0 255    20 440 70 120 60 0 0 0 255\n"
            + "Motion C 20 440 70 120 60 0 0 0 255    50 440 250 120 60 0 0 0 255\n"
            + "Motion and Color C 50 440 250 120 60 0 0 0 255    70 440 370 120 60 0 0 170 85\n"
            + "Color C 70 440 370 120 60 0 0 170 85    80 440 370 120 60 0 0 255 0\n"
            + "Nothing C 80 440 370 120 60 0 0 255 0    100 440 370 120 60 0 0 255 0");
  }

  @Test
  public void testGetPlay() {
    this.model = this.builder.build();
    TextualView view = new TextualView(model);
    view.play();
    assertEquals(view.getText(), "");
  }
}