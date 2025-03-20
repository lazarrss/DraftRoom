package raf.draft.dsw.model.nodes;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import raf.draft.dsw.model.structures.Project;
import raf.draft.dsw.model.structures.Room;
import raf.draft.dsw.model.structures.roomelements.RoomElement;

@JsonTypeName("draftnodeleaf")

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RoomElement.class, name = "roomElement")
})
public abstract class DraftNodeLeaf extends DraftNode{
    public DraftNodeLeaf(){

    }

    public DraftNodeLeaf(String name, DraftNode parent) {
        super(name, parent);
    }
}
