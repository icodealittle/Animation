package cs5004.animation;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;

import javax.swing.JOptionPane;

import cs5004.animation.controller.Controller;
import cs5004.animation.controller.IController;
import cs5004.animation.model.AModel;
import cs5004.animation.model.AnimationBuilder;
import cs5004.animation.model.AnimationReader;
import cs5004.animation.model.IAnimationModel;
import cs5004.animation.model.IBuilder;
import cs5004.animation.view.EditView;
import cs5004.animation.view.IEditView;
import cs5004.animation.view.IView;
import cs5004.animation.view.SVGView;
import cs5004.animation.view.TextualView;
import cs5004.animation.view.VisualView;

/**
 * This is a class for EasyAnimator that will run a command argument.
 */
public class EasyAnimator {

  /**
   * The main function that initializes the model and the view based on the given arguments and
   * runs an animation.
   * @param args the input that allows the user to specify what kind of animation they desire.
   */
  public static void main(String[] args) {
    AnimationBuilder builderModel = new AModel.AnimationModelBuilder();
    IView textualView;
    IAnimationModel animationModel;
    Readable in = new StringReader("");
    int ticks = 1;
    String viewType = "";
    Appendable out = System.out;
    FileWriter writer = null;
    IBuilder builder = new AModel.AnimationModelBuilder();
    IEditView editView;
    IController controller;

    if (!(Arrays.stream(args).anyMatch("-in"::equals)
            && (Arrays.stream(args).anyMatch("-view"::equals)))) {
      popUpError("Cannot run the program");
    }

    for (int i = 0; i < args.length; i++) {

      if (args[i].equals("-in")) {
        try {
          in = new FileReader(args[i + 1]);
        } catch (FileNotFoundException e) {
          popUpError("File does not exist");
        } catch (IndexOutOfBoundsException e) {
          popUpError("No file specified");
        }
      }

      if (args[i].equals("-view")) {
        try {
          viewType = args[i + 1];
        } catch (IndexOutOfBoundsException e) {
          popUpError("No view specified");
        }
      }

      if (args[i].equals("-speed")) {
        try {
          int newSpeed = Integer.parseInt(args[i + 1]);
          if (newSpeed > 0) {
            ticks = newSpeed;
          } else {
            popUpError("Negative speed");
          }
        } catch (NumberFormatException e) {
          popUpError("No valid speed specified");
        } catch (IndexOutOfBoundsException e) {
          popUpError("No speed specified");
        }
      }

      if (args[i].equals("-out")) {
        try {
          writer = new FileWriter(args[i + 1]);
        } catch (IOException e) {
          popUpError("File does not exist");
        } catch (IndexOutOfBoundsException e) {
          popUpError("No file specified");
        }
      }
    }

    if (viewType.equals("playback")) {
      AnimationReader.parseFile(in, builder);
      editView = new EditView(ticks);
      controller = new Controller(builder, editView, ticks);
      controller.play();
      return;
    }

    animationModel = AnimationReader.parseFile(in, builderModel);

    switch (viewType) {
      case "visual":
        IView view = new VisualView(animationModel, ticks);
        view.play();
        return;
      case "text":
        textualView = new TextualView(animationModel);
        break;
      case "svg":
        textualView = new SVGView(animationModel, ticks);
        break;
      default:
        popUpError("Must have a valid view");
        return;
    }

    textualView.play();

    try {
      if (writer != null) {
        writer.append(textualView.getText());
        writer.close();
      }
      else {
        out.append(textualView.getText());
      }
    }
    catch (IOException e) {
      popUpError("Error on output");
    }
  }

  private static void popUpError(String message) {
    JOptionPane.showMessageDialog(null, message, "Error on Animation", 0);
    System.exit(1);
  }
}
