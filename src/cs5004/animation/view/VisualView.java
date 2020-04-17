package cs5004.animation.view;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import java.awt.Dimension;

import cs5004.animation.model.AnimationInterface;
import cs5004.animation.model.IAnimationModel;
import cs5004.animation.model.ShapeGetter;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A class that represents a visual view that displays the animation as a swing animation.
 */
public class VisualView extends JFrame implements IView {

  protected int speed;
  private IAnimationModel animationModel;
  private Timer timer;
  private AAnimationDrawingPanel animationDrawingPanel;

  /**
   * Constructor for GUIAnimation view that initializes the fields, creates a panel on which the
   * animation will be drawn, and adds the scroll bars.
   *
   * @param animationModel the incoming model used for the animation.
   * @param tickPerSec     is the tickRate of the animation that can be adjusted.
   */
  public VisualView(IAnimationModel animationModel, int tickPerSec) {
    super();

    if (animationModel == null) {
      throw new IllegalArgumentException("Cannot be null.");
    }

    if (tickPerSec <= 0) {
      throw new IllegalArgumentException("No non-negative and can only be great "
              + "than or equal to 0");
    }
    this.animationModel = animationModel;
    this.speed = tickPerSec;
    this.animationDrawingPanel = new DrawPanel();
    this.animationDrawingPanel.setPreferredSize(this.getShapeSpaceNeeded());
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JScrollPane sp = new JScrollPane(this.animationDrawingPanel);
    this.add(sp);
    this.setSize(this.animationModel.getWidth(), this.animationModel.getHeight());
    this.timer = new Timer();
  }

  @Override
  public void play() {
    this.setVisible(true);
    this.timer.schedule(new DrawFrameTask(this.animationModel.getFinalTick()), 0, 1000);
  }

  @Override
  public String getText() {
    return null;
  }


  private Dimension getShapeSpaceNeeded() {
    int opWidth = 0;
    int opHeight = 0;
    for (List<AnimationInterface> listCommand : this.animationModel.getCommands().values()) {
      opWidth = Math.max(opWidth, listCommand.get(0).getShapeStartXPoint()
              + listCommand.get(0).getShapeStartW());

      opHeight = Math.max(opHeight,
              listCommand.get(0).getShapeStartYPoint() + listCommand.get(0).getShapeStartH());

      for (AnimationInterface command : listCommand) {
        opWidth = Math.max(opWidth, command.getShapeEndXPoint() + command.getShapeEndW());
        opHeight = Math.max(opHeight, command.getShapeEndYPoint() + command.getShapeEndH());
      }
    }
    return new Dimension(opWidth, opHeight);
  }

  /**
   * A TimerTask that updates this visual view at each tick of the animation.
   */
  private class DrawFrameTask extends TimerTask {

    private int tick = 0;
    private int finalTick;

    private DrawFrameTask(int finalTick) {
      super();
      this.finalTick = finalTick;
    }

    @Override
    public void run() {
      if (this.tick >= this.finalTick) {
        timer.cancel();
      }
      List<ShapeGetter> shapeTick = animationModel.getShapeState(this.tick);
      animationDrawingPanel.draw(shapeTick);
      this.tick++;
    }
  }
}
