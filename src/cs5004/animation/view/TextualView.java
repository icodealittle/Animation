package cs5004.animation.view;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import cs5004.animation.model.AnimationInterface;
import cs5004.animation.model.IAnimationModel;
import cs5004.animation.model.ShapeGetter;

/**
 * A class that represents a text description of the given animation.
 */
public class TextualView implements IView {

  private Appendable out;
  private IAnimationModel animationModel;

  /**
   * Constructor for TextView that takes the model to convert into a string.
   * @param animationModel is the model for which the TextView will create a constructor for.
   */
  public TextualView(IAnimationModel animationModel) {
    this.animationModel = animationModel;
    out = new StringWriter();
  }

  @Override
  public String getText() {
    return this.out.toString();
  }

  @Override
  public void play() {
    StringBuilder stringProduce = new StringBuilder();
    for (Map.Entry<String, List<AnimationInterface>> entry : this.animationModel
            .getCommands().entrySet()) {
      ShapeGetter shape = this.animationModel.getShapes().get(entry.getKey());
      stringProduce.append("Shape ").append(entry.getKey()).append(" ")
              .append(shape.getShapeType())
              .append("\n");
      for (AnimationInterface work : entry.getValue()) {
        StringBuilder temp = new StringBuilder(work.getCommandType()).append(" ");
        temp.append(entry.getKey()).append(" ")
                .append(work.getShapeStartTime()).append(" ")
                .append(work.getShapeStartXPoint()).append(" ")
                .append(work.getShapeStartYPoint()).append(" ")
                .append(work.getShapeStartW()).append(" ")
                .append(work.getShapeStartH()).append(" ")
                .append(work.getStartOrientation()).append(" ")
                .append(work.getShapeStartColor().getRed()).append(" ")
                .append(work.getShapeStartColor().getGreen()).append(" ")
                .append(work.getShapeStartColor().getBlue()).append("    ")
                .append(work.getShapeEndTime()).append(" ")
                .append(work.getShapeEndXPoint()).append(" ")
                .append(work.getShapeEndYPoint()).append(" ")
                .append(work.getShapeEndW()).append(" ")
                .append(work.getShapeEndH()).append(" ")
                .append(work.getEndShapeOrientation()).append(" ")
                .append(work.getShapeEndColor().getRed()).append(" ")
                .append(work.getShapeEndColor().getGreen()).append(" ")
                .append(work.getShapeEndColor().getBlue()).append("\n");
        stringProduce.append(temp);
      }
      stringProduce.append("\n");
    }

    if (stringProduce.length() != 0) {
      stringProduce.delete(stringProduce.length() - 2, stringProduce.length());
    }
    out = stringProduce;
  }

}
