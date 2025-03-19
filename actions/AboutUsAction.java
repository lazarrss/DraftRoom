package raf.draft.dsw.controller.actions;

import raf.draft.dsw.controller.AbstractRoomAction;
import raf.draft.dsw.gui.swing.view.my.MyAboutUs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class AboutUsAction extends AbstractRoomAction {
    public AboutUsAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.ALT_DOWN_MASK));
        putValue(SMALL_ICON, loadIcon("/images/toolBar/aboutUs.png"));
        putValue(NAME, "About us");
        putValue(SHORT_DESCRIPTION, "About us");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        MyAboutUs myAboutUs = new MyAboutUs();
        myAboutUs.setVisible(true);
    }
}
