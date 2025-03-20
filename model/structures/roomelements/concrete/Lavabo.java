package raf.draft.dsw.model.structures.roomelements.concrete;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.structures.roomelements.RoomElement;

import java.awt.*;
import java.util.Objects;

@JsonTypeName("Lavabo")

public class Lavabo extends RoomElement {
    private static int lastid = 1;
    private int id;
    public Lavabo(){

    }
    public Lavabo(String name, DraftNode parent) {
        super(name, parent);
        this.id = lastid++;
    }
    public Lavabo(String name, DraftNode parent, Point location, int width, int height, int rotationRatio) {
        super("lavabo"+lastid, parent, location, width, height, rotationRatio);
        this.id = lastid++;
    }
    public Lavabo(RoomElement copy) {
        super(copy);
    }

    @Override
    public RoomElement clone() {
        return new Lavabo(this);
    }

    @JsonIgnore
    @Override
    public void setName(String name) {
        super.setName("Lavabo"+name);
        id = Integer.parseInt(name);
        lastid = id;
    }
    @Override
    public void mySetName(String name){
        super.setName(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Lavabo lavabo = (Lavabo) o;
        return id == lavabo.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
