package raf.draft.dsw.controller.actions;

import raf.draft.dsw.controller.AbstractRoomAction;
import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;
import raf.draft.dsw.model.structures.roomelements.RoomElement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.List;

public class Organize extends AbstractRoomAction {
    public Organize(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        putValue(SMALL_ICON, loadIcon("/images/toolBar/shuffle.png"));
        putValue(NAME, "Organize");
        putValue(SHORT_DESCRIPTION, "Organize");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(MainFrame.getInstance().getOrganizer().getRoomElementLIst() == null || MainFrame.getInstance().getOrganizer().getRoomElementLIst().isEmpty())
            return;
        List<RoomElement> list = MainFrame.getInstance().getOrganizer().getRoomElementLIst();
        MyTabPanel curr = (MyTabPanel) MainFrame.getInstance().getTabbedPane().getSelectedComponent();
        if(curr.getRoom().getRoomHeight() == 0 || curr.getRoom().getRoomWidth() == 0)
            return;
        if(list.size() != curr.getPainterList().size()) {
            return;
        }
        for(int i = 0; i < list.size(); i++){
            if(!list.contains(curr.getPainterList().get(i).getRoomElement()))
                return;
        }

        MainFrame.getInstance().getOrganizer().arrangeRoom(list, curr);
    }
}
