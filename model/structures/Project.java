package raf.draft.dsw.model.structures;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import raf.draft.dsw.controller.observer.IPublisher;
import raf.draft.dsw.controller.observer.ISubscriber;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.model.messages.Message;
import raf.draft.dsw.model.messages.MessageType;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.DraftNodeComposite;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@JsonTypeName("project")
public class Project extends DraftNodeComposite implements IPublisher {
    private String author = "Default";
    private String pathToFile = "Default";
    @JsonIgnore
    List<ISubscriber> subscriberList = new ArrayList<>();
    private boolean changed = false;
    public Project(){
    }
    public Project(String name, DraftNode parent){
        super(name,parent);
        changed = true;
    }

    public Project(String name, DraftNode parent, String author, String pathToFile) {
        super(name, parent);
        this.author = author;
        this.pathToFile = pathToFile;
        changed = true;
    }

    public void removeFromTree(){
        notifySubscribers(new Message("removed", MessageType.INFO, LocalDateTime.now()));
        ApplicationFramework.getInstance().getProjectController().setOnChanged(this);
    }
    public boolean doesNameExsists(String name){
        for(DraftNode node : ((ProjectExplorer)this.getParent()).getChildren()){
            if(node.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    public boolean childNameTaken(String name, String type){
        for (DraftNode node : this.getChildren()) {
            if(type.equals("Room")) {
                if (node.getName().equals(name) && node instanceof Room) {
                    return true;
                }
            }else{
                if (node.getName().equals(name) && node instanceof Building) {
                    return true;
                }
            }
        }
        return false;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
        notifySubscribers(new Message("Project author changed: " + author, MessageType.INFO, LocalDateTime.now()));
        ApplicationFramework.getInstance().getProjectController().setOnChanged(this);
    }
    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public boolean isOnChanged() {
        return changed;
    }

    public void setOnChanged(boolean onChanged) {
        this.changed = onChanged;
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        notifySubscribers(new Message("project name changed: " + name, MessageType.INFO, LocalDateTime.now()));
        ApplicationFramework.getInstance().getProjectController().setOnChanged(this);
    }
    @Override
    protected boolean validChild(DraftNode draftNode) {
        return draftNode instanceof Building || draftNode instanceof Room;
    }


    @Override
    public void addSubscriber(ISubscriber subscriber) {
        if(!subscriberList.contains(subscriber)) {
            subscriberList.add(subscriber);
            changed = true;
        }
    }

    @Override
    public void removeSubscriber(ISubscriber subscriber) {
        subscriberList.remove(subscriber);
        changed = true;
    }

    @Override
    public void notifySubscribers(Message message) {
        for(ISubscriber subscriber : subscriberList)
            subscriber.update(message);
    }

}
