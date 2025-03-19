package raf.draft.dsw.controller.actions;

import raf.draft.dsw.controller.AbstractRoomAction;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.jtree.DraftTreeImplementation;
import raf.draft.dsw.gui.swing.jtree.model.DraftTreeItem;
import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;
import raf.draft.dsw.model.nodes.DraftNodeComposite;
import raf.draft.dsw.model.structures.Building;
import raf.draft.dsw.model.structures.Room;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class LoadPattern extends AbstractRoomAction {
    public LoadPattern() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.ALT_DOWN_MASK));
        putValue(SMALL_ICON, loadIcon("/images/toolBar/pp.png"));
        putValue(NAME, "Load pattern");
        putValue(SHORT_DESCRIPTION, "load");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MyTabPanel curr = ((MyTabPanel)MainFrame.getInstance().getTabbedPane().getSelectedComponent());

        if(curr == null) return;
        if(curr.getRoom().getRoomHeight() != 0 && curr.getRoom().getRoomWidth() != 0)
            return;

        String name = curr.getRoom().getName();
        String author = curr.getRoom().getAuthorFromProject();
        String path = curr.getRoom().PathOfRoom();

        Room room = ApplicationFramework.getInstance().getRoomController().loadPattern();
        if(room == null) return;

        DraftTreeItem parent = (DraftTreeItem) curr.getRoomItem().getParent();

        room.setName(name);
        if(parent.getDraftNode() instanceof Building)
            room.setName(name + ' ');

        ((DraftTreeImplementation) MainFrame.getInstance().getDraftTree()).addChild(parent, room, curr);

        curr = ((MyTabPanel)MainFrame.getInstance().getTabbedPane().getSelectedComponent());

        curr.setAuthor(author);
        curr.setPath(path);
    }
}
