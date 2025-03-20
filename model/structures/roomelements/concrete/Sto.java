package raf.draft.dsw.model.structures.roomelements.concrete;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.structures.roomelements.RoomElement;

import java.awt.*;
import java.util.Objects;

@JsonTypeName("Sto")

public class Sto extends RoomElement {
    private static int lastid = 1;
    private int id;
    public Sto(){

    }
    public Sto(String name, DraftNode parent) {
        super(name, parent);
        this.id = lastid++;
    }
    public Sto(String name, DraftNode parent, Point location, int width, int height, int rotationRatio) {
        super("sto"+lastid, parent, location, width, height, rotationRatio);
        this.id = lastid++;
    }


    public Sto(RoomElement copy) {
        super(copy);
    }

    @Override
    public RoomElement clone() {
        return new Sto(this);
    }
    @Override
    public void mySetName(String name){
        super.setName(name);
    }
    @JsonIgnore
    @Override
    public void setName(String name) {
        super.setName("Sto"+name);
        id = Integer.parseInt(name);
        lastid = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Sto sto = (Sto) o;
        return id == sto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
