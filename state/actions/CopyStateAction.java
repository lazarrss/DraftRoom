package raf.draft.dsw.controller.state.actions;

import raf.draft.dsw.controller.AbstractRoomAction;
import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class CopyStateAction extends AbstractRoomAction {
    public CopyStateAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        putValue(SMALL_ICON, loadIcon("/images/states/copy.png"));
        putValue(NAME, "Delete");
        putValue(SHORT_DESCRIPTION, "Delete state");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(MainFrame.getInstance().getTabbedPane().getSelectedComponent() == null)
            return;
        MainFrame.getInstance().getTabbedPane().startCopyState();
    }
}
