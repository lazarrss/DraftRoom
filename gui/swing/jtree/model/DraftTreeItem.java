package raf.draft.dsw.gui.swing.jtree.model;

import raf.draft.dsw.controller.observer.IPublisher;
import raf.draft.dsw.controller.observer.ISubscriber;
import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.model.messages.Message;
import raf.draft.dsw.model.messages.MessageType;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.structures.Room;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import java.awt.color.ColorSpace;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Flow;

public class DraftTreeItem extends DefaultMutableTreeNode implements IPublisher {
    private DraftNode draftNode;
    List<ISubscriber> subscriberList = new ArrayList<>();

    @Override
    public void addSubscriber(ISubscriber subscriber) {
        if(this.draftNode instanceof Room)
            if(!subscriberList.contains(subscriber))
                subscriberList.add(subscriber);
    }

    @Override
    public void removeSubscriber(ISubscriber subscriber) {
        subscriberList.remove(subscriber);
    }

    @Override
    public void notifySubscribers(Message message) {
        for (ISubscriber subscriber : subscriberList)
            subscriber.update(message);
    }

    public DraftTreeItem(DraftNode draftNode) {
        this.draftNode = draftNode;
        if(draftNode instanceof Room){
            this.addSubscriber(MainFrame.getInstance());
            this.addSubscriber(MainFrame.getInstance().getTabbedPane());
            notifySubscribers(new Message("Open tab item", MessageType.INFO, LocalDateTime.now(), this));
        }
    }
    public void setName(String name) {
        this.draftNode.setName(name);
    }

    public DraftNode getDraftNode() {
        return draftNode;
    }

    public void setDraftNode(DraftNode draftNode) {
        this.draftNode = draftNode;
    }

    @Override
    public String toString() {
        return draftNode.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DraftTreeItem that = (DraftTreeItem) o;
        return Objects.equals(draftNode, that.draftNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(draftNode);
    }
}
