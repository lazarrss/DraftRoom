package raf.draft.dsw.model.structures.roomelements.concrete;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.structures.roomelements.RoomElement;

import java.awt.*;
import java.util.Objects;

@JsonTypeName("Bojler")

public class Bojler extends RoomElement {
    private static int lastid = 1;
    private int id;
    public Bojler(){

    }
    public Bojler(String name, DraftNode parent) {
        super("Bojler"+lastid, parent);
        this.id = lastid++;
    }

    public Bojler(String name, DraftNode parent, Point location, int width, int height, int rotationRatio) {
        super("bojler"+lastid, parent, location, width, height, rotationRatio);
        int x;
        int y;
        Point point;
        setBottomRightPoint(null);
        this.id = lastid++;
    }

    public Bojler(RoomElement copy) {
        super(copy);
    }

    @Override
    public RoomElement clone() {
        return new Bojler(this);
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
        Bojler bojler = (Bojler) o;
        return id == bojler.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
