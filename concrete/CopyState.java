package raf.draft.dsw.controller.state.concrete;

import raf.draft.dsw.controller.state.State;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;
import raf.draft.dsw.gui.swing.view.painters.RoomElementPainter;

import java.awt.*;

public class CopyState implements State {
    @Override
    public void misKliknut(MyTabPanel roomView, Point point) {

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
        roomView.getRoom().getCopyList().clear();
        for(RoomElementPainter painter : roomView.getSelectionList()){
            roomView.getRoom().getCopyList().add(painter.getRoomElement());
        }
    }
    @Override
    public void misSkrolGore(MyTabPanel roomView, Point point) {

    }

    @Override
    public void misSkrolDole(MyTabPanel roomView, Point point) {

    }
}
