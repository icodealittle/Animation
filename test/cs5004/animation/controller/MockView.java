package cs5004.animation.controller;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cs5004.animation.model.ShapeGetter;
import cs5004.animation.view.IEditView;
import cs5004.animation.view.IView;

/**
 * This is Mock class to test the EditView.
 */
public class MockView implements IEditView {

  public Appendable log = new StringBuilder();
  List<IView> listeners = new ArrayList<>();

  private void addToLog(String txt) {
    try {
      log.append(txt).append("\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void setWidth(int width) {
    this.addToLog("SET WIDTH");
  }

  @Override
  public void setHeight(int height) {
    this.addToLog("SET HEIGHT");
  }

  @Override
  public void addButtonListener(ActionListener actionListener) {
    this.addToLog("ADD BUTTON LISTENER");
    this.listeners.add((IView) actionListener);
  }

  @Override
  public void setSpeed(double tickSpeed) {
    this.addToLog("SET SPEED");
  }

  @Override
  public void display(List<ShapeGetter> shapes) {
    this.addToLog("DISPLAY");
  }

  @Override
  public void setSlider(double tick) {
    this.addToLog("SET SLIDER");
  }

  @Override
  public void displayError(String s) {
    this.addToLog("SHOW ERRORR");
  }

  @Override
  public void addPropertyListener(PropertyChangeListener listener) {
    this.addToLog("ADD PROPERTY LISTENER");
  }

  @Override
  public void play() {
    this.addToLog("PLAY");
  }

  @Override
  public String getText() {
    this.addToLog("GET TEXT");
    return "";
  }
}
