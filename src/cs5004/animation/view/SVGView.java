package cs5004.animation.view;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import cs5004.animation.model.AnimationInterface;
import cs5004.animation.model.IAnimationModel;
import cs5004.animation.model.ShapeGetter;

/**
 * A class that represents a view for the animation that writes XML to create a .svg
 *     file to view the animation.
 */
public class SVGView implements IView {

  Appendable out;
  IAnimationModel animationModel;
  private int multiplier;
  private int x;
  private int y;

  /**
   * Constructor for SVGAnimation view that takes in the model for the animation and the tickspeed.
   * @param animationModel model used for the animation.
   * @param tickPerSec is the tick speed of the animation.
   */
  public SVGView(IAnimationModel animationModel, int tickPerSec) {
    if (tickPerSec < 1) {
      throw new IllegalArgumentException("Cannot pass a negative ticks per second");
    }
    this.animationModel = animationModel;
    out = new StringBuilder();
    this.multiplier = 1000 / tickPerSec;
    this.x = animationModel.getX();
    this.y = animationModel.getY();
  }

  //helper for the appendable
  private void tryAppend(String input) {
    try {
      out.append(input);
    } catch (IOException e) {
      throw new IllegalStateException("Bad Appendable");
    }
  }

  @Override
  public void play() {
    String string;
    tryAppend("<svg width=\"" + animationModel.getWidth() + "\" height=\""
            + animationModel.getHeight() + "\" version=\"1.1\"\n    "
            + "xmlns=\"http://www.w3.org/2000/svg\">\n");

    for (Map.Entry<String, ShapeGetter> entry : animationModel.getShapes().entrySet()) {
      List<AnimationInterface> commands = animationModel.getCommands().get(entry.getKey());
      switch (entry.getValue().getShapeType()) {
        case "Rectangle" :
          string = "rect";
          break;
        case "Oval" :
          string = "oval";
          break;
        case "Ellipse" :
          string = "ellipse";
          break;
        default:
          string = "";
      }
      this.tryAppend("<" + string + " id=\""
              + entry.getKey());
      this.tryAppend(this.getStartState(commands.get(0), string));
      this.tryAppend(this.commandsToString(commands, string));
      this.tryAppend("</" + string + ">\n\n");
    }
    tryAppend("</svg>");
  }

  @Override
  public String getText() {
    return this.out.toString();
  }

  private String getStartState(AnimationInterface command, String type) {
    StringBuilder output = new StringBuilder();
    switch (type) {
      case "rect":
        output = new StringBuilder("\" x=\"")
                .append(command.getShapeStartXPoint() - this.x)
                .append("\" y=\"").append(command.getShapeStartYPoint()
                        - this.y).append("\" width=\"")
                .append(command.getShapeStartW()).append("\" height=\"")
                .append(command.getShapeStartH())
                .append("\" fill=\"rgb(").append(command.getShapeStartColor()
                        .getRed()).append(",")
                .append(command.getShapeStartColor().getGreen()).append(",")
                .append(command.getShapeStartColor().getBlue())
                .append(")\" visibility=\"visible\" >\n");
        break;
      case "ellipse":
      case "oval":
        output = new StringBuilder("\" cx=\"")
                .append(command.getShapeStartXPoint() - this.x
                        + command.getShapeStartW() / 2)
                .append("\" cy=\"")
                .append(command.getShapeStartYPoint() - this.y
                        + command.getShapeStartH() / 2)
                .append("\" rx=\"")
                .append(command.getShapeStartW() / 2).append("\" ry=\"")
                .append(command.getShapeStartH() / 2)
                .append("\" fill=\"rgb(").append(command.getShapeStartColor()
                        .getRed()).append(",")
                .append(command.getShapeStartColor().getGreen()).append(",")
                .append(command.getShapeStartColor().getBlue())
                .append(")\" visibility=\"visible\" >\n");
        break;
      default:
        return output.toString();
    }
    return output.toString();
  }

