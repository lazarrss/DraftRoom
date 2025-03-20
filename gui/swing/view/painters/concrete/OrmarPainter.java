package raf.draft.dsw.gui.swing.view.painters.concrete;

import raf.draft.dsw.utils.GeometryUtils;
import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;
import raf.draft.dsw.gui.swing.view.painters.RoomElementPainter;
import raf.draft.dsw.model.structures.roomelements.RoomElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;

public class OrmarPainter extends RoomElementPainter {
    public OrmarPainter(RoomElement roomElement) {
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

        int x = (int) getRoomElement().getLocation().getX();
        int y = (int) getRoomElement().getLocation().getY();
        GeneralPath path = new GeneralPath();
//        if(((MyTabPanel) MainFrame.getInstance().getTabbedPane().getSelectedComponent()).getSelectionList().contains(this)){
//            this.fillSelected(g);
//        }

        Rectangle rectangle = new Rectangle(x, y, width, height);
        path.append(rectangle, false);
        path.moveTo(x + (float) width / 2, y);
        path.lineTo(x + (float) width / 2, y + height);
        g.fillOval(x + width / 2 - (width / 25), y + height / 2 +(height / 25), width / 25, height/ 25);
        g.fillOval(x + width / 2 + (width / 25) - (width / 25) , y + height / 2 + (height / 25), width / 25, height/ 25);
        path.closePath();
        setShape(path);
        g.draw(getShape());

        Shape updatedBounds = getShape();
        setRotatedBounds(rotate.createTransformedShape(updatedBounds));

        g.setTransform(start);

        int x1 = getRotatedBounds().getBounds().x;
        int y1 = getRotatedBounds().getBounds().y;
        int width1 = (int) getRotatedBounds().getBounds().getWidth();
        int height1 = (int) getRotatedBounds().getBounds().getHeight();

        Rectangle rectangle1 = new Rectangle(x1, y1, width1, height1);

        GeometryUtils.setResizeRectangle(g, rectangle1, getRoomElement());
    }
}
