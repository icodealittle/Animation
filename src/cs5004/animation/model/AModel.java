package cs5004.animation.model;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A class that represent an animation model.
 * This method contains a list of shapes, which represent the shape(s) that is like appear on the
 *     screen, as well as the command that are implement for those shapes.
 *     The commands are being represents as the actions of each shapes that being input the values
 *     what its in to perform.
 */
public class AModel implements IAnimationModel {

  private String startOrientation;
  private String endOrientation;
  private int x;
  private int y;
  private int width;
  private int height;
  private LinkedHashMap<String, InterfaceShape> shapes;
  private LinkedHashMap<String, List<AnimationInterface>> commands;

  /**
   * A constructor that represent a private method for the model.
   * This method only allows the provided builder method to create new models.
   * @param commands utilizing the LinkedHashMap function that allow us to connects a shape
   *                 identification. This also allow all the commands to associate the
   *                 identification are being input in time order manners.
   * @param shapes utilizing the LinkedHashMap function that allow us to connect identification with
   *               their prefer shape. It also to ensure that identification are unique to
   *               one-on-one shapes.
   * @param x the X coordinate point of where the shape at.
   * @param y the Y coordinate of where the shape at.
   * @param width the width of the shape.
   * @param height the height of the shape.
   */
  private AModel(LinkedHashMap<String, List<AnimationInterface>> commands,
                 LinkedHashMap<String, InterfaceShape> shapes, int x, int y, int width,
                 int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.shapes = shapes;
    this.commands = commands;
  }

  @Override
  public int getX() {
    return this.x;
  }

