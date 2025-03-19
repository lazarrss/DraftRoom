package raf.draft.dsw.controller.actions;

import raf.draft.dsw.controller.AbstractRoomAction;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.jtree.model.DraftTreeItem;
import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.model.messages.MessageType;
import raf.draft.dsw.model.structures.ProjectExplorer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class DeleteNode extends AbstractRoomAction {
    public DeleteNode(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
        putValue(SMALL_ICON, loadIcon("/images/toolBar/delete.png"));
        putValue(NAME, "Delete");
        putValue(SHORT_DESCRIPTION, "DeleteDraftNode");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DraftTreeItem selected = MainFrame.getInstance().getDraftTree().getSelectedNode();
        if(selected == null){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Select something to delete", MessageType.ERROR);
            return;
        }
        if(selected.getDraftNode() instanceof ProjectExplorer){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Project Explorer cannot be deleted", MessageType.ERROR);
            return;
         }

        MainFrame.getInstance().getDraftTree().removeChild(selected);
        selected.getDraftNode().removeFromTree();
        MainFrame.getInstance().projectExplorer.clearSelection();
    }
}
