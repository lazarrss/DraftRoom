package raf.draft.dsw.model.structures.roomelements.concrete;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.structures.roomelements.RoomElement;

import java.awt.*;
import java.util.Objects;

@JsonTypeName("VesMasina")

public class VesMasina extends RoomElement {
    private static int lastid = 1;
    private int id;
    public VesMasina(){

    }
    public VesMasina(String name, DraftNode parent) {
        super(name, parent);
        this.id = lastid++;
    }
    public VesMasina(String name, DraftNode parent, Point location, int width, int height, int rotationRatio) {
        super("vesmasina"+lastid, parent, location, width, height, rotationRatio);
        this.id = lastid++;
    }
    public VesMasina(RoomElement copy) {
        super(copy);
    }

    @Override
    public RoomElement clone() {
        return new VesMasina(this);
    }
    @Override
    public void mySetName(String name){
        super.setName(name);
    }
    @JsonIgnore
    @Override
    public void setName(String name) {
        super.setName("Ves Masina"+name);
        id = Integer.parseInt(name);
        lastid = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        VesMasina vesMasina = (VesMasina) o;
        return id == vesMasina.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
