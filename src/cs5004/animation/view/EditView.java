package cs5004.animation.view;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cs5004.animation.model.ShapeGetter;

/**
 * A constructor that represent a visual view for the animation. The constructor allows user to
 *     edit the animation display, which includes adding shapes. The animation can be either be
 *     played, paused, reset, looped.
 */
public class EditView extends JFrame implements IEditView, ActionListener, ChangeListener {
  private final JTextField tickSpeed;
  private JPanel toolAbilities;
  protected boolean speedSetter = true;
  private final JSlider jSlider;
  private int width = 335;
  private int height = 855;
  private final DrawPanel drawPanel;
  private final List<ActionListener> mouseClickListener;
  private final List<PropertyChangeListener> tickListener;
  private boolean sliderStopper = true;

  /**
   * A method for Edit view. It creates a visual view with all interactive pieces and initializing
   *     all listener. Takes a ticks per second – the starting speed.
   * @param ticksPerSecond the starting speed for an animation.
   */
  public EditView(int ticksPerSecond) {
    super();
    this.tickSpeed = new JTextField("" + ticksPerSecond);
    this.mouseClickListener = new ArrayList<>();
    this.tickListener = new ArrayList<>();
    this.setLayout(new BorderLayout());
    this.drawPanel = new DrawPanel();
    this.drawPanel.setPreferredSize(new Dimension(this.width, this.height));
    JScrollPane scrollPane = new JScrollPane(this.drawPanel);
    jSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
    jSlider.addChangeListener(this);
    jSlider.setPreferredSize(new Dimension(200, 32));
    JPanel display = new JPanel(new BorderLayout());
    display.add(jSlider, BorderLayout.NORTH);
    display.add(scrollPane, BorderLayout.CENTER);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.add(display, BorderLayout.CENTER);
    this.createAbilities();
    this.add(this.toolAbilities, BorderLayout.NORTH);
    this.setSize(this.width + 500, this.height + 36);
  }

  @Override
  public void setWidth(int width) {
    this.width = width;
    this.drawPanel.setPreferredSize(new Dimension(this.width, this.height));
  }


  @Override
  public void setHeight(int height) {
    this.height = height;
    this.drawPanel.setPreferredSize(new Dimension(this.width, this.height));
  }

  @Override
  public void play() {
    this.setVisible(true);
  }

  @Override
  public String getText() {
    return null;
  }


  @Override
  public void addButtonListener(ActionListener actionListener) {
    this.mouseClickListener.add(actionListener);

  }

  @Override
  public void addPropertyListener(PropertyChangeListener listener) {
    this.tickListener.add(listener);

  }

  @Override
  public void setSpeed(double tickSpeed) {
    speedSetter = false;
    jSlider.setValue((int) (tickSpeed * 100));

  }

  private void fireButtonEvent(String type) {
    for (ActionListener listener : this.mouseClickListener) {
      listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, type));
    }
  }

  private void firePropertyChangeEvent(String type, String newValue) {
    for (PropertyChangeListener listener : this.tickListener) {
      listener.propertyChange(new PropertyChangeEvent(this, type,
              newValue, newValue));
    }
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    String command = actionEvent.getActionCommand();

    if (command.equals("tick speed")) {
      this.firePropertyChangeEvent("tick speed", this.tickSpeed.getText());
    } else {
      this.fireButtonEvent(command);
    }
  }

  private void createAbilities() {
    JButton start = new JButton("START");
    start.setActionCommand("start");
    start.addActionListener(this);
    JPanel startButton = new JPanel();
    startButton.add(start);
    JButton pause = new JButton("PAUSE");
    pause.setActionCommand("pause");
    pause.addActionListener(this);
    JPanel pauseButton = new JPanel();
    pauseButton.add(pause);
    JLabel loop = new JLabel("REPEAT");
    JCheckBox loopToggle = new JCheckBox();
    loopToggle.setActionCommand("loop");
    loopToggle.addActionListener(this);
    JPanel loopPanel = new JPanel();
    loopPanel.add(loop);
    loopPanel.add(loopToggle);
    JButton restart = new JButton("RESTART");
    restart.setActionCommand("restart");
    restart.addActionListener(this);
    JLabel speed = new JLabel("TICK SPEED");
    this.tickSpeed.setActionCommand("tick speed");
    this.tickSpeed.addActionListener(this);
    JPanel tickPanel = new JPanel();
    tickPanel.add(speed);
    tickPanel.add(this.tickSpeed);
    JLabel desc = new JLabel("Use the scroller below to adjust speed.");
    JPanel labelPanel = new JPanel();
    labelPanel.add(desc);
    this.toolAbilities = new JPanel(new FlowLayout(FlowLayout.CENTER, 3, 3));
    this.toolAbilities.add(startButton);
    this.toolAbilities.add(pauseButton);
    this.toolAbilities.add(restart);
    this.toolAbilities.add(loopPanel);
    this.toolAbilities.add(tickPanel);
    this.toolAbilities.add(labelPanel);
  }

  @Override
  public void display(List<ShapeGetter> shapes) {
    this.drawPanel.draw(shapes);
  }

  @Override
  public void displayError(String s) {
    JOptionPane.showMessageDialog(this, s, "An error occurred",
            JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void setSlider(double tick) {
    sliderStopper = false;
    jSlider.setValue((int) (tick * 100));
  }

  @Override
  public void stateChanged(ChangeEvent changeEvent) {
    if (sliderStopper) {
      this.firePropertyChangeEvent("speedChanger", "" + jSlider.getValue());
    } else {
      sliderStopper = true;
    }
  }
}
