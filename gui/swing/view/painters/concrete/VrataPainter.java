package raf.draft.dsw.gui.swing.view.painters.concrete;

import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;
import raf.draft.dsw.gui.swing.view.painters.RoomElementPainter;
import raf.draft.dsw.model.structures.roomelements.RoomElement;
import raf.draft.dsw.utils.GeometryUtils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;

public class VrataPainter extends RoomElementPainter {
    public VrataPainter(RoomElement roomElement) {
        super(roomElement);
        setShape(new Rectangle());
    }
    @Override
    public void paint(Graphics2D g2d) {
        double scale = ((MyTabPanel) MainFrame.getInstance().getTabbedPane().getSelectedComponent()).getScalingFactor();
        paint(g2d, getRoomElement(), (int) (getRoomElement().getWidth() * scale), (int) (getRoomElement().getHeight() * scale));
    }
    @Override
    public void paint(Graphics2D g, RoomElement object, int width, int height) {
        AffineTransform start = g.getTransform();
        AffineTransform rotate = new AffineTransform();

        int centerX = getRoomElement().getLocation().x + width/2;
        int centerY = getRoomElement().getLocation().y + height/2;

        rotate.rotate(Math.PI / 2 * getRoomElement().getRotationRatio(), centerX, centerY);
        g.transform(rotate);

        int x1 = (int) getRoomElement().getLocation().getX();
        int y1 = (int) getRoomElement().getLocation().getY();

        int x = x1 + width;
        int y = y1;

        GeneralPath path = new GeneralPath();
//        if(((MyTabPanel) MainFrame.getInstance().getTabbedPane().getSelectedComponent()).getSelectionList().contains(this)){
//            this.fillSelected(g);
//        }

        path.moveTo(x,y);
        path.lineTo(x, y + height);
        path.moveTo(x,y);
        path.append(new Arc2D.Float(x - (float) width , y, width*2, height*2 , 90, 90, Arc2D.OPEN), false);

        Rectangle bounds = new Rectangle(x1, y1, width, height);
        setShape(bounds);
        g.draw(path);

        Shape updatedBounds = getShape();
        setRotatedBounds(rotate.createTransformedShape(updatedBounds));
        g.setTransform(start);

        int x2 = getRotatedBounds().getBounds().x;
        int y2 = getRotatedBounds().getBounds().y;
        int width1 = (int) getRotatedBounds().getBounds().getWidth();
        int height1 = (int) getRotatedBounds().getBounds().getHeight();

        Rectangle rectangle1 = new Rectangle(x2, y2, width1, height1);

        GeometryUtils.setResizeRectangle(g, rectangle1, getRoomElement());
    }
}
