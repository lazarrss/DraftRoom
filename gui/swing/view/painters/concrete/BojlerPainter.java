package raf.draft.dsw.gui.swing.view.painters.concrete;

import raf.draft.dsw.utils.GeometryUtils;
import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;
import raf.draft.dsw.gui.swing.view.painters.RoomElementPainter;
import raf.draft.dsw.model.structures.roomelements.RoomElement;

import java.awt.*;
import java.awt.geom.*;

public class BojlerPainter extends RoomElementPainter {
    public BojlerPainter(RoomElement roomElement) {
        super(roomElement);
        setShape(new Rectangle());
    }
    @Override
    public void paint(Graphics2D g2d) {
        AffineTransform start = g2d.getTransform();
        AffineTransform rotate = new AffineTransform();

        double scale = ((MyTabPanel) MainFrame.getInstance().getTabbedPane().getSelectedComponent()).getScalingFactor();

        int width = (int) (getRoomElement().getWidth() * scale);
        int height = (int) (getRoomElement().getHeight() * scale);

        double x = getRoomElement().getLocation().x;
        double y = getRoomElement().getLocation().y;

        int centerX = getRoomElement().getLocation().x + width/2;
        int centerY = getRoomElement().getLocation().y + height/2;

        rotate.rotate(Math.PI / 2 * getRoomElement().getRotationRatio(), centerX, centerY);
        setRotate(rotate);

        g2d.transform(rotate);

        double poluprecnikH = (double) height / 2;
        double poluprecnikW = (double) width / 2;

        GeneralPath path = new GeneralPath();
//        if(((MyTabPanel) MainFrame.getInstance().getTabbedPane().getSelectedComponent()).getSelectionList().contains(this)){
//            g2d.setColor(Color.orange);
//        }
//        for(RoomElementPainter painter : ((MyTabPanel) MainFrame.getInstance().getTabbedPane().getSelectedComponent()).getSelectionList()){
//            if(painter.equals(this))
//                g2d.setColor(Color.orange);
//        }

        Ellipse2D ellipse2D = new Ellipse2D.Double(x, y, width, height);
        path.append(ellipse2D, true);

        path.moveTo(x + poluprecnikW / 2, y +  poluprecnikH / 2);
        path.lineTo(x + 3 * poluprecnikW / 2, y + 3 * poluprecnikH / 2);

        path.moveTo(x + 3 * poluprecnikW / 2, y + poluprecnikH / 2);
        path.lineTo(x + poluprecnikW / 2, y + 3 * poluprecnikH / 2);

        path.closePath();

        setShape(path);
        g2d.draw(getShape());

        Shape updatedBounds = getShape();
        setRotatedBounds(rotate.createTransformedShape(updatedBounds));

        g2d.setTransform(start);

        int x1 = getRotatedBounds().getBounds().x;
        int y1 = getRotatedBounds().getBounds().y;
        int width1 = (int) getRotatedBounds().getBounds().getWidth();
        int height1 = (int) getRotatedBounds().getBounds().getHeight();

        Ellipse2D  ellipse2D1 = new Ellipse2D.Double(x1, y1, width1, height1);

        Line2D diagonal1 = new Line2D.Double(x1, y1, x1 + width1, y1 + height1);
        Line2D diagonal2 = new Line2D.Double(x1 + width1, y1, x1, y1+height1);

        GeometryUtils.setResizeEllipse(getRoomElement(), diagonal1, diagonal2, ellipse2D1, g2d);
    }

    @Override
    public void paint(Graphics2D g, RoomElement object, int width, int height) {
    }

}
