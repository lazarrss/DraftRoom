package raf.draft.dsw.gui.swing.jtree;

import raf.draft.dsw.controller.observer.IPublisher;
import raf.draft.dsw.controller.observer.ISubscriber;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.jtree.controller.OpenTab;
import raf.draft.dsw.gui.swing.jtree.model.DraftTreeItem;
import raf.draft.dsw.gui.swing.jtree.view.DraftTreeView;
import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;
import raf.draft.dsw.gui.swing.view.painters.RoomElementPainter;
import raf.draft.dsw.gui.swing.view.painters.concrete.BojlerPainter;
import raf.draft.dsw.model.messages.Message;
import raf.draft.dsw.model.messages.MessageType;
import raf.draft.dsw.model.nodefactory.DraftNodeFactory;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.DraftNodeComposite;
import raf.draft.dsw.model.structures.Building;
import raf.draft.dsw.model.structures.Project;
import raf.draft.dsw.model.structures.ProjectExplorer;
import raf.draft.dsw.model.structures.Room;
import raf.draft.dsw.model.structures.roomelements.RoomElement;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static raf.draft.dsw.controller.filecontrollers.ProjectController.getscale;

public class DraftTreeImplementation implements DraftTree{
    private DraftTreeView draftTreeView;
    private DefaultTreeModel defaultTreeModel;
    private final DraftNodeFactory draftNodeFactory = new DraftNodeFactory();

    @Override
    public DraftTreeView generateTree(ProjectExplorer projectExplorer) {
        DraftTreeItem root = new DraftTreeItem(projectExplorer);
        defaultTreeModel = new DefaultTreeModel(root);
        draftTreeView = new DraftTreeView(defaultTreeModel);
        return draftTreeView;
    }

    @Override
    public void addChild(DraftTreeItem parent, String[] attributes, String type) {
        if (!(parent.getDraftNode() instanceof DraftNodeComposite))
            return;

        DraftNode child = createChild(parent.getDraftNode(), attributes, type);
        DraftTreeItem childItem = new DraftTreeItem(child);
        parent.add(childItem);
        ((DraftNodeComposite) parent.getDraftNode()).addChild(child);
        draftTreeView.expandPath(draftTreeView.getSelectionPath());
        SwingUtilities.updateComponentTreeUI(draftTreeView);
    }

    public void addChild(DraftTreeItem parent, Project project){
        if(!(parent.getDraftNode() instanceof ProjectExplorer)) return;

        DraftTreeItem draftTreeItem = new DraftTreeItem(project);
        parent.add(draftTreeItem);

        ((ProjectExplorer) parent.getDraftNode()).addChild(project);

        ApplicationFramework.getInstance().getProjectController().connect(draftTreeItem, parent);
        project.addSubscriber(MainFrame.getInstance());

        draftTreeView.expandPath(draftTreeView.getSelectionPath());
        SwingUtilities.updateComponentTreeUI(draftTreeView);
    }
    public void addChild(DraftTreeItem parent, Room room, MyTabPanel roomView){
        if(!(parent.getDraftNode() instanceof Building || parent.getDraftNode() instanceof Project)) return;

        DraftTreeItem draftTreeItem = new DraftTreeItem(room);
        parent.add(draftTreeItem);

        MainFrame.getInstance().getDraftTree().removeChild(roomView.getRoomItem());
        roomView.getRoom().removeFromTree();

        ((DraftNodeComposite) parent.getDraftNode()).addChild(room);
        room.setParent(parent.getDraftNode());

        ApplicationFramework.getInstance().getRoomController().connect(draftTreeItem, parent);

        roomView = (MyTabPanel) MainFrame.getInstance().getTabbedPane().getSelectedComponent();

        if(parent.getDraftNode() instanceof Building){
            ((Project)parent.getDraftNode().getParent()).addSubscriber(roomView);
            ((Building) parent.getDraftNode()).addSubscriber(roomView);
            roomView.getHeader().getTitleLabel().setForeground(((Building) parent.getDraftNode()).getColor());
        }else ((Project) parent.getDraftNode()).addSubscriber(roomView);

        draftTreeView.expandPath(draftTreeView.getSelectionPath());
        SwingUtilities.updateComponentTreeUI(draftTreeView);
    }

    @Override
    public void addChild(Room room, RoomElement roomElement) {
        room.addChild(roomElement);

        MyTabPanel roomPanel = (MyTabPanel) MainFrame.getInstance().getTabbedPane().getSelectedComponent();

        DraftTreeItem draftTreeItem = new DraftTreeItem(roomElement);

        roomPanel.getRoomItem().add(draftTreeItem);
        roomPanel.getRoomElements().add(draftTreeItem);

        draftTreeView.expandPath(draftTreeView.getSelectionPath());
        SwingUtilities.updateComponentTreeUI(draftTreeView);
    }

    @Override
    public DraftTreeItem getSelectedNode() {
        return (DraftTreeItem) draftTreeView.getLastSelectedPathComponent();
    }

    @Override
    public void removeChild(DraftTreeItem child) {
        DraftTreeItem parentItem = (DraftTreeItem) child.getParent();
        DraftNodeComposite parentNode = (DraftNodeComposite) parentItem.getDraftNode();
        parentNode.removeChild(child.getDraftNode());
        parentItem.remove(child);
        SwingUtilities.updateComponentTreeUI(draftTreeView);
    }

    @Override
    public void removeChild(DraftTreeItem roomItem, RoomElement roomElement) {
        MyTabPanel roomPanel = (MyTabPanel) MainFrame.getInstance().getTabbedPane().getSelectedComponent();
        Room roomNode = (Room) roomItem.getDraftNode();
        roomNode.removeChild(roomElement);

        Iterator<DraftTreeItem> it = roomPanel.getRoomElements().iterator();
        while (it.hasNext()){
            DraftTreeItem draftTreeItem = it.next();
            if(draftTreeItem.getDraftNode().equals(roomElement)){
                roomItem.remove(draftTreeItem);
                it.remove();
            }
        }

        SwingUtilities.updateComponentTreeUI(draftTreeView);
    }

    private DraftNode createChild(DraftNode parent, String[] attributes, String type) {

        return draftNodeFactory.createDraftNode(type, parent, attributes);
    }

    public DraftNodeFactory getDraftNodeFactory() {
        return draftNodeFactory;
    }


    public DraftTreeView getDraftTreeView() {
        return draftTreeView;
    }

    public DefaultTreeModel getDefaultTreeModel() {
        return defaultTreeModel;
    }
}
