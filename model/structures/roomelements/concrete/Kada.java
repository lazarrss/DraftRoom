package raf.draft.dsw.model.structures.roomelements.concrete;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.structures.roomelements.RoomElement;

import java.awt.*;
import java.util.Objects;

@JsonTypeName("Kada")

public class Kada extends RoomElement {
    private static int lastid = 1;
    private int id;
    public Kada(){

    }
    public Kada(String name, DraftNode parent) {
        super(name, parent);
        this.id = lastid++;
    }

    public Kada(String name, DraftNode parent, Point location, int width, int height, int rotationRatio) {
        super("kada"+lastid, parent, location, width, height, rotationRatio);
        this.id = lastid++;
    }

    public Kada(RoomElement copy) {
        super(copy);
    }

    @Override
    public RoomElement clone() {
        return new Kada(this);
    }
    @JsonIgnore

    @Override
    public void setName(String name) {
        super.setName("Bojler"+name);
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
        Kada kada = (Kada) o;
        return id == kada.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
