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
 * JUnit tests for the SVG view.
 */
public class SVGViewTest {
  private final ByteArrayOutputStream out = new ByteArrayOutputStream();
  private AModel.AnimationModelBuilder builder;
  private IAnimationModel model;

  @Before
  public void setUp() {
    this.builder = new AModel.AnimationModelBuilder();
    System.setOut(new PrintStream(out));
  }


  @Test(expected = IllegalArgumentException.class)
  public void testBadConstructor1() {
    model = builder.build();
    SVGView view = new SVGView(model, 0);
    SVGView view1 = new SVGView(model, -6);
  }

  @Test
  public void testAppendable() {
    this.model = this.builder.build();
    IView view = new SVGView(model, 1);
    assertEquals(view.getText(), "");
    view.play();
    assertEquals(view.getText(), "<svg width=\"1000\" height=\"1000\""
            + " version=\"1.1\"\n"
            + "    xmlns=\"http://www.w3.org/2000/svg\">\n</svg>");
  }


  @Test
  public void testMain() {
    EasyAnimator.main(new String[]{"-view", "svg", "-in", "./src/cs5004/animation"
            + "/startercode/smalldemo.txt"});
    assertEquals("<svg width=\"360\" height=\"360\" version=\"1.1\"\n"
            + "    xmlns=\"http://www.w3.org/2000/svg\">\n"
            + "<rect id=\"R\" x=\"0\" y=\"130\" width=\"50\" height=\"100\" fill=\"rgb(255,0,0)\""
            + " visibility=\"visible\" >\n"
            + "    <animate attributeType=\"xml\" begin=\"10000ms\" dur=\"40000ms\""
            + " attributeName=\"x\" from=\"0\" to=\"100\" fill=\"freeze\" />\n"
            + "    <animate attributeType=\"xml\" begin=\"10000ms\" dur=\"40000ms\" "
            + "attributeName=\"y\" from=\"130\" to=\"230\" fill=\"freeze\" />\n"
            + "    <animate attributeType=\"xml\" begin=\"51000ms\" dur=\"19000ms\" "
            + "attributeName=\"width\" from=\"50\" to=\"25\" fill=\"freeze\" />\n"
            + "    <animate attributeType=\"xml\" begin=\"70000ms\" dur=\"30000ms\" "
            + "attributeName=\"x\" from=\"100\" to=\"0\" fill=\"freeze\" />\n"
            + "    <animate attributeType=\"xml\" begin=\"70000ms\" dur=\"30000ms\" "
            + "attributeName=\"y\" from=\"230\" to=\"130\" fill=\"freeze\" />\n"
            + "</rect>\n\n"
            + "<ellipse id=\"C\" cx=\"300\" cy=\"30\" rx=\"60\" ry=\"30\" "
            + "fill=\"rgb(0,0,255)\" visibility=\"visible\" >\n"
            + "    <animate attributeType=\"xml\" begin=\"20000ms\" dur=\"30000ms\" "
            + "attributeName=\"cy\" from=\"30\" to=\"210\" fill=\"freeze\" />\n"
            + "    <animate attributeType=\"xml\" begin=\"50000ms\" dur=\"20000ms\" "
            + "attributeName=\"cy\" from=\"210\" to=\"330\" fill=\"freeze\" />\n"
            + "    <animate attributeType=\"xml\" begin=\"50000ms\" dur=\"20000ms\" "
            + "attributeName=\"fill\" from=\"rgb(0,0,255)\" to=\"rgb(0,170,85)\" "
            + "fill=\"freeze\" />\n"
            + "    <animate attributeType=\"xml\" begin=\"70000ms\" dur=\"10000ms\" "
            + "attributeName=\"fill\" from=\"rgb(0,170,85)\" to=\"rgb(0,255,0)\" fill=\"freeze\" "
            + "/>\n</ellipse>\n\n</svg>", out.toString());
  }


}