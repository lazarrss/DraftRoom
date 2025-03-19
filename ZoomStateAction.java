package raf.draft.dsw.controller.state.actions;

import raf.draft.dsw.controller.AbstractRoomAction;
import raf.draft.dsw.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class ZoomStateAction extends AbstractRoomAction {
    public ZoomStateAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F2, InputEvent.CTRL_DOWN_MASK));
        putValue(SMALL_ICON, loadIcon("/images/states/zoom.png"));
        putValue(NAME, "Zoom");
        putValue(SHORT_DESCRIPTION, "Zoom state");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getTabbedPane().startZoomState();
    }
}
