package raf.draft.dsw.gui.swing.view.painters;

import raf.draft.dsw.model.structures.roomelements.RoomElement;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Objects;

public abstract class RoomElementPainter implements Painter<RoomElement> {
    private AffineTransform rotate = new AffineTransform();
    private RoomElement roomElement;
    private Shape shape;
    private Shape rotatedBounds;

    public RoomElementPainter(RoomElement roomElement) {
        this.roomElement = roomElement;
    }
    public RoomElementPainter(RoomElementPainter copy){
        this.roomElement = copy.roomElement;
        this.shape = copy.shape;
        this.rotatedBounds = copy.rotatedBounds;
    }
    public abstract void paint(Graphics2D g2d);
    public boolean elementAt(Point point){
        if(getRoomElement().getRotationRatio() == 0)
            return getShape().intersects(new Rectangle(point.x, point.y, 1, 1));
        else return rotatedElementAt(point);
    }
    public boolean elementAt(Rectangle rectangle){
//        if(getRoomElement().getRotationRatio() == 0)
            return getShape().intersects(rectangle);
//        else return rotatedElementAt(rectangle);
    }
    public boolean rotatedElementAt(Rectangle rectangle){
        return getRotatedBounds().intersects(rectangle);
    }
    public boolean rotatedElementAt(Point point){
        return getRotatedBounds().intersects(new Rectangle(point.x, point.y, 1, 1));
    }
    public void fillSelected(Graphics2D graphics2D) {
        graphics2D.setColor(Color.orange);
        paint(graphics2D);
    }
    public RoomElement getRoomElement() {
        return roomElement;
    }

    public void setRoomElement(RoomElement roomElement) {
        this.roomElement = roomElement;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public Shape getRotatedBounds() {
        return rotatedBounds;
    }

    public void setRotatedBounds(Shape rotatedBounds) {
        this.rotatedBounds = rotatedBounds;
    }

    public AffineTransform getRotate() {
        return rotate;
    }

    public void setRotate(AffineTransform rotate) {
        this.rotate = rotate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomElementPainter painter = (RoomElementPainter) o;
        return Objects.equals(rotate, painter.rotate) && Objects.equals(roomElement, painter.roomElement) && Objects.equals(shape, painter.shape) && Objects.equals(rotatedBounds, painter.rotatedBounds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rotate, roomElement, shape, rotatedBounds);
    }
}
