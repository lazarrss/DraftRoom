package raf.draft.dsw.controller.state.actions;

import raf.draft.dsw.controller.AbstractRoomAction;
import raf.draft.dsw.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class RotateRightStateAction extends AbstractRoomAction {
    public RotateRightStateAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F2, InputEvent.CTRL_DOWN_MASK));
        putValue(SMALL_ICON, loadIcon("/images/states/rotate.png"));
        putValue(NAME, "Rotate");
        putValue(SHORT_DESCRIPTION, "Rotate state");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(MainFrame.getInstance().getTabbedPane().getSelectedComponent() == null)
            return;
        MainFrame.getInstance().getTabbedPane().startRotateRightState();
    }
}
