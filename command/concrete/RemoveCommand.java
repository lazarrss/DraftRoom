package raf.draft.dsw.controller.command.concrete;

import raf.draft.dsw.controller.command.AbstractCommand;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.jtree.model.DraftTreeItem;
import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;
import raf.draft.dsw.gui.swing.view.painters.RoomElementPainter;
import raf.draft.dsw.model.structures.roomelements.RoomElement;

import java.util.ArrayList;
import java.util.List;

public class RemoveCommand extends AbstractCommand {
    private MyTabPanel roomView;
    private RoomElementPainter child;
    private List<RoomElement> children = new ArrayList<>();
    private List<RoomElementPainter> painters = new ArrayList<>();

    public RemoveCommand(MyTabPanel roomView, List<RoomElementPainter> painters) {
        this.roomView = roomView;
        for(RoomElementPainter list : painters){
            this.painters.add(list);
            children.add(list.getRoomElement());
        }
    }
    public RemoveCommand(MyTabPanel roomView, RoomElementPainter painter){
        this.roomView = roomView;
        this.child = painter;
    }

    @Override
    public void doCommand() {
        for(int i = 0; i<painters.size(); i++) {
            roomView.getPainterList().remove(painters.get(i));
            MainFrame.getInstance().getDraftTree().removeChild(roomView.getRoomItem(), children.get(i));

            if(!roomView.getSelectionList().isEmpty())
                roomView.getSelectionList().remove(painters.get(i));
        }
        if(!painters.isEmpty()) ApplicationFramework.getInstance().getProjectController().setOnChanged(painters.getFirst().getRoomElement());
        if(child != null){
            roomView.getPainterList().remove(child);
            MainFrame.getInstance().getDraftTree().removeChild(roomView.getRoomItem(), child.getRoomElement());
            if(!roomView.getSelectionList().isEmpty())
                roomView.getSelectionList().remove(child);
            ApplicationFramework.getInstance().getProjectController().setOnChanged(child.getRoomElement());
        }
        roomView.repaint();
    }

    @Override
    public void undoCommand() {
        for(int i = 0; i<painters.size(); i++){
            children.get(i).addSubscriber(roomView);
            roomView.getPainterList().add(painters.get(i));
            MainFrame.getInstance().getDraftTree().addChild(roomView.getRoom(), children.get(i));
            //if(!roomView.getSelectionList().isEmpty())
                roomView.getSelectionList().add(painters.get(i));
        }
        if(!painters.isEmpty()) ApplicationFramework.getInstance().getProjectController().setOnChanged(painters.getFirst().getRoomElement());

        if(child != null){
            child.getRoomElement().addSubscriber(roomView);
            roomView.getPainterList().add(child);
            MainFrame.getInstance().getDraftTree().addChild(roomView.getRoom(), child.getRoomElement());
            ApplicationFramework.getInstance().getProjectController().setOnChanged(child.getRoomElement());
        }
        roomView.repaint();
    }
}
