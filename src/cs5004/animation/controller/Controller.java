package cs5004.animation.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cs5004.animation.model.AnimationInterface;
import cs5004.animation.model.IAnimationModel;
import cs5004.animation.model.IBuilder;
import cs5004.animation.model.ShapeGetter;
import cs5004.animation.view.IEditView;

/**
 * A class that represent a controller that implement IController, ActionListener,
 * PropertyChangeListener to allow user to edit the animation view.
 */
public class Controller implements IController, ActionListener, PropertyChangeListener {

  private final IEditView view;
  private Timer timer;
  private int tickSeconds;
  private int startingTick;
  private final int finalTick;
  private int currentTick;
  private boolean looping = false;
  private boolean paused = true;
  private final IBuilder builder;

  /**
   * A constructor for the controller, which takes in a builder (Animation), a view, and an
   * animation tick time – speed per second.
   *
   * @param builder     an editable interface for an animation model.
   * @param view        an editable interface for the view – it changes the animation according to
   *                    the user preference.
   * @param tickSeconds the animation speed should be initiate.
   */
  public Controller(IBuilder builder, IEditView view, int tickSeconds) {
    this.builder = builder;
    this.view = view;
    this.tickSeconds = tickSeconds;
    this.startingTick = this.getStartingTick();
    this.finalTick = builder.build().getFinalTick();
    this.currentTick = this.startingTick;
  }

  @Override
  public void propertyChange(PropertyChangeEvent event) {
    switch (event.getPropertyName()) {
      case "tick speed":
        int speed;
        try {
          speed = Integer.parseInt(event.getNewValue().toString());
        } catch (NumberFormatException e) {
          view.displayError("Tick speed is not a number");
          return;
        }
        if (Integer.parseInt(event.getNewValue().toString()) < 1) {
          view.displayError("Tick speed cant be a negative number");
          return;
        }

        tickSeconds = speed;
        if (!paused) {
          this.timer.cancel();
          this.timer = new Timer();
          this.timer.schedule(new DrawFrameTask(this.builder.build()), 0,
                  1000 / this.tickSeconds);
        }
        return;
      case "speedChanger":
        currentTick = (int) ((Double.parseDouble(event.getNewValue().toString()) / 100.0)
                * (double) (finalTick - this.startingTick)) + startingTick;
        if (!paused) {
          paused = true;
        }
        view.display(builder.build().getShapeState(currentTick));
        return;
      default:
    }
  }

  @Override
  public int getStartingTick() {
    Map<String, List<AnimationInterface>> commands = this.builder.getCommands();
    int startingTick = Integer.MAX_VALUE;

    for (String id : commands.keySet()) {
      if (!commands.get(id).isEmpty()) {
        startingTick = Math.min(startingTick, commands.get(id).get(0).getShapeStartTime());
      }
    }
    if (startingTick == Integer.MAX_VALUE) {
      return 0;
    }
    return startingTick;
  }

  @Override
  public void start() {
    IAnimationModel model = this.builder.build();
    if (this.paused && this.currentTick < model.getFinalTick()) {
      this.startingTick = this.getStartingTick();
      this.timer = new Timer();
      this.timer.schedule(new DrawFrameTask(model), 0, 1000 / this.tickSeconds);
      paused = false;
    }
  }

  @Override
  public void play() {
    this.view.addButtonListener(this);
    this.view.addPropertyListener(this);
    this.view.setWidth((int) this.builder.getNeededSpace().getWidth());
    this.view.setHeight((int) this.builder.getNeededSpace().getHeight());
    this.view.play();
    this.view.display(this.builder.build().getShapeState(this.startingTick));
  }

  /**
   * A method that invoked when various buttons are pressed from the view, allowing the user to
   * play, pause, loop, and restart the current animation.
   *
   * @param actionEvent the event that was create by and sent by the view.
   */
  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    switch (actionEvent.getActionCommand()) {
      case "start":
        this.start();
        return;
      case "pause":
        if (!paused) {
          paused = true;
        }
        return;
      case "loop":
        looping = !looping;
        return;
      case "restart":
        currentTick = startingTick;
        start();
        break;
      default:
    }
  }

  /**
   * A class that represent draw task, which update the visual view of each tick.
   */
  private class DrawFrameTask extends TimerTask {
    private final int finalTick;
    private final IAnimationModel model;


    private DrawFrameTask(IAnimationModel model) {
      super();
      this.finalTick = model.getFinalTick();
      this.model = model;
    }

    /**
     * A method that take the action to be performed by this timer task, changing the states of each
     * shape based on its key frames and calls the display method on the view. It also checks if the
     * isLooping field and the isPaused field are true and either loop the animation or just end it
     * after the final tick.
     */
    @Override
    public void run() {
      if (paused) {
        timer.cancel();
      } else if (currentTick >= this.finalTick) {
        if (looping) {
          currentTick = startingTick;
        } else {
          timer.cancel();
          paused = true;
        }
      }
      List<ShapeGetter> shapesAtTick = this.model.getShapeState(currentTick);
      view.display(shapesAtTick);
      view.setSlider((double) currentTick / (double) (finalTick - startingTick));
      currentTick = currentTick + 1;
    }
  }
}
