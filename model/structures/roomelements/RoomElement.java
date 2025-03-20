package raf.draft.dsw.model.structures.roomelements;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import raf.draft.dsw.controller.observer.IPublisher;
import raf.draft.dsw.controller.observer.ISubscriber;
import raf.draft.dsw.model.messages.Message;
import raf.draft.dsw.model.messages.MessageType;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.DraftNodeLeaf;
import raf.draft.dsw.model.structures.roomelements.concrete.*;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@JsonTypeName("roomElement")
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Bojler.class, name = "Bojler"),
        @JsonSubTypes.Type(value = Kada.class, name = "Kada"),
        @JsonSubTypes.Type(value = Krevet.class, name = "Krevet"),
        @JsonSubTypes.Type(value = Lavabo.class, name = "Lavabo"),
        @JsonSubTypes.Type(value = Ormar.class, name = "Ormar"),
        @JsonSubTypes.Type(value = Sto.class, name = "Sto"),
        @JsonSubTypes.Type(value = VesMasina.class, name = "VesMasina"),
        @JsonSubTypes.Type(value = WCSolja.class, name = "WCSolja"),
        @JsonSubTypes.Type(value = Vrata.class, name = "Vrata")
})
public abstract class RoomElement extends DraftNodeLeaf implements Prototype, IPublisher {
    @JsonIgnore
    private List<ISubscriber> subscriberList = new ArrayList<>();
    private Point location;
    private int width;
    private int height;
    private int rotationRatio;
    private Point startPoint;
    private Point movePoint;
    private Point bottomRightPoint;
    public RoomElement(){

    }

    public RoomElement(String name, DraftNode parent) {
        super(name, parent);
    }

    public RoomElement(String name, DraftNode parent, Point location, int width, int height, int rotationRatio) {
        super(name, parent);
        this.location = location;
        this.width = width;
        this.height = height;
        this.rotationRatio = rotationRatio;
        startPoint = location;
    }

    public RoomElement(RoomElement copy){
        super(copy.getName(), copy.getParent());
        this.location = copy.getLocation();
        this.width = copy.getWidth();
        this.height = copy.getHeight();
        this.rotationRatio = copy.getRotationRatio();
        this.startPoint = new Point(copy.startPoint.x + 10, copy.startPoint.y + 10);
        this.movePoint = copy.movePoint;
        this.bottomRightPoint = copy.movePoint;
        this.setSubscriberList(copy.getSubscriberList());
    }

    public abstract RoomElement clone();
    public void mySetName(String name){

    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
        notifySubscribers(new Message("repaint", MessageType.INFO, LocalDateTime.now(), this));
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
        notifySubscribers(new Message("repaint", MessageType.INFO, LocalDateTime.now(), this));
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        notifySubscribers(new Message("repaint", MessageType.INFO, LocalDateTime.now(), this));
    }

    public int getRotationRatio() {
        return rotationRatio;
    }

    public Point getMovePoint() {
        return movePoint;
    }

    public Point getBottomRightPoint() {
        return bottomRightPoint;
    }

    public void setBottomRightPoint(Point bottomRightPoint) {
        this.bottomRightPoint = bottomRightPoint;
        notifySubscribers(new Message("repaint", MessageType.INFO, LocalDateTime.now(), this));
    }

    public void setMovePoint(Point movePoint) {
        this.movePoint = movePoint;
        notifySubscribers(new Message("repaint", MessageType.INFO, LocalDateTime.now(), this));
    }

    public void setRotationRatio(int rotationRatio) {
        this.rotationRatio = rotationRatio;
        if(rotationRatio > 3)
            this.rotationRatio %= 4;
        else if(rotationRatio < -3)
            this.rotationRatio %= 4;
        notifySubscribers(new Message("repaint", MessageType.INFO, LocalDateTime.now(), this));
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
        notifySubscribers(new Message("repaint", MessageType.INFO, LocalDateTime.now(), this));
    }

    public List<ISubscriber> getSubscriberList() {
        return subscriberList;
    }

    public void setSubscriberList(List<ISubscriber> subscriberList) {
        this.subscriberList = subscriberList;
    }


    @Override
    public void removeFromTree() {

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
