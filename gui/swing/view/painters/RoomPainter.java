package raf.draft.dsw.gui.swing.view.painters;

import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;
import raf.draft.dsw.model.structures.Room;

import javax.swing.*;
import java.awt.*;

public class RoomPainter implements Painter<Room> {
    private Room room;
    private Rectangle shape;
    public RoomPainter(Room room) {
        this.room = room;
    }
    public void paint(Graphics2D g){
        double scale = ((MyTabPanel) MainFrame.getInstance().getTabbedPane().getSelectedComponent()).getScalingFactor();
        paint(g, room, (int) (room.getRoomWidth() * scale), (int) (room.getRoomHeight() * scale));
    }

    @Override
    public void paint(Graphics2D g, Room object, int width, int height) {
        g.setColor(Color.BLACK);
        Rectangle rectangle = new Rectangle(10 ,15, width, height);
        g.draw(rectangle);
        setShape(rectangle);
    }
    public Rectangle getShape() {
        return shape;
    }

    public void setShape(Rectangle shape) {
        this.shape = shape;
    }
}
