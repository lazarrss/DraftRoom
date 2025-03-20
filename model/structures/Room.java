package raf.draft.dsw.model.structures;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import raf.draft.dsw.controller.observer.IPublisher;
import raf.draft.dsw.controller.observer.ISubscriber;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.model.messages.Message;
import raf.draft.dsw.model.messages.MessageType;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.DraftNodeComposite;
import raf.draft.dsw.model.nodes.DraftNodeLeaf;
import raf.draft.dsw.model.structures.roomelements.RoomElement;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@JsonTypeName("room")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Room extends DraftNodeComposite implements IPublisher {
    @JsonIgnore
    List<ISubscriber> subscriberList = new ArrayList<>();
    @JsonIgnore
    List<RoomElement> copyList = new ArrayList<>();
    private double roomHeight;
    private double roomWidth;
    private static int lastid = 0;
    private int id;
    public Room(){

    }
    public Room(String name, DraftNode parent) {
        super(name, parent);
        this.id = lastid++;
    }
    @Override
    public void addChild(DraftNode draftNode) {
        if(validChild(draftNode)) {
            getChildren().add(draftNode);
            notifySubscribers(new Message("repaint", MessageType.INFO, LocalDateTime.now()));
            ApplicationFramework.getInstance().getProjectController().setOnChanged(this);
        }
        else
            JOptionPane.showMessageDialog(null, "Nije validno dete", "Invalid child", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    protected boolean validChild(DraftNode draftNode) {
        return draftNode instanceof RoomElement;
    }

    public Room(String name, DraftNode parent, double roomHeight, double roomWidth) {
        super(name, parent);
        this.roomHeight = roomHeight;
        this.roomWidth = roomWidth;
        this.id = lastid++;
    }
    @JsonIgnore
    public void removeFromTree(){
        notifySubscribers(new Message("removed", MessageType.INFO, LocalDateTime.now()));
//        ApplicationFramework.getInstance().getProjectController().setOnChanged(this);
    }
    public String PathOfRoom(){
        DraftNode draftNode = this.getParent();
        if(draftNode == null) return "";
        if(draftNode instanceof Project){
            return draftNode.getName() + "/";
        }
        return draftNode.getParent().getName() + "/" + draftNode.getName();
    }
    public String getAuthorFromProject(){
        DraftNode draftNode = this.getParent();
        if(draftNode == null) return "";
        if(draftNode instanceof Project)
            return ((Project) draftNode).getAuthor();
        else return ((Project)draftNode.getParent()).getAuthor();
    }
    public boolean doesNameExsists(String name){
        if(this.getParent() instanceof Project) {
            for (DraftNode node : ((Project) this.getParent()).getChildren()) {
                if (node.getName().equals(name) && (node instanceof Room)) {
                    return true;
                }
            }
        }else{
            for(DraftNode node : ((Building)this.getParent()).getChildren()){
                if(node.getName().equals(name) && (node instanceof Room)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public void removeChild(DraftNode draftNode) {
        super.removeChild(draftNode);
        notifySubscribers(new Message("repaint", MessageType.INFO, LocalDateTime.now(), this));
        ApplicationFramework.getInstance().getProjectController().setOnChanged(this);
    }

    public double getRoomHeight() {
        return roomHeight;
    }

    public void setRoomHeight(double roomHeight) {
        this.roomHeight = roomHeight;
//        notifySubscribers(new Message("repaint", MessageType.INFO, LocalDateTime.now(), this));
    }

    public double getRoomWidth() {
        return roomWidth;
    }

    public void setRoomWidth(double roomWidth) {
        this.roomWidth = roomWidth;
//        notifySubscribers(new Message("repaint", MessageType.INFO, LocalDateTime.now(), this));
    }

    public List<RoomElement> getCopyList() {
        return copyList;
    }

    public void setCopyList(List<RoomElement> copyList) {
        this.copyList = copyList;
        ApplicationFramework.getInstance().getProjectController().setOnChanged(this);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        notifySubscribers(new Message("value changed: " + name, MessageType.INFO, LocalDateTime.now()));
        ApplicationFramework.getInstance().getProjectController().setOnChanged(this);
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Room room = (Room) o;
        return id == room.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public void addSubscriber(ISubscriber subscriber) {
        if(!subscriberList.contains(subscriber)) {
            subscriberList.add(subscriber);
            if(this.getParent() instanceof Building)
                ((Building)this.getParent()).addSubscriber(subscriber);
            else if(this.getParent() instanceof Project)
                ((Project)this.getParent()).addSubscriber(subscriber);
            ApplicationFramework.getInstance().getProjectController().setOnChanged(this);
        }
    }

    @Override
    public void removeSubscriber(ISubscriber subscriber) {
        subscriberList.remove(subscriber);
        ApplicationFramework.getInstance().getProjectController().setOnChanged(this);
    }

    @Override
    public void notifySubscribers(Message message) {
        for(ISubscriber subscriber : subscriberList)
            subscriber.update(message);
    }
}
