package raf.draft.dsw.controller.actions;

import raf.draft.dsw.controller.AbstractRoomAction;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.jtree.DraftTreeImplementation;
import raf.draft.dsw.gui.swing.jtree.model.DraftTreeItem;
import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;
import raf.draft.dsw.model.messages.Message;
import raf.draft.dsw.model.messages.MessageType;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.structures.Building;
import raf.draft.dsw.model.structures.Project;
import raf.draft.dsw.model.structures.Room;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;

public class OpenAction extends AbstractRoomAction {
    public OpenAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        putValue(SMALL_ICON, loadIcon("/images/toolBar/open.png"));
        putValue(NAME, "Open");
        putValue(SHORT_DESCRIPTION, "OpenProject");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Project project = ApplicationFramework.getInstance().getProjectController().openProject();
        if(project == null) return;
        DraftTreeItem parent = (DraftTreeItem) ((DraftTreeImplementation) MainFrame.getInstance().getDraftTree()).getDefaultTreeModel().getRoot();

        for(int i=0; i< parent.getChildCount(); i++){
            DraftTreeItem childItem = (DraftTreeItem) parent.getChildAt(i);
//            if(child.getDraftNode().equals(project))
//                return;
            if (childItem.getDraftNode().equals(project)){
//                for(DraftNode child : project.getChildren()){
//                    if(child instanceof Building){
//                        for(DraftNode childchild : ((Building) child).getChildren()){
//                            for(int j=0; i<MainFrame.getInstance().getTabbedPane().getComponentCount(); i++){
//                                MyTabPanel panel = (MyTabPanel) MainFrame.getInstance().getTabbedPane().getComponent(j);
//                                if(panel.getRoom().equals(childchild))
//                                    MainFrame.getInstance().getTabbedPane().setSelectedComponent(panel);
//                            }
//                        }
//                    }else if(child instanceof Room)
//                        for(int j=0; i<MainFrame.getInstance().getTabbedPane().getComponentCount(); i++){
//                            MyTabPanel panel = (MyTabPanel) MainFrame.getInstance().getTabbedPane().getComponent(j);
//                            if(panel.getRoom().equals(child))
//                                MainFrame.getInstance().getTabbedPane().setSelectedComponent(panel);
//                        }
//                }
                ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Project is already opened", MessageType.INFO);
                return;
            }
        }

        ((DraftTreeImplementation) MainFrame.getInstance().getDraftTree()).addChild(parent, project);
    }
}
