package raf.draft.dsw.controller.actions;

import raf.draft.dsw.controller.AbstractRoomAction;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class UndoAction extends AbstractRoomAction {
    public UndoAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        putValue(SMALL_ICON, loadIcon("/images/toolBar/undo.png"));
        putValue(NAME, "Undo");
        putValue(SHORT_DESCRIPTION, "UndoAction");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(MainFrame.getInstance().getTabbedPane().getSelectedComponent() == null)
            ApplicationFramework.getInstance().disableUndo();
        ((MyTabPanel) MainFrame.getInstance().getTabbedPane().getSelectedComponent()).getCommandManager().undoCommand();
    }
}
