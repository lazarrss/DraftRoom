package raf.draft.dsw.controller.state.actions;

import raf.draft.dsw.controller.AbstractRoomAction;
import raf.draft.dsw.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class ResizeStateAction extends AbstractRoomAction {
    public ResizeStateAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F2, InputEvent.CTRL_DOWN_MASK));
        putValue(SMALL_ICON, loadIcon("/images/states/resize.png"));
        putValue(NAME, "Resize");
        putValue(SHORT_DESCRIPTION, "Resize state");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getTabbedPane().startResizeState();
    }
}
