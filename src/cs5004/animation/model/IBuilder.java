package cs5004.animation.model;

import java.awt.Dimension;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A constructor that represent the builder of editable view.
 */
public interface IBuilder extends AnimationBuilder {

  /**
   * a getter that return the shapes form the builder.
   * @return a mapping of the shapes identification in the builder.
   */
  LinkedHashMap<String, ShapeGetter> getShapes();

  /**
   * A getter that return the commands from the builder.
   * @return a mapping of the shape identification in the builder.
   */
  LinkedHashMap<String, List<AnimationInterface>> getCommands();

  /**
   * A getter that return the space needed form the builder.
   * @return the dimension of the shape in the animation.
   */
  Dimension getNeededSpace();

  /**
   * A setter that return the view map of all shapes in the animation and their ID.
   * @param shapes all the shapes in the animation.
   */
  void setShapes(Map<String, ShapeGetter> shapes);

}
