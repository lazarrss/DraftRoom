package raf.draft.dsw.controller.state;

import raf.draft.dsw.gui.swing.view.my.MyTabPanel;

import java.awt.*;
import java.awt.geom.NoninvertibleTransformException;

public interface State {
    void misKliknut(MyTabPanel roomView, Point point) throws NoninvertibleTransformException, InterruptedException; // mouse clicked
    void misPritisnut(MyTabPanel roomView, Point point) throws NoninvertibleTransformException; // mouse pressed
    void misPusten(MyTabPanel roomView, Point point) throws NoninvertibleTransformException; // mouse released
    void misUsao(MyTabPanel roomView, Point point); // mouse entered
    void misIzasao(MyTabPanel roomView, Point point); // mouse exited
    void misVuce(MyTabPanel roomView, Point point) throws NoninvertibleTransformException; // mouse dragged
    void misPomeren(MyTabPanel roomView, Point point); // mouse moved
    void misSkrolGore(MyTabPanel roomView, Point point);
    void misSkrolDole(MyTabPanel roomView, Point point);
    void triggerOnAction(MyTabPanel roomView, Point point);
}
