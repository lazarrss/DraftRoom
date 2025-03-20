package raf.draft.dsw.gui.swing.jtree.controller;

import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.jtree.model.DraftTreeItem;
import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.gui.swing.view.my.MyTabHeader;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;
import raf.draft.dsw.gui.swing.view.my.MyTabbedPane;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.structures.Building;
import raf.draft.dsw.model.structures.Project;
import raf.draft.dsw.model.structures.ProjectExplorer;
import raf.draft.dsw.model.structures.Room;
import raf.draft.dsw.model.structures.roomelements.RoomElement;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;

public class OpenTab{
    private JTree projectExplorer;
    private MyTabbedPane myTabbedPane;
    private int width;
    private int height;
    private int id = 0;

    public void sendRoom(DraftTreeItem draftTreeItem){
        Room sendNode;
        if(draftTreeItem.getDraftNode() instanceof Room){
            MainFrame.getInstance().scrollPane.setVisible(true);
            MainFrame.getInstance().revalidate();
            sendNode = (Room) draftTreeItem.getDraftNode();
            openTabForNode(sendNode, draftTreeItem);
        }else if(draftTreeItem.getDraftNode() instanceof Project){
            Project project = (Project) draftTreeItem.getDraftNode();
            for (DraftNode object : project.getChildren()) {
                if (object instanceof Room) {
                    MainFrame.getInstance().scrollPane.setVisible(true);
                    MainFrame.getInstance().revalidate();
                    sendNode = (Room) object;
                    openTabForNode(sendNode, draftTreeItem);
                } else if (object instanceof Building) {
                    MainFrame.getInstance().scrollPane.setVisible(true);
                    MainFrame.getInstance().revalidate();
                    for (DraftNode rooms : ((Building) object).getChildren()) {
                        sendNode = (Room)rooms;
                        openTabForNode(sendNode, draftTreeItem);
                    }
                }
            }
        }else if(draftTreeItem.getDraftNode() instanceof RoomElement){

        }
    }

    public OpenTab(JTree projectExplorer, MyTabbedPane myTabbedPane) {

        this.projectExplorer = projectExplorer;
        this.myTabbedPane = myTabbedPane;

        this.projectExplorer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(MainFrame.getInstance().getDraftTree().getSelectedNode() == null) return;
                if (MainFrame.getInstance().getDraftTree().getSelectedNode().getDraftNode() instanceof ProjectExplorer ||
                    MainFrame.getInstance().getDraftTree().getSelectedNode().getDraftNode() instanceof Building)
                    return;
                if (e.getClickCount() == 1) {
                    DraftTreeItem draftTreeItem = (DraftTreeItem) projectExplorer.getLastSelectedPathComponent();
                    //DraftNode draftNode = draftTreeItem.getDraftNode();
                    TreePath selectedPath = projectExplorer.getPathForLocation(e.getX(), e.getY());
                    if(selectedPath != null) {
                        sendRoom(draftTreeItem);
                    }
                }
            }
        });
    }
    public void openTabForNode(Room node, DraftTreeItem draftTreeItem) {
        for (int i = 0; i < myTabbedPane.getTabCount(); i++) {
            Component tabComponent = myTabbedPane.getComponentAt(i);
            if(tabComponent instanceof MyTabPanel) {
                MyTabPanel panel = (MyTabPanel) tabComponent;
                if (panel.getRoom().equals(node)) {
                    myTabbedPane.startEditRoomState();
                    myTabbedPane.setSelectedIndex(i);
                    undoRedo();
                    return;
                }
            }
        }

        int index = -1;
        for (Map.Entry<Integer, MyTabPanel> entry : myTabbedPane.getMapOfClosedTabs().entrySet()) {
            if(entry.getValue().getRoom().equals(node)){
                index = entry.getKey();
                myTabbedPane.addTab(node.toString(), entry.getValue());
                addClosableTab(node.toString(), entry.getValue(), node);
                myTabbedPane.setSelectedComponent(entry.getValue());
                myTabbedPane.startEditRoomState();
                undoRedo();
                //entry.getValue().repaint();
            }
        }
        if(index != -1){
            myTabbedPane.getMapOfClosedTabs().remove(index);
            return;
        }
        MyTabPanel tabContent = getTabContent(node, draftTreeItem);

        addClosableTab(node.toString(), tabContent, node);
        myTabbedPane.setSelectedComponent(tabContent);
        undoRedo();
    }

    private void addClosableTab(String title, Component content, Room node) {

        myTabbedPane.addTab(title, content);

        MyTabHeader myTabHeader = new MyTabHeader(new FlowLayout(FlowLayout.LEFT, 0, 0), title, node);

        myTabHeader.getCloseButton().addActionListener(e -> {
            int tabIndex = myTabbedPane.indexOfTabComponent(myTabHeader);
            if (tabIndex != -1) {
                myTabbedPane.getMapOfClosedTabs().put(id++, (MyTabPanel) content);
                myTabbedPane.remove(tabIndex);
            }
        });
        myTabHeader.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                myTabbedPane.setTabComponentAt(myTabbedPane.indexOfComponent(content), myTabHeader);
                myTabbedPane.setSelectedComponent(content);
                undoRedo();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        ((MyTabPanel) content).setHeader(myTabHeader);
        myTabbedPane.setTabComponentAt(myTabbedPane.indexOfComponent(content), myTabHeader);
    }

    private MyTabPanel getTabContent(Room node, DraftTreeItem draftTreeItem) {
        MyTabPanel tabContent = new MyTabPanel(new BorderLayout(), node, myTabbedPane, draftTreeItem);
        SwingUtilities.invokeLater(() -> {
            width = tabContent.getWidth();
            height = tabContent.getHeight();
            System.out.println("Width: " + width + ", Height: " + height);
        });
        tabContent.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                tabContentInit(tabContent, width, height);
            }
        });
        tabContent.setPath(tabContent.getRoom().PathOfRoom());
        tabContent.setAuthor(tabContent.getRoom().getAuthorFromProject());
        return tabContent;
    }

    private static void tabContentInit(MyTabPanel tabContent, int width, int height) {
        if(tabContent.getWidth() == 0 || tabContent.getHeight() == 0) {
            tabContent.setWidth(width);
            tabContent.setHeight(height);
//            tabContent.editScaleFactor();
        }else {
            tabContent.setWidth(tabContent.getWidth());
            tabContent.setHeight(tabContent.getHeight());
//            tabContent.editScaleFactor();
        }
    }
    private void undoRedo(){
        if(((MyTabPanel)myTabbedPane.getSelectedComponent()).commandSize() == 0)
            ApplicationFramework.getInstance().disableUndo();
        else ApplicationFramework.getInstance().enableUndo();
        if(((MyTabPanel)myTabbedPane.getSelectedComponent()).commandSize() == ((MyTabPanel)myTabbedPane.getSelectedComponent()).commandPos())
            ApplicationFramework.getInstance().disableRedo();
        else ApplicationFramework.getInstance().disableUndo();
    }
}
