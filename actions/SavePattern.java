package raf.draft.dsw.controller.actions;

import raf.draft.dsw.controller.AbstractRoomAction;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.jtree.model.DraftTreeItem;
import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.model.messages.MessageType;
import raf.draft.dsw.model.structures.Room;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class SavePattern extends AbstractRoomAction {
    public SavePattern() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.ALT_DOWN_MASK));
        putValue(SMALL_ICON, loadIcon("/images/toolBar/loadPattern.png"));
        putValue(NAME, "Save pattern");
        putValue(SHORT_DESCRIPTION, "Save patern");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DraftTreeItem selected = MainFrame.getInstance().getDraftTree().getSelectedNode();
        if(selected == null) return;
        if(!(selected.getDraftNode() instanceof Room))
            return;
//        if(selected.getDraftNode().getName().charAt(selected.getDraftNode().getName().length()-1) == ' ')
//            selected.getDraftNode().setName(selected.getDraftNode().getName().replaceAll(" $", ""));
        ApplicationFramework.getInstance().getRoomController().savePattern((Room) selected.getDraftNode());
    }
}
