package raf.draft.dsw.model.structures;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import raf.draft.dsw.controller.observer.IPublisher;
import raf.draft.dsw.controller.observer.ISubscriber;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.model.messages.Message;
import raf.draft.dsw.model.messages.MessageType;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.DraftNodeComposite;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@JsonTypeName("building")

public class Building extends DraftNodeComposite implements IPublisher {
    @JsonIgnore
    List<ISubscriber> subscriberList = new ArrayList<>();
    @JsonSerialize
    @JsonDeserialize
    Color color = returnColor(this);
    public Building(){

    }
    public Building(String name, DraftNode parent) {
        super(name, parent);
    }
    public Color returnColor(Object node) {
        Color color;
        if (node instanceof Building) {
            Random random = new Random();
            color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            while(color.getRGB() == 0)
                color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        } else {
            color = null;
        }
        return color;
    }
    public void removeFromTree(){
        notifySubscribers(new Message("removed", MessageType.INFO, LocalDateTime.now()));
    }
    public boolean doesNameExists(String name){
        for(DraftNode node : ((Project)this.getParent()).getChildren()){
            if(node.getName().equals(name) && !(node instanceof Room)) {
                return true;
            }
        }
        return false;
    }
    public boolean childNameTaken(String name){
        for (DraftNode node : this.getChildren()) {
            if (node.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    @Override
    protected boolean validChild(DraftNode draftNode) {
        return draftNode instanceof Room;
    }

    public Color getColor() {
        return color;
    }
    @JsonIgnore

    public void setColor(Color color) {
        this.color = color;
        ApplicationFramework.getInstance().getProjectController().setOnChanged(this);
    }
    @JsonIgnore
    @Override
    public void setName(String name) {
        super.setName(name);
        notifySubscribers(new Message("building value changed: " + name, MessageType.INFO, LocalDateTime.now()));
        ApplicationFramework.getInstance().getProjectController().setOnChanged(this);
    }

    @Override
    public void addSubscriber(ISubscriber subscriber) {
        if(!subscriberList.contains(subscriber)) {
            subscriberList.add(subscriber);
            ((Project)this.getParent()).addSubscriber(subscriber);
            ApplicationFramework.getInstance().getProjectController().setOnChanged(this);
        }
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
