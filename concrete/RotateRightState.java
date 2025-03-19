package raf.draft.dsw.controller.state.concrete;

import raf.draft.dsw.controller.command.AbstractCommand;
import raf.draft.dsw.controller.command.concrete.RotateCommand;
import raf.draft.dsw.controller.state.State;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;
import raf.draft.dsw.gui.swing.view.painters.RoomElementPainter;
import raf.draft.dsw.gui.swing.view.painters.RoomElementPainterFactory;
import raf.draft.dsw.model.messages.MessageType;
import raf.draft.dsw.model.structures.roomelements.RoomElement;
import raf.draft.dsw.utils.GeometryUtils;

import java.awt.*;

public class RotateRightState implements State {
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
    public void misKliknut(MyTabPanel roomView, Point point) {
    }
    @Override
    public void triggerOnAction(MyTabPanel roomView, Point point) {
        if(roomView.getSelectionList().isEmpty())
            return;
        AbstractCommand abstractCommand = new RotateCommand(roomView, roomView.getSelectionList(), true);
        roomView.getCommandManager().addCommand(abstractCommand);
    }
    @Override
    public void misSkrolGore(MyTabPanel roomView, Point point) {

    }

    @Override
    public void misSkrolDole(MyTabPanel roomView, Point point) {

    }
}
