package raf.draft.dsw.gui.swing.view.painters.concrete;

import raf.draft.dsw.utils.GeometryUtils;
import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;
import raf.draft.dsw.gui.swing.view.painters.RoomElementPainter;
import raf.draft.dsw.model.structures.roomelements.RoomElement;

import java.awt.*;
import java.awt.geom.*;

public class WCSoljaPainter extends RoomElementPainter {
    public WCSoljaPainter(RoomElement roomElement) {
        super(roomElement);
        setShape(new Rectangle());
        setRotatedBounds(new Rectangle());
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

        path.moveTo(x,y);

        path.lineTo(x + width, y);
        path.lineTo(x + width, y + (float) height / 2);
        path.moveTo(x,y);
        path.lineTo(x,y + (float) height / 2);

        path.moveTo(x , y + (float) height / 6);
        path.lineTo(x + width, y + (float) height / 6);

        g.drawArc(x, y, width, height, 0, -180);
        Ellipse2D ellipse2D = new Ellipse2D.Double(x, y, width, height);

        path.moveTo(x+width/3, y + height/3);
        path.lineTo(x + width/3 + width/3, y + height/3);
        path.lineTo(x + width/3 + width/3, y + (float) height / 3 + height/6);
        path.moveTo(x+width/3, y + height/3);
        path.lineTo(x+width/3,y + (float) height / 3 + height/6);
        g.drawArc(x+width/3, y + height/3, width/3, height/3, 0, -180);

        Rectangle bounds = new Rectangle(x, y, width, height);

        setShape(bounds);

        g.draw(path);

        Shape updatedBounds = getShape();
        setRotatedBounds(rotate.createTransformedShape(updatedBounds));

        g.setTransform(start);

        int x1 = getRotatedBounds().getBounds().x;
        int y1 = getRotatedBounds().getBounds().y;
        int width1 = (int) getRotatedBounds().getBounds().getWidth();
        int height1 = (int) getRotatedBounds().getBounds().getHeight();

        if(getRoomElement().getRotationRatio() == 0 || getRoomElement().getRotationRatio() == 3) {
            Ellipse2D ellipse2D1 = new Ellipse2D.Double(x1, y1, width1, height1);

            Line2D diagonal1 = new Line2D.Double(x1, y1, x1 + width1, y1 + height1);
            Line2D diagonal2 = new Line2D.Double(x1 + width1, y1, x1, y1 + height1);

            GeometryUtils.setResizeEllipse(getRoomElement(), diagonal1, diagonal2, ellipse2D1, g);
        }else{
            GeometryUtils.setResizeRectangle(g, new Rectangle(x1, y1, width1, height1), getRoomElement());
        }
    }
}
