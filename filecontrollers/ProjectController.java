package raf.draft.dsw.controller.filecontrollers;

import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.jtree.model.DraftTreeItem;
import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;
import raf.draft.dsw.gui.swing.view.painters.RoomElementPainterFactory;
import raf.draft.dsw.gui.swing.view.painters.RoomPainter;
import raf.draft.dsw.model.messages.Message;
import raf.draft.dsw.model.messages.MessageType;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.DraftNodeComposite;
import raf.draft.dsw.model.structures.Building;
import raf.draft.dsw.model.structures.Project;
import raf.draft.dsw.model.structures.ProjectExplorer;
import raf.draft.dsw.model.structures.Room;
import raf.draft.dsw.model.structures.roomelements.RoomElement;
import raf.draft.dsw.serializer.Serializer;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDateTime;

public class ProjectController {
    private Serializer serializer;
    public ProjectController(Serializer serializer){
        this.serializer = serializer;
    }
    public void save(Project project){
        if(!project.isOnChanged()) {
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("No changes to save", MessageType.INFO);
            return;
        }
        if(project.getPathToFile().isEmpty()){
            saveAs(project);
        }else{
            try {
                serializer.serialize(project);
                project.setOnChanged(false);
                ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Project saved", MessageType.INFO);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

    }
    public void saveAs(Project project){
        JFileChooser jFileChooser = new JFileChooser();
        if(jFileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
            String path = jFileChooser.getSelectedFile().getPath();
            project.setPathToFile(path);
            save(project);
        }
    }
    public Project openProject(){
        JFileChooser jFileChooser = new JFileChooser();
        if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            String path = jFileChooser.getSelectedFile().getPath();
            try {
                Project curr = serializer.deserialize(path);
                curr.setPathToFile(path);
                return curr;
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
        return null;
    }

    public void setOnChanged(DraftNode node){
        if(node instanceof ProjectExplorer) return;
        Room room = null;
        if(node instanceof RoomElement)
            room = (Room) node.getParent();
        if(node instanceof Room)
            room = (Room) node;
        if(room != null){
            if(room.getParent() instanceof Project){
                ((Project)room.getParent()).setOnChanged(true);
            }else if(room.getParent() instanceof Building){
                ((Project)room.getParent().getParent()).setOnChanged(true);
            }
        }
        if(node instanceof Building){
            ((Project)node.getParent()).setOnChanged(true);
        }else if(node instanceof Project) {
            ((Project) node).setOnChanged(true);
        }
    }

    public void connect(DraftTreeItem project, DraftTreeItem parent){
        for(DraftNode child : ((Project)project.getDraftNode()).getChildren()){
            child.setParent(project.getDraftNode());
            DraftTreeItem draftTreeItem = new DraftTreeItem(child);
            project.addSubscriber(((MyTabPanel) MainFrame.getInstance().getTabbedPane().getSelectedComponent()));
            project.add(draftTreeItem);
            if(child instanceof Room) {
                connect(draftTreeItem);
            }
            if(child instanceof Building){
                for(DraftNode room : ((Building) child).getChildren()){
                    room.setParent(child);
                    DraftTreeItem roomItem = new DraftTreeItem(room);
                    draftTreeItem.add(roomItem);
                    connect(roomItem);
                }
            }
        }
    }
    public void connect(DraftTreeItem room){
        MyTabPanel curr = ((MyTabPanel) MainFrame.getInstance().getTabbedPane().getSelectedComponent());

        if(room.getDraftNode().getParent() instanceof Building)
            ((Building)room.getDraftNode().getParent()).addSubscriber(curr);
        else ((Project)room.getDraftNode().getParent()).addSubscriber(curr);
        ((Room)room.getDraftNode()).addSubscriber(curr);

        for(DraftNode child : ((Room)room.getDraftNode()).getChildren()){
            child.setParent(room.getDraftNode());
            DraftTreeItem draftTreeItem = new DraftTreeItem(child);
            room.add(draftTreeItem);
            ((RoomElement)child).addSubscriber(curr);
            curr.getPainterList().add(RoomElementPainterFactory.getPainter((RoomElement) child));
        }
        curr.setRoom((Room) room.getDraftNode());
        curr.setRoomItem(room);
        curr.setRoomPainter(new RoomPainter((Room) room.getDraftNode()));

        double scalingFactor = getscale(curr);
        curr.setScalingFactor(scalingFactor);
        curr.repaint();
    }

    public static double getscale(MyTabPanel roomView) {
        double panelWidth = roomView.getWidth() - 20; //100
        double panelHeight = roomView.getHeight() - 30; //100
        double roomHeight = (roomView.getRoom().getRoomHeight()); //50
        double roomWidth = (roomView.getRoom().getRoomWidth()); //50

        double odnosW = panelWidth / roomWidth; // 418 / 2000
        double odnosH = panelHeight / roomHeight; // 291 / 22000

        return Math.min(odnosW, odnosH);
    }
}
