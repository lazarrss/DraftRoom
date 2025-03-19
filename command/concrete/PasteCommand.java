package raf.draft.dsw.controller.command.concrete;

import raf.draft.dsw.controller.command.AbstractCommand;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;
import raf.draft.dsw.gui.swing.view.painters.RoomElementPainter;
import raf.draft.dsw.gui.swing.view.painters.RoomElementPainterFactory;
import raf.draft.dsw.model.structures.roomelements.RoomElement;

import java.awt.*;
import java.util.*;
import java.util.List;


public class PasteCommand extends AbstractCommand {
    private MyTabPanel roomView;
    private IdentityHashMap<RoomElementPainter, Boolean> painterSelected = new IdentityHashMap<>();
    public PasteCommand(MyTabPanel roomView){
        this.roomView = roomView;
        double scale = roomView.getScalingFactor();
        for (RoomElement roomElement : roomView.getRoom().getCopyList()) {
            RoomElement newElement = roomElement.clone();
            String name = newElement.getName() + "copy";
            newElement.mySetName(name);
            int offsetX = 15, offsetY = 15;

            if (roomElement.getLocation().x + roomElement.getWidth() * scale + offsetX >= roomView.getRoom().getRoomWidth() * scale) {
                newElement.setLocation(new Point(roomElement.getLocation().x - offsetX, roomElement.getLocation().y));
            } else if (roomElement.getLocation().y + roomElement.getHeight() * scale + offsetY >= roomView.getRoom().getRoomHeight() * scale) {
                newElement.setLocation(new Point(roomElement.getLocation().x, roomElement.getLocation().y - offsetY));
            } else {
                newElement.setLocation(new Point(roomElement.getLocation().x + offsetX, roomElement.getLocation().y + offsetY));
            }

            RoomElementPainter painter = RoomElementPainterFactory.getPainter(newElement);
            painterSelected.put(painter, false);
        }
        roomView.getRoom().getCopyList().clear();
    }
    @Override
    public void doCommand() {
        for(Map.Entry<RoomElementPainter, Boolean> entry : painterSelected.entrySet()){
            roomView.getPainterList().add(entry.getKey());
            if(entry.getValue())
                roomView.getSelectionList().add(entry.getKey());
            MainFrame.getInstance().getDraftTree().addChild(roomView.getRoom(), entry.getKey().getRoomElement());

            ApplicationFramework.getInstance().getProjectController().setOnChanged(entry.getKey().getRoomElement());
        }
    }

    @Override
    public void undoCommand() {
        for(Map.Entry<RoomElementPainter, Boolean> entry : painterSelected.entrySet()){
            roomView.getPainterList().remove(entry.getKey());
            roomView.getSelectionList().remove(entry.getKey());
            MainFrame.getInstance().getDraftTree().removeChild(roomView.getRoomItem(), entry.getKey().getRoomElement());
            ApplicationFramework.getInstance().getProjectController().setOnChanged(entry.getKey().getRoomElement());
        }
        roomView.repaint();
    }
}
