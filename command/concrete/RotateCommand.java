package raf.draft.dsw.controller.command.concrete;

import raf.draft.dsw.controller.command.AbstractCommand;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;
import raf.draft.dsw.gui.swing.view.painters.RoomElementPainter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.List;

public class RotateCommand extends AbstractCommand {
    private MyTabPanel roomView;
    private List<RoomElementPainter> painters = new ArrayList<>();
    private boolean rotation; // left = false, right = true;
    public RotateCommand(MyTabPanel roomView, List<RoomElementPainter> painters, boolean rotation) {
        this.roomView = roomView;
        this.rotation = rotation;
        this.painters.addAll(painters);
    }

    @Override
    public void doCommand() {
        if(!painters.isEmpty()) {
            for (RoomElementPainter painter : painters) {
                int rotation = painter.getRoomElement().getRotationRatio();
                if(this.rotation)
                    rotation++;
                else rotation--;
                painter.getRoomElement().setRotationRatio(rotation);
            }
        }
        ApplicationFramework.getInstance().getProjectController().setOnChanged(painters.getFirst().getRoomElement());
    }

    @Override
    public void undoCommand() {
        if(!painters.isEmpty()) {
            for (RoomElementPainter painter : painters) {
                int rotation = painter.getRoomElement().getRotationRatio();
                if(this.rotation)
                    rotation--;
                else rotation++;
                painter.getRoomElement().setRotationRatio(rotation);
            }
        }
        ApplicationFramework.getInstance().getProjectController().setOnChanged(painters.getFirst().getRoomElement());
    }

}
