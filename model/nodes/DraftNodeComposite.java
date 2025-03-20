package raf.draft.dsw.model.nodes;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.model.structures.Building;
import raf.draft.dsw.model.structures.Project;
import raf.draft.dsw.model.structures.Room;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@JsonTypeName("draftnodecomposite")

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Project.class, name = "project"),
        @JsonSubTypes.Type(value = Room.class, name = "room"),
        @JsonSubTypes.Type(value = Building.class, name = "building")
})
public abstract class DraftNodeComposite extends DraftNode{
    private List<DraftNode> children = new ArrayList<>();
    public DraftNodeComposite(){

    }
    public DraftNodeComposite(String name, DraftNode parent) {
        super(name, parent);
    }

    public void addChild(DraftNode draftNode){
        if (this.validChild(draftNode)) {
            children.add(draftNode);
        }
        else
            JOptionPane.showMessageDialog(null, "Nije validno dete", "Invalid child", JOptionPane.ERROR_MESSAGE);
    }
    public void removeChild(DraftNode draftNode){
        children.remove(draftNode);
    }
    protected abstract boolean validChild(DraftNode draftNode);

    public List<DraftNode> getChildren() {
        return children;
    }

    public void setChildren(List<DraftNode> children) {
        this.children = children;
    }

}