  @Override
  public int getY() {
    return this.y;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public LinkedHashMap<String, ShapeGetter> getShapes() {
    LinkedHashMap<String, ShapeGetter> output = new LinkedHashMap<>();
    for (String key : shapes.keySet()) {
      output.put(key, shapes.get(key));
    }
    return output;
  }

  //finds the index of a command.
  private static int indexOfCommand(List<AnimationInterface> list, int startTime) {
    if (list.isEmpty()) {
      return 0;
    }

    int indexMin = 0;
    int indexMax = list.size() - 1;

    while (indexMin < indexMax) {
      int indexMiddle = (indexMin + indexMax) / 2;
      int middleTime = list.get(indexMiddle).getShapeStartTime();

      if (startTime == middleTime) {
        return indexMiddle;
      }
      else if (startTime > middleTime) {
        indexMin = indexMiddle + 1;
      }
      else {
        indexMax = indexMiddle - 1;
      }
    }

    if (startTime < list.get(indexMin).getShapeStartTime()) {
      return indexMin - 1;
    }
    else {
      return indexMin;
    }
  }


  @Override
  public List<ShapeGetter> getShapeState(int time) {
    List<ShapeGetter> output = new ArrayList<>();
    for (Map.Entry<String, List<AnimationInterface>> entry : this.commands.entrySet()) {
      String id = entry.getKey();
      InterfaceShape shape = this.shapes.get(id);

      int index = indexOfCommand(this.commands.get(id), time);
      if (index == -1) {
        continue;
      }
      if (time <= this.commands.get(id).get(index).getShapeEndTime()) {
        this.commands.get(id).get(index).setState(time, shape);
      }
      else {
        continue;
      }
      double newX = shape.getPosition().getX() - this.x;
      double newY = shape.getPosition().getY() - this.y;
      shape.setShapePosition(new Point2D.Double(newX, newY));
      output.add(shape);
    }
    return output;
  }

  @Override
  public LinkedHashMap<String, List<AnimationInterface>> getCommands() {
    return this.commands;
  }


  @Override
  public int getFinalTick() {
    int output = 0;
    for (List<AnimationInterface> commandList : this.commands.values()) {
      output = Math.max(output, commandList.get(commandList.size() - 1).getShapeEndTime());
    }
    return output;
  }

  @Override
  public String toString() {
    StringBuilder output = new StringBuilder();
    for (Map.Entry<String, List<AnimationInterface>> entry : this.commands.entrySet()) {
      InterfaceShape shape = this.shapes.get(entry.getKey());
      output.append("Shape ").append(entry.getKey()).append(" ").append(shape.getShapeType())
              .append("\n");
      for (AnimationInterface command : entry.getValue()) {
        StringBuilder temp = new StringBuilder(command.getCommandType()).append(" ")
                .append(entry.getKey()).append(" ").append(command.getShapeStartTime()).append(" ");

        command.setState(command.getShapeStartTime(), shape);
        temp.append(this.getShapeInformation(shape)).append("    ");

        command.setState(command.getShapeEndTime(), shape);
        temp.append(command.getShapeEndTime()).append(" ")
                .append(this.getShapeInformation(shape)).append("\n");
        output.append(temp);
      }
      output.append("\n");
    }
    return output.toString().trim();
  }

  /**
   * A Helper constructor method that gives the user a description of the give shape that
   *     implements.
   * @param shape the shape that the method is outputting a description for.
   * @return description of the shape in a string form.
   */
  private String getShapeInformation(InterfaceShape shape) {
    StringBuilder tempShape = new StringBuilder();
    tempShape.append(shape.getPosition().getX()).append(" ")
            .append(shape.getPosition().getY()).append(" ")
            .append(shape.getWidth()).append(" ")
            .append(shape.getHeight()).append(" ")
            .append(shape.getColor().getRed()).append(" ")
            .append(shape.getColor().getGreen()).append(" ")
            .append(shape.getColor().getBlue());
    return tempShape.toString();
  }

  /**
   * A builder class that implements all methods from AnimationBuilder.
   */
  public static class AnimationModelBuilder implements IBuilder {
    private int x = 0;
    private int y = 0;
    private int width = 1000;
    private int height = 1000;
    private LinkedHashMap<String, InterfaceShape> shapes;
    private LinkedHashMap<String, List<AnimationInterface>> commands;

    /**
     * A construct method for AnimationModelBuilder in which the method initialize an empty list of
     * command.
     */
    public AnimationModelBuilder() {
      this.shapes = new LinkedHashMap<>();
      this.commands = new LinkedHashMap<>();

    }

    @Override
    public IAnimationModel build() {

      for (String p : this.shapes.keySet()) {
        if (!this.shapes.containsKey(p)) {
          throw new IllegalArgumentException("Must have a command");
        }
      }
      for (String p : this.commands.keySet()) {
        if (!this.shapes.containsKey(p)) {
          throw new IllegalStateException("Must have a shape");
        }
      }
      return new AModel(this.commands, this.shapes, this.x, this.y,
              this.width, this.height);
    }

    @Override
    public AnimationBuilder setBounds(int x, int y, int width, int height) {
      if (width < 0 || height < 0) {
        throw new IllegalArgumentException("Only non-negative integer for width and height.");
      }
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      return this;
    }

    @Override
    public AnimationBuilder declareShape(String name, String type) {
      if (this.shapes.containsKey(name)) {
        throw new IllegalArgumentException("Name is used already");
      }

      InterfaceShape shape;

      switch (type.toLowerCase()) {
        case "rectangle":
          shape = new Rectangle();
          break;
        case "oval":
          shape = new Oval();
          break;
        case "ellipse":
          shape = new Ellipse();
          break;
        default:
          shape = null;
      }
      if (shape == null) {
        throw new IllegalArgumentException("Invalid");
      }
      this.shapes.put(name, shape);
      return this;
    }

    @Override
    public AnimationBuilder addMotion(String name, int t1, int x1, int y1, int w1, int h1, int r1,
                                      int g1, int b1, int t2, int x2, int y2, int w2, int h2,
                                      int r2, int g2, int b2) {
      String type = this.getType(x1, y1, w1, h1, r1, g1, b1, x2, y2, w2, h2, r2, g2, b2);
      AnimationInterface command = new AnimationImpl(type, t1, x1, y1, w1, h1, r1, g1, b1,
              t2, x2, y2, w2, h2, r2, g2, b2);
      if (this.commands.containsKey(name)) {
        this.addCommand(name, this.commands.get(name), command,
                indexOfCommand(this.commands.get(name), command.getShapeStartTime()) + 1);
      } else {
        this.commands.put(name, new ArrayList<>(Arrays.asList(command)));
      }
      return this;
    }

    // This will get the type of animation based on the parameters.
    private String getType(int x1, int y1, int w1, int h1, int r1, int g1, int b1, int x2, int y2,
                           int w2, int h2, int r2, int g2, int b2) {
      String description = "";

      if (x1 != x2 || y1 != y2) {
        description += " and Motion";
      }
      if (w1 != w2 || h1 != h2) {
        description += " and Size";
      }
      if (r1 != r2 || g1 != g2 || b1 != b2) {
        description += " and Color";
      }

      if (description.length() == 0) {
        return "Nothing";
      } else {
        return description.substring(5);
      }
    }

    /**
     * This will add commands to a list at a certain index.
     * @param id is the name of the shape.
     * @param list the list.
     * @param command command.
     * @param addIndex the index being added.
     */
    private void addCommand(String id, List<AnimationInterface> list, AnimationInterface command,
                            int addIndex) {
      int startTick = command.getShapeStartTime();
      InterfaceShape shapeCopy1 = this.shapes.get(id).duplicate();
      InterfaceShape shapeCopy2 = this.shapes.get(id).duplicate();

      if (addIndex == 0) {
        list.add(addIndex, command);
        return;
      }

      AnimationInterface previousCommand = list.get(addIndex - 1);

      previousCommand.setState(previousCommand.getShapeEndTime(), shapeCopy1);
      command.setState(startTick, shapeCopy2);

      if (previousCommand.getShapeEndTime() != startTick) {
        throw new IllegalArgumentException("Need overlap at their start/end times");
      }
      else if (this.shapesAreEqual(shapeCopy1, shapeCopy2)) {
        list.add(addIndex, command);
      }
      else {
        throw new IllegalArgumentException("Invalid movement");
      }
    }

    /**
     * This checks if the shapes are equal.
     * @param shape1 first shape.
     * @param shape2 second shape.
     * @return boolean, true if they are.
     */
    private boolean shapesAreEqual(InterfaceShape shape1, InterfaceShape shape2) {
      return shape1.getShapeType().equals(shape2.getShapeType())
              && shape1.getWidth() == shape2.getWidth()
              && shape1.getHeight() == shape2.getHeight()
              && shape1.getPosition().getX() == shape2.getPosition().getX()
              && shape1.getPosition().getY() == shape2.getPosition().getY()
              && shape1.getOrientation() == shape2.getOrientation()
              && shape1.getColor().getRGB() == shape2.getColor().getRGB();
    }

    @Override
    public LinkedHashMap<String, ShapeGetter> getShapes() {
      LinkedHashMap<String, ShapeGetter> shapeOutput = new LinkedHashMap<>();
      for (String keyShapes : shapes.keySet()) {
        shapeOutput.put(keyShapes, shapes.get(keyShapes));
      }
      return shapeOutput;
    }

    @Override
    public LinkedHashMap<String, List<AnimationInterface>> getCommands() {
      LinkedHashMap<String, List<AnimationInterface>> output = new LinkedHashMap<>();
      for (String key : this.commands.keySet()) {
        List<AnimationInterface> newCommands = new ArrayList<>();
        for (AnimationInterface command : this.commands.get(key)) {
          newCommands.add(command);
        }
        output.put(key, newCommands);
      }
      return output;
    }

    @Override
    public Dimension getNeededSpace() {
      int minX = this.x;
      int minY = this.y;
      int maxX = this.x;
      int maxY = this.y;

      for (List<AnimationInterface> commandList : this.getCommands().values()) {
        minX = Math.min(minX,
                commandList.get(0).getShapeStartXPoint());
        minY = Math.min(minY,
                commandList.get(0).getShapeStartYPoint());
        maxX = Math.max(maxX,
                commandList.get(0).getShapeStartXPoint() + commandList.get(0).getShapeStartW());
        maxY = Math.max(maxY,
                commandList.get(0).getShapeStartYPoint() + commandList.get(0).getShapeStartH());

        for (AnimationInterface command : commandList) {
          minX = Math.min(minX, command.getShapeEndXPoint());
          minY = Math.min(minY, command.getShapeEndYPoint());
          maxX = Math.max(maxX, command.getShapeEndXPoint() + command.getShapeEndW());
          maxY = Math.max(maxY, command.getShapeEndYPoint() + command.getShapeEndH());
        }
      }
      return new Dimension(maxX - minX, maxY - minY);
    }

    @Override
    public void setShapes(Map<String, ShapeGetter> shapes) {
      //Does nothing. It is just a help method that takes in Map of ShapeGetter and return nothing.
    }
  }
}
