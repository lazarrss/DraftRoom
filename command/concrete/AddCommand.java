package raf.draft.dsw.controller.command.concrete;

import raf.draft.dsw.controller.command.AbstractCommand;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.jtree.model.DraftTreeItem;
import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;
import raf.draft.dsw.gui.swing.view.painters.RoomElementPainter;
import raf.draft.dsw.gui.swing.view.painters.RoomElementPainterFactory;
import raf.draft.dsw.model.structures.roomelements.RoomElement;

import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

public class AddCommand extends AbstractCommand {
    private MyTabPanel roomView;
    private RoomElement child;
    private TreeMap<RoomElementPainter, Boolean> painterSelected = new TreeMap<>(Comparator.comparingInt(a -> a.getRoomElement().getRotationRatio()));
    private RoomElementPainter painter;
    public AddCommand(MyTabPanel roomView, RoomElementPainter painter){
        this.roomView = roomView;
        this.painter = painter;
        if(roomView.getSelectionList().contains(painter))
            painterSelected.put(painter, true);
        else painterSelected.put(painter, false);
        this.child = painter.getRoomElement();

    }
    @Override
    public void doCommand() {
        child.addSubscriber(roomView);
        roomView.getPainterList().add(painter);
        if(painterSelected.get(painter))
            roomView.getSelectionList().add(painter);
        MainFrame.getInstance().getDraftTree().addChild(roomView.getRoom(), child);
        ApplicationFramework.getInstance().getProjectController().setOnChanged(child);
        roomView.repaint();
    }

    @Override
    public void undoCommand() {
        roomView.getPainterList().remove(painter);
        roomView.getSelectionList().remove(painter);
        MainFrame.getInstance().getDraftTree().removeChild(roomView.getRoomItem(), child);
        ApplicationFramework.getInstance().getProjectController().setOnChanged(child);
        roomView.repaint();
    }
}
