package raf.draft.dsw.model.nodefactory;

import raf.draft.dsw.controller.observer.IPublisher;
import raf.draft.dsw.controller.observer.ISubscriber;
import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.model.messages.Message;
import raf.draft.dsw.model.messages.MessageType;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.structures.Building;
import raf.draft.dsw.model.structures.Project;
import raf.draft.dsw.model.structures.ProjectExplorer;
import raf.draft.dsw.model.structures.Room;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DraftNodeFactory implements IPublisher {

    public static final String PROJECT = "Project",
                                BUILDING = "Building",
                                ROOM = "Room";

    private List<ISubscriber> subscriberList = new ArrayList<>();
    public DraftNodeFactory(){
    }

    public DraftNode createDraftNode(String type, DraftNode parent, String... attributes) {
        switch (type) {
            case PROJECT -> {
                return new Project(attributes[0], parent, attributes[1], attributes[2]);
            }
            case BUILDING -> {
                return new Building(attributes[0], parent);
            }
            case ROOM -> {
                return new Room(attributes[0], parent);
            }
        }
        return null;
    }

    @Override
    public void addSubscriber(ISubscriber subscriber) {
        if(!subscriberList.contains(subscriber))
            subscriberList.add(subscriber);
    }

    @Override
    public void removeSubscriber(ISubscriber subscriber) {
        subscriberList.remove(subscriber);
    }

    @Override
    public void notifySubscribers(Message message) {
        for(ISubscriber subscriber : subscriberList)
            subscriber.update(message);
    }
}
