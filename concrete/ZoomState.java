package raf.draft.dsw.controller.state.concrete;

import raf.draft.dsw.controller.state.State;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;

import java.awt.*;

public class ZoomState implements State {
    @Override
    public void misKliknut(MyTabPanel roomView, Point point) {
        triggerOnAction(roomView, point);
    }
    @Override
    public void misPritisnut(MyTabPanel roomView, Point point) {

    }

    @Override
    public void misPusten(MyTabPanel roomView, Point point) {

    }

    @Override
    public void misUsao(MyTabPanel roomView, Point point) {

    }

    @Override
    public void misIzasao(MyTabPanel roomView, Point point) {

    }

    @Override
    public void misVuce(MyTabPanel roomView, Point point) {

    }

    @Override
    public void misPomeren(MyTabPanel roomView, Point point) {

    }

    @Override
    public void triggerOnAction(MyTabPanel roomView, Point point) {

    }
    @Override
    public void misSkrolGore(MyTabPanel roomView, Point point) {
        double zoom = roomView.getZoomFactor();
        zoom *= 1.05;
        if(zoom >= 3)
            return;
        roomView.setZoomFactor(zoom);
        roomView.setZoomPoint(point);

        roomView.repaint();
    }

    public void misSkrolDole(MyTabPanel roomView, Point point) {
        double zoom = roomView.getZoomFactor();
        zoom *= 0.95;
        if(zoom <= 0.01)
            return;
        roomView.setZoomFactor(zoom);
        roomView.setZoomPoint(point);

        roomView.repaint();

    }
}