  /**
   * This is a helper method for converting the commands into strings.
   * @param i is the command list
   * @param type of shape.
   * @return a string of the command.
   */
  private String commandsToString(List<AnimationInterface> i, String type) {
    StringBuilder output = new StringBuilder();
    String startString;
    String endString = "\" fill=\"freeze\" />\n";
    for (AnimationInterface controlCommand : i) {
      startString = "    <animate attributeType=\"xml\" begin=\""
              + controlCommand.getShapeStartTime() * multiplier + "ms\" dur=\""
              + (controlCommand.getShapeEndTime()
              - controlCommand.getShapeStartTime()) * multiplier + "ms\" "
              + "attributeName=\"";

      if (controlCommand.getStartOrientation() != controlCommand.getEndShapeOrientation()) {
        if (type.equals("rect")) {
          output.append("<animateTransform attributeName=\"transform\" attributeType=\"xml\""
                  + " type=\"rotate\" from=\"" + controlCommand.getStartOrientation() + " "
                  + (controlCommand.getShapeStartXPoint()
                  + controlCommand.getShapeStartW() / 2 - this.x) + " "
                  + (controlCommand.getShapeStartYPoint()
                  + controlCommand.getShapeStartH() / 2 - this.y)
                  + "\" to=\"" + controlCommand.getEndShapeOrientation() + " "
                  + (controlCommand.getShapeStartXPoint()
                  + controlCommand.getShapeStartW() / 2 - this.x) + " "
                  + (controlCommand.getShapeStartYPoint()
                  + controlCommand.getShapeStartH() / 2 - this.y) + "\" dur=\""
                  + ((controlCommand.getShapeStartTime()
                  - controlCommand.getShapeStartTime()) * multiplier) + "ms\" "
                  + "repeatCount=\"0\"/>\n");
        }
        else if (type.equals("ellipse")) {
          output.append("<animateTransform attributeName=\"transform\" attributeType=\"xml\""
                  + " type=\"rotate\" from=\"" + controlCommand.getStartOrientation() + " "
                  + (controlCommand.getShapeStartXPoint() - this.x
                  + controlCommand.getShapeStartW() / 2) + " "
                  + (controlCommand.getShapeStartYPoint() - this.y
                  + controlCommand.getShapeStartH() / 2)
                  + "\" to=\"" + controlCommand.getEndShapeOrientation() + " "
                  + (controlCommand.getShapeStartXPoint() - this.x
                  + controlCommand.getShapeStartW() / 2) + " "
                  + (controlCommand.getShapeStartYPoint() - this.y
                  + controlCommand.getShapeStartH() / 2)
                  + "\" dur=\"" + ((controlCommand.getShapeEndTime()
                  - controlCommand.getShapeStartTime()) * multiplier)
                  + "ms\" repeatCount=\"0\"/>\n");
        }
        continue;
      }
      if (controlCommand.getShapeStartXPoint() != controlCommand.getShapeEndXPoint()) {
        if (type.equals("rect")) {
          output.append(startString).append("x\" from=\"").append(controlCommand
                  .getShapeStartXPoint() - this.x)
                  .append("\" to=\"").append(controlCommand.getShapeEndXPoint()
                  - this.x).append(endString);
        }
        else {
          output.append(startString).append("cx\" from=\"")
                  .append(controlCommand.getShapeStartXPoint() - this.x
                          + controlCommand.getShapeStartW() / 2)
                  .append("\" to=\"")
                  .append(controlCommand.getShapeEndXPoint() - this.x
                          + controlCommand.getShapeEndW() / 2)
                  .append(endString);
        }
      }
      if (controlCommand.getShapeStartYPoint() != controlCommand.getShapeEndYPoint()) {
        if (type.equals("rect")) {
          output.append(startString).append("y\" from=\"")
                  .append(controlCommand.getShapeStartYPoint() - this.y)
                  .append("\" to=\"")
                  .append(controlCommand.getShapeEndYPoint() - this.y)
                  .append(endString);
        }
        else {
          output.append(startString).append("cy\" from=\"")
                  .append(controlCommand.getShapeStartYPoint() - this.y
                          + controlCommand.getShapeStartH() / 2)
                  .append("\" to=\"")
                  .append(controlCommand.getShapeEndYPoint() - this.y
                          + controlCommand.getShapeEndH() / 2)
                  .append(endString);
        }
      }
      if (controlCommand.getShapeStartH() != controlCommand.getShapeEndH()) {
        if (type.equals("rect")) {
          output.append(startString).append("height\" from=\"")
                  .append(controlCommand.getShapeStartH())
                  .append("\" to=\"").append(controlCommand
                  .getShapeEndH()).append(endString);
        }
        else {
          output.append(startString).append("ry\" from=\"")
                  .append(controlCommand.getShapeStartH() / 2)
                  .append("\" to=\"").append(controlCommand
                  .getShapeEndH() / 2).append(endString);
        }
      }
      if (controlCommand.getShapeStartW() != controlCommand.getShapeEndW()) {
        if (type.equals("rect")) {
          output.append(startString).append("width\" from=\"")
                  .append(controlCommand.getShapeStartW())
                  .append("\" to=\"").append(controlCommand
                  .getShapeEndW()).append(endString);
        }
        else {
          output.append(startString).append("ry\" from=\"")
                  .append(controlCommand.getShapeStartW() / 2)
                  .append("\" to=\"").append(controlCommand
                  .getShapeEndW() / 2).append(endString);
        }
      }
      if (controlCommand.getShapeStartColor().getRed() != controlCommand
              .getShapeEndColor().getRed()
              || controlCommand.getShapeStartColor().getGreen() != controlCommand
              .getShapeEndColor().getGreen()
              || controlCommand.getShapeStartColor().getBlue() != controlCommand
              .getShapeEndColor().getBlue()) {
        output.append(startString).append("fill\" from=\"rgb(")
                .append(controlCommand.getShapeStartColor().getRed()).append(",")
                .append(controlCommand.getShapeStartColor().getGreen()).append(",")
                .append(controlCommand.getShapeStartColor().getBlue()).append(")\" to=\"rgb(")
                .append(controlCommand.getShapeEndColor().getRed()).append(",")
                .append(controlCommand.getShapeEndColor().getGreen()).append(",")
                .append(controlCommand.getShapeEndColor().getBlue()).append(")")
                .append(endString);
      }
    }
    return output.toString();
  }

}
