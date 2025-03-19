package raf.draft.dsw.controller.actions;

import raf.draft.dsw.controller.AbstractRoomAction;
import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;
import raf.draft.dsw.gui.swing.view.painters.RoomElementPainter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class AutoOrganize extends AbstractRoomAction {
    public AutoOrganize() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.ALT_DOWN_MASK));
        putValue(SMALL_ICON, loadIcon("/images/toolBar/autoorganize.png"));
        putValue(NAME, "auto organize");
        putValue(SHORT_DESCRIPTION, "auto organize");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MyTabPanel curr = (MyTabPanel) MainFrame.getInstance().getTabbedPane().getSelectedComponent();
        for(RoomElementPainter painter : curr.getPainterList()){
            MainFrame.getInstance().getDraftTree().removeChild(curr.getRoomItem(), painter.getRoomElement());
        }
        curr.getPainterList().clear();
        curr.getSelectionList().clear();

        MainFrame.getInstance().getOrganizer().init();
    }
}
