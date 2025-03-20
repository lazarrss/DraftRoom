package raf.draft.dsw.gui.swing.view.painters.concrete;

import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;
import raf.draft.dsw.gui.swing.view.painters.RoomElementPainter;
import raf.draft.dsw.model.structures.roomelements.RoomElement;
import raf.draft.dsw.model.structures.roomelements.concrete.Lavabo;
import raf.draft.dsw.utils.GeometryUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;

public class LavaboPainter extends RoomElementPainter {

    public LavaboPainter(RoomElement roomElement) {
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

        path.moveTo(x, y);
        path.lineTo(x + width, y);
        path.lineTo(x + width / 2.0, y + height);
        path.lineTo(x, y);
        g.fillOval(x + width / 2 - (width / 15) / 2, y + height / 3 - (height / 15) , width / 15, height / 15);
        path.closePath();
        setShape(path);
        g.draw(getShape());

        Shape updatedBounds = getShape();
        setRotatedBounds(rotate.createTransformedShape(updatedBounds));

        int x1;
        int y1;
        g.setTransform(start);
        if(getRoomElement().getRotationRatio() == 0) {
            x1 = (x + width / 2) + width / 4;
            y1 = y + height / 2;
            getRoomElement().setBottomRightPoint(new Point(x1, y1));
            Rectangle rectangle = new Rectangle(x1 - 10, y1 - 10, 20,20);
            g.draw(rectangle);
        }else if(getRoomElement().getRotationRatio() == 3 || getRoomElement().getRotationRatio() == -1){
            x1 = x + width / 2;
            y1 = (y + height / 2) + height / 4;
            getRoomElement().setBottomRightPoint(new Point(x1, y1));
            Rectangle rectangle = new Rectangle(x1 - 10,y1 - 10, 20,20);
            g.draw(rectangle);
        }
        else {
            x1 = getRotatedBounds().getBounds().x;
            y1 = getRotatedBounds().getBounds().y;
            int width1 = (int) getRotatedBounds().getBounds().getWidth();
            int height1 = (int) getRotatedBounds().getBounds().getHeight();

            Rectangle rectangle1 = new Rectangle(x1, y1, width1, height1);

            GeometryUtils.setResizeRectangle(g, rectangle1, getRoomElement());
        }
    }
}
