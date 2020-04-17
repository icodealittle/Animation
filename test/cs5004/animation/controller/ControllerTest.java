package cs5004.animation.controller;

import org.junit.Before;
import org.junit.Test;

import java.awt.event.ActionEvent;


import javax.swing.JButton;
import javax.swing.JCheckBox;

import cs5004.animation.model.AModel;
import cs5004.animation.model.IBuilder;
import cs5004.animation.view.EditView;
import cs5004.animation.view.IEditView;

import static org.junit.Assert.assertEquals;

/**
 * Junit to test and check if the Controller constructor works properly.
 */
public class ControllerTest {
  JButton startButton;
  JButton pauseButton;
  JButton restartButton;
  JCheckBox loopCheckbox;

  Controller controller;
  IEditView view;
  IBuilder builder;

  @Before
  public void setUp() {
    this.startButton = new JButton("start");
    this.pauseButton = new JButton("pause");
    this.restartButton = new JButton("restart");
    this.loopCheckbox = new JCheckBox("loop");
    this.builder = new AModel.AnimationModelBuilder();
    this.view = new EditView(20);
    this.controller = new Controller(builder, view, 20);
  }

  @Test
  public void testCheckActionPerformed() {
    ActionEvent aE = new ActionEvent(startButton, 1, "start");
    ActionEvent aE1 = new ActionEvent(pauseButton, 2, "pause");
    ActionEvent aE2 = new ActionEvent(restartButton, 3, "restart");
    ActionEvent ae3 = new ActionEvent(loopCheckbox, 4, "loop");
    this.controller.start();
    assertEquals(0, this.controller.getStartingTick());
    this.controller.actionPerformed(aE);
    assertEquals(0, this.controller.getStartingTick());
    this.controller.actionPerformed(aE1);
    this.controller.actionPerformed(aE2);
    this.controller.actionPerformed(ae3);
  }

  @Test
  public void testing() {
    builder = new AModel.AnimationModelBuilder();
    MockView view = new MockView();
    Controller controller = new Controller(builder, view, 20);
    controller.start();
    assertEquals("", view.log.toString());
  }
}