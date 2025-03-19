package raf.draft.dsw.controller.state.concrete;

import raf.draft.dsw.controller.command.AbstractCommand;
import raf.draft.dsw.controller.command.concrete.MoveCommand;
import raf.draft.dsw.controller.state.State;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.utils.GeometryUtils;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;
import raf.draft.dsw.gui.swing.view.painters.RoomElementPainter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MoveState implements State {
    @Override
    public void misKliknut(MyTabPanel roomView, Point point) {
    }

    @Override
    public void misPritisnut(MyTabPanel roomView, Point point) throws NoninvertibleTransformException {
        boolean ok = false;
        int pomerajx = 0;
        int pomerajy = 0;
        if (roomView.getDragStartPoint() != null && roomView.getDragEndPoint() != null) {
            pomerajx = point.x - roomView.getDragEndPoint().x;
            pomerajy = point.y - roomView.getDragEndPoint().y;

            Point accumulatedOffset = roomView.getOffSet();
            accumulatedOffset.translate(pomerajx, pomerajy);
            roomView.setOffSet(accumulatedOffset);
        }
        AffineTransform currentTransform = new AffineTransform();
        if (roomView.getZoomPoint() != null) {
            double zoomFactor = roomView.getZoomFactor();
            currentTransform.translate(roomView.getZoomPoint().x, roomView.getZoomPoint().y);
            currentTransform.scale(zoomFactor, zoomFactor);
            currentTransform.translate(-roomView.getZoomPoint().x, -roomView.getZoomPoint().y);
        }
        currentTransform.translate(roomView.getOffSet().x, roomView.getOffSet().y);
        Point2D realPoint = currentTransform.inverseTransform(point, null);
        Point realP = new Point((int) realPoint.getX(), (int) realPoint.getY());

        for (RoomElementPainter painter : roomView.getSelectionList()) {
            if ((painter.getRoomElement().getRotationRatio() % 2 == 0 && painter.elementAt(realP))
                    || (painter.getRoomElement().getRotationRatio() % 2 != 0 && painter.getRotatedBounds().intersects(new Rectangle(realP.x, realP.y, 1, 1)))) {
                ok = true;
            }
            painter.getRoomElement().setMovePoint(new Point((int) (realPoint.getX() - painter.getRoomElement().getLocation().x), (int) (realPoint.getY() - painter.getRoomElement().getLocation().y)));
            painter.getRoomElement().setStartPoint(painter.getRoomElement().getLocation());
        }
        if (!ok) {
            if (!roomView.getSelectionList().isEmpty()) {
                roomView.getSelectionList().clear();
                roomView.repaint();
            }
            for (RoomElementPainter painter : roomView.getPainterList()) {
                if ((painter.getRoomElement().getRotationRatio() % 2 == 0 && painter.elementAt(realP))
                        || (painter.getRoomElement().getRotationRatio() % 2 != 0 && painter.getRotatedBounds().intersects(new Rectangle(realP.x, realP.y, 1, 1)))) {
                    roomView.getSelectionList().add(painter);
                    painter.getRoomElement().setMovePoint(new Point((int) (realPoint.getX() - painter.getRoomElement().getLocation().x), (int) (realPoint.getY() - painter.getRoomElement().getLocation().y)));
                    painter.getRoomElement().setStartPoint(painter.getRoomElement().getLocation());
                    break;
                }
            }
        }
        if (roomView.getSelectionList().isEmpty()) {
            roomView.setDragStartPoint(point);
            roomView.setDragEndPoint(point);
            roomView.repaint();
        }
    }

    @Override
    public void misPusten(MyTabPanel roomView, Point point) throws NoninvertibleTransformException {
        if (roomView.getSelectionList().isEmpty()) {
            roomView.setDragStartPoint(null);
            roomView.setDragEndPoint(null);
            roomView.repaint();
            return;
        }

        AffineTransform transform = new AffineTransform();

        if (roomView.getZoomPoint() != null) {
            double zoomFactor = roomView.getZoomFactor();
            transform.translate(roomView.getZoomPoint().x, roomView.getZoomPoint().y);
            transform.scale(zoomFactor, zoomFactor);
            transform.translate(-roomView.getZoomPoint().x, -roomView.getZoomPoint().y);
        }
        transform.translate(roomView.getOffSet().x, roomView.getOffSet().y);

        List<Point> pointList = new ArrayList<>();

        Point2D realPoint = transform.inverseTransform(point, null);
        for (RoomElementPainter painter : roomView.getSelectionList()) {
            transform.setToTranslation(0, 0);

            Point2D transformedPoint = transform.transform(new Point((int) realPoint.getX(), (int) realPoint.getY()), null);
            Point newPoint = new Point((int) transformedPoint.getX(), (int) transformedPoint.getY());

            int newX = newPoint.x - painter.getRoomElement().getMovePoint().x;
            int newY = newPoint.y - painter.getRoomElement().getMovePoint().y;

            int checkWidth = (int) (roomView.getRoom().getRoomWidth() * roomView.getScalingFactor());
            int checkHeight = (int) (roomView.getRoom().getRoomHeight() * roomView.getScalingFactor());

            int elementWidth = (int) (painter.getRoomElement().getWidth() * roomView.getScalingFactor());
            int elementHeight = (int) (painter.getRoomElement().getHeight() * roomView.getScalingFactor());

            if (painter.getRoomElement().getRotationRatio() % 2 == 0) {
                if (newX < 20) newX = 10;
                if (newY < 30) newY = 15;
                if (newX + elementWidth > checkWidth)
                    newX = (int) (((checkWidth + 10) - elementWidth));
                if (newY + elementHeight > checkHeight)
                    newY = (int) (((checkHeight + 15) - elementHeight));
            } else {
                if (newX + (elementWidth - elementHeight) / 2 < 20) newX = 10 - (elementWidth - elementHeight) / 2;
                if (newY - (elementWidth - elementHeight) / 2 < 30) newY = 15 + (elementWidth - elementHeight) / 2;
                if (newX + (elementWidth - elementHeight) / 2 + elementHeight > checkWidth)
                    newX = (int) (((checkWidth + 10) - elementHeight) - (elementWidth - elementHeight) / 2);
                if (newY - (elementWidth - elementHeight) / 2 + elementWidth > checkHeight)
                    newY = (int) (((checkHeight + 15) - elementWidth) + (elementWidth - elementHeight) / 2);
            }

            if (GeometryUtils.overlaps(painter, roomView.getPainterList())) {
                painter.getRoomElement().setLocation(painter.getRoomElement().getStartPoint());
                pointList.add(new Point(painter.getRoomElement().getStartPoint()));
            }else {
                painter.getRoomElement().setLocation(new Point(newX, newY));
                pointList.add(new Point(newX, newY));
            }
        }


        AbstractCommand abstractCommand = new MoveCommand(roomView, pointList);
        roomView.getCommandManager().addCommand(abstractCommand);
    }

    @Override
    public void misUsao(MyTabPanel roomView, Point point) {
    }

    @Override
    public void misIzasao(MyTabPanel roomView, Point point) {
    }

    @Override
    public void misVuce(MyTabPanel roomView, Point point) throws NoninvertibleTransformException {
        AffineTransform currentTransform = new AffineTransform();
        int pomerajX = 0, pomerajY = 0;
        if (roomView.getDragStartPoint() != null && roomView.getDragEndPoint() != null) {
            pomerajX = point.x - roomView.getDragEndPoint().x;
            pomerajY = point.y - roomView.getDragEndPoint().y;

            Point accumulatedOffset = roomView.getOffSet();
            accumulatedOffset.translate(pomerajX, pomerajY);
            roomView.setOffSet(accumulatedOffset);
        }
        if (roomView.getZoomPoint() != null) {
            double zoomFactor = roomView.getZoomFactor();
            currentTransform.translate(roomView.getZoomPoint().x, roomView.getZoomPoint().y);
            currentTransform.scale(zoomFactor, zoomFactor);
            currentTransform.translate(-roomView.getZoomPoint().x, -roomView.getZoomPoint().y);
        }
        currentTransform.translate(roomView.getOffSet().x, roomView.getOffSet().y);
        Point2D realPoint = currentTransform.inverseTransform(point, null);

        for (RoomElementPainter painter : roomView.getSelectionList()) {
            currentTransform.setToTranslation(0, 0);

            Point2D transformedPoint = currentTransform.transform(new Point((int) realPoint.getX(), (int) realPoint.getY()), null);
            Point newPoint = new Point((int) transformedPoint.getX(), (int) transformedPoint.getY());

            int newX = newPoint.x - painter.getRoomElement().getMovePoint().x;
            int newY = newPoint.y - painter.getRoomElement().getMovePoint().y;

            int checkWidth = (int) (roomView.getRoom().getRoomWidth() * roomView.getScalingFactor());
            int checkHeight = (int) (roomView.getRoom().getRoomHeight() * roomView.getScalingFactor());

            int elementWidth = (int) (painter.getRoomElement().getWidth() * roomView.getScalingFactor());
            int elementHeight = (int) (painter.getRoomElement().getHeight() * roomView.getScalingFactor());

            if(painter.getRoomElement().getRotationRatio() % 2 == 0){
                if(newX < 20) newX = 10;
                if(newY < 30) newY = 15;
                if (newX + elementWidth > checkWidth)
                    newX = (int) (((checkWidth + 10) - elementWidth));
                if (newY + elementHeight > checkHeight)
                    newY = (int) (((checkHeight + 15) - elementHeight));
            }else {
                if(newX + (elementWidth-elementHeight) / 2 < 20) newX = 10 - (elementWidth-elementHeight) / 2;
                if(newY - (elementWidth-elementHeight) / 2 < 30) newY = 15 + (elementWidth-elementHeight) / 2;
                if (newX + (elementWidth-elementHeight) / 2 + elementHeight > checkWidth)
                    newX = (int) (((checkWidth + 10) - elementHeight) - (elementWidth-elementHeight) / 2);
                if (newY - (elementWidth-elementHeight) / 2  + elementWidth > checkHeight)
                    newY = (int) (((checkHeight + 15) - elementWidth) + (elementWidth-elementHeight) / 2);
            }
            painter.getRoomElement().setLocation(new Point(newX, newY));
        }
        if(roomView.getSelectionList().isEmpty()){
            roomView.setDragEndPoint(point);
            roomView.repaint();
        }
    }
    @Override
    public void misPomeren(MyTabPanel roomView, Point point) {
    }

    @Override
    public void triggerOnAction(MyTabPanel roomView, Point point) {
    }

    @Override
    public void misSkrolGore(MyTabPanel roomView, Point point) {
    }

    @Override
    public void misSkrolDole(MyTabPanel roomView, Point point) {
    }
}
