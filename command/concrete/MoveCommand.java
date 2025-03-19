package raf.draft.dsw.controller.command.concrete;

import raf.draft.dsw.controller.command.AbstractCommand;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;
import raf.draft.dsw.gui.swing.view.painters.RoomElementPainter;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MoveCommand extends AbstractCommand {
    MyTabPanel roomView;
    List<RoomElementPainter> painters;
    List<Point> points = new ArrayList<>();
    HashMap<RoomElementPainter, Point> mapUndo = new HashMap<>();
    HashMap<RoomElementPainter, Point> mapRedo = new HashMap<>();
    public MoveCommand(MyTabPanel roomView, List<Point> points) {
        this.roomView = roomView;
        this.points.addAll(points);
        for(RoomElementPainter painter : roomView.getSelectionList()){
            mapUndo.put(painter, new Point(painter.getRoomElement().getStartPoint()));
        }
        this.painters = roomView.getSelectionList();
    }

    @Override
    public void doCommand() {
        if(mapRedo.isEmpty()) return;
        for (Map.Entry<RoomElementPainter,Point> entry : mapRedo.entrySet()){
            Point loc = entry.getValue();
            entry.getKey().getRoomElement().setLocation(entry.getValue());
            entry.getValue().setLocation(loc);
            ApplicationFramework.getInstance().getProjectController().setOnChanged(entry.getKey().getRoomElement());
        }
    }

    @Override
    public void undoCommand() {
        for (Map.Entry<RoomElementPainter,Point> entry : mapUndo.entrySet()){
            Point loc = entry.getValue();
            mapRedo.put(entry.getKey(), entry.getKey().getRoomElement().getLocation());
            entry.getKey().getRoomElement().setLocation(entry.getValue());
            entry.getValue().setLocation(loc);
            ApplicationFramework.getInstance().getProjectController().setOnChanged(entry.getKey().getRoomElement());
        }
    }
}
