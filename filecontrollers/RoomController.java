package raf.draft.dsw.controller.filecontrollers;

import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.jtree.model.DraftTreeItem;
import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;
import raf.draft.dsw.gui.swing.view.painters.RoomElementPainterFactory;
import raf.draft.dsw.gui.swing.view.painters.RoomPainter;
import raf.draft.dsw.model.messages.MessageType;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.structures.Building;
import raf.draft.dsw.model.structures.Project;
import raf.draft.dsw.model.structures.Room;
import raf.draft.dsw.model.structures.roomelements.RoomElement;
import raf.draft.dsw.serializer.Serializer;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

import static raf.draft.dsw.controller.filecontrollers.ProjectController.getscale;

public class RoomController {
    private Serializer serializer;
    public static String dest = "src/main/resources/templates";
    public RoomController(Serializer serializer){
        this.serializer = serializer;
        File file = new File(dest);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void savePattern(Room room){
        try {
            serializer.saveTemplate(room);
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Success", MessageType.INFO);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public Room loadPattern(){
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File(dest));
        if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            String path = jFileChooser.getSelectedFile().getPath();
            try {
                return serializer.loadTemplate(path);
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
        return null;
    }

    public void connect(DraftTreeItem dtf, DraftTreeItem parent) {
        MyTabPanel curr = ((MyTabPanel) MainFrame.getInstance().getTabbedPane().getSelectedComponent());

        ((Room)dtf.getDraftNode()).addSubscriber(curr);
        for(DraftNode child : ((Room)dtf.getDraftNode()).getChildren()){
            child.setParent(dtf.getDraftNode());
            DraftTreeItem draftTreeItem = new DraftTreeItem(child);
            dtf.add(draftTreeItem);
            ((RoomElement)child).addSubscriber(curr);
            curr.getPainterList().add(RoomElementPainterFactory.getPainter((RoomElement) child));
        }
//        curr.setRoom((Room) dtf.getDraftNode());
//        curr.setRoomItem(dtf);
        curr.setRoomPainter(new RoomPainter((Room) dtf.getDraftNode()));

        double scalingFactor = getscale(curr);
        curr.setScalingFactor(scalingFactor);
        curr.repaint();
    }
}
