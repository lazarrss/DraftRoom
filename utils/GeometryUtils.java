package raf.draft.dsw.utils;

import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;
import raf.draft.dsw.gui.swing.view.painters.RoomElementPainter;
import raf.draft.dsw.model.structures.roomelements.RoomElement;

import java.awt.*;
import java.awt.geom.*;
import java.util.List;

public class GeometryUtils {
    public static void setResizeRectangle(Graphics2D g, Rectangle rectangle, RoomElement roomElement) {
        Point intersection = new Point((int) (rectangle.getLocation().x + rectangle.getWidth() ), (int) (rectangle.getLocation().y + rectangle.getHeight() ));
        roomElement.setBottomRightPoint(intersection);
    }
    public static void setResizeEllipse(RoomElement roomElement, Line2D diagonal1, Line2D diagonal2, Ellipse2D ellipse2D, Graphics2D g){
        Point2D intersection = GeometryUtils.findIntersection(diagonal1, ellipse2D, roomElement);
        Point resizePoint = new Point((int) intersection.getX() , (int) intersection.getY() );
        roomElement.setBottomRightPoint(resizePoint);
    }
    public static Point2D findIntersection(Line2D line, Ellipse2D ellipse, RoomElement roomElement) {
        double cx = ellipse.getCenterX();
        double cy = ellipse.getCenterY();
        double rx = ellipse.getWidth() / 2;
        double ry = ellipse.getHeight() / 2;

        double x1 = line.getX1(), y1 = line.getY1();
        double x2 = line.getX2(), y2 = line.getY2();

        double dx = x2 - x1, dy = y2 - y1;
        double a = (dx * dx) / (rx * rx) + (dy * dy) / (ry * ry);
        double b = 2 * (dx * (x1 - cx) / (rx * rx) + dy * (y1 - cy) / (ry * ry));
        double c = ((x1 - cx) * (x1 - cx)) / (rx * rx) + ((y1 - cy) * (y1 - cy)) / (ry * ry) - 1;

        double discriminant = b * b - 4 * a * c;
        if (discriminant < 0) {
            return null;
        }

        double sqrtDiscriminant = Math.sqrt(discriminant);

        double t1 = (-b + sqrtDiscriminant) / (2 * a);
        double t2 = (b + sqrtDiscriminant) / (2 * a);

         return new Point2D.Double(x2+t2*dx, y2+t2*dy);
    }

    public static boolean overlaps(RoomElementPainter target, List<RoomElementPainter> allElements) {
        for (RoomElementPainter painter : allElements) {
            if (painter != target) {
                if((target.getRoomElement().getRotationRatio() % 2 == 0)) {
                    if (shapesOverlap(target.getShape(), painter.getShape())) {
                        return true;
                    }
                }else{
                    if (shapesOverlap(target.getRotatedBounds(), painter.getRotatedBounds())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static boolean shapesOverlap(Shape shape1, Shape shape2) {
        Area area1 = new Area(shape1);
        Area area2 = new Area(shape2);

        area1.intersect(area2);
        return !area1.isEmpty();
    }
}
