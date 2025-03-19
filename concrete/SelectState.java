package raf.draft.dsw.controller.state.concrete;

import raf.draft.dsw.controller.state.State;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;
import raf.draft.dsw.gui.swing.view.painters.RoomElementPainter;
import raf.draft.dsw.gui.swing.view.painters.RoomElementPainterFactory;
import raf.draft.dsw.model.structures.roomelements.RoomElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

public class SelectState implements State {
    @Override
    public void misKliknut(MyTabPanel roomView, Point point) throws NoninvertibleTransformException {

        AffineTransform currentTransform = new AffineTransform();

        if (roomView.getZoomPoint() != null) {
            double zoomFactor = roomView.getZoomFactor();
            currentTransform.translate(roomView.getZoomPoint().x, roomView.getZoomPoint().y);
            currentTransform.scale(zoomFactor, zoomFactor);
            currentTransform.translate(-roomView.getZoomPoint().x, -roomView.getZoomPoint().y);
        }
        currentTransform.translate(roomView.getOffSet().x, roomView.getOffSet().y);

        Point2D realPoint = currentTransform.inverseTransform(point, null);
        for (RoomElementPainter painter : roomView.getPainterList()) {
            Rectangle rect = new Rectangle((int) realPoint.getX(), (int) realPoint.getY(), 4, 4);
            if ((painter.getRoomElement().getRotationRatio() % 2 == 0 && painter.elementAt(rect))
            ||(painter.getRoomElement().getRotationRatio() % 2 != 0 && painter.getRotatedBounds().intersects(rect))) {
                roomView.getSelectionList().add(painter);
                break;
            }
        }
         roomView.repaint();
    }
    @Override
    public void misPritisnut(MyTabPanel roomView, Point point) {
        roomView.setSelectionPointLT(point);
        roomView.setSelectionPointRB(null);
    }

    @Override
    public void misPusten(MyTabPanel roomView, Point point) throws NoninvertibleTransformException {
        roomView.setSelectionPointRB(point);

        AffineTransform currentTransform = new AffineTransform();

        if (roomView.getZoomPoint() != null) {
            double zoomFactor = roomView.getZoomFactor();
            currentTransform.translate(roomView.getZoomPoint().x, roomView.getZoomPoint().y);
            currentTransform.scale(zoomFactor, zoomFactor);
            currentTransform.translate(-roomView.getZoomPoint().x, -roomView.getZoomPoint().y);
        }

        currentTransform.translate(roomView.getOffSet().x, roomView.getOffSet().y);

        Point2D realPointRB = currentTransform.inverseTransform(point, null);
        Point2D realPointLT = currentTransform.inverseTransform(roomView.getSelectionPointLT(), null);

         Rectangle selectionRect = new Rectangle(
                Math.min((int) realPointLT.getX(), (int) realPointRB.getX()),
                Math.min((int) realPointLT.getY(), (int) realPointRB.getY()),
                Math.abs((int) realPointRB.getX() - (int) realPointLT.getX()),
                Math.abs((int) realPointRB.getY() - (int) realPointLT.getY()));

            roomView.getSelectionList().clear();
            for (RoomElementPainter painter : roomView.getPainterList()) {
                if ((painter.getRoomElement().getRotationRatio() % 2 == 0 && painter.elementAt(selectionRect))
                        ||(painter.getRoomElement().getRotationRatio() % 2 != 0 && painter.getRotatedBounds().intersects(selectionRect))) {
                    roomView.getSelectionList().add(painter);
                }
            }

        roomView.setSelectionPointLT(null);
        roomView.setSelectionPointRB(null);

        roomView.repaint();
    }

    @Override
    public void misUsao(MyTabPanel roomView, Point point) {

    }

    @Override
    public void misIzasao(MyTabPanel roomView, Point point) {

    }

    @Override
    public void misVuce(MyTabPanel roomView, Point point) {
        roomView.setSelectionPointRB(point);
        roomView.repaint();
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
