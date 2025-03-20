package raf.draft.dsw.gui.swing.jtree;

import raf.draft.dsw.controller.observer.IPublisher;
import raf.draft.dsw.controller.observer.ISubscriber;
import raf.draft.dsw.gui.swing.jtree.model.DraftTreeItem;
import raf.draft.dsw.gui.swing.jtree.view.DraftTreeView;
import raf.draft.dsw.model.structures.ProjectExplorer;
import raf.draft.dsw.model.structures.Room;
import raf.draft.dsw.model.structures.roomelements.RoomElement;

public interface DraftTree{
    DraftTreeView generateTree(ProjectExplorer projectExplorer);
    void addChild(DraftTreeItem parent, String[] attributes, String type);
    void addChild(Room room, RoomElement roomElement);
    DraftTreeItem getSelectedNode();
    void removeChild(DraftTreeItem child);
    void removeChild(DraftTreeItem room, RoomElement roomElement);
}
