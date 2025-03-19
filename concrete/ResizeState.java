package raf.draft.dsw.controller.state.concrete;

import raf.draft.dsw.controller.command.AbstractCommand;
import raf.draft.dsw.controller.command.concrete.ResizeCommand;
import raf.draft.dsw.controller.command.concrete.RotateCommand;
import raf.draft.dsw.controller.state.State;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;
import raf.draft.dsw.gui.swing.view.painters.RoomElementPainter;
import raf.draft.dsw.gui.swing.view.painters.concrete.BojlerPainter;
import raf.draft.dsw.gui.swing.view.painters.concrete.LavaboPainter;
import raf.draft.dsw.gui.swing.view.painters.concrete.VrataPainter;
import raf.draft.dsw.gui.swing.view.painters.concrete.WCSoljaPainter;
import raf.draft.dsw.utils.GeometryUtils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

public class ResizeState implements State {

    @Override
    public void misKliknut(MyTabPanel roomView, Point point) {

    }
    @Override
    public void misPritisnut(MyTabPanel roomView, Point point) throws NoninvertibleTransformException {
        AffineTransform currentTransform = new AffineTransform();

        if (roomView.getZoomPoint() != null) {
            double zoomFactor = roomView.getZoomFactor();
            currentTransform.translate(roomView.getZoomPoint().x, roomView.getZoomPoint().y);
            currentTransform.scale(zoomFactor, zoomFactor);
            currentTransform.translate(-roomView.getZoomPoint().x, -roomView.getZoomPoint().y);
        }
        currentTransform.translate(roomView.getOffSet().x, roomView.getOffSet().y);

        Point2D realPoint = currentTransform.inverseTransform(point, null);
        Rectangle hitbox = new Rectangle((int) realPoint.getX() - 10, (int) realPoint.getY() - 10, 20, 20);

        for (RoomElementPainter painter : roomView.getPainterList()) {
//            Point2D bottomRightPoint = currentTransform.inverseTransform(painter.getRoomElement().getBottomRightPoint(), null);
            Point bottomRightPoint = painter.getRoomElement().getBottomRightPoint();
            Rectangle painterHitbox = new Rectangle((int) (bottomRightPoint.getX() - 10), (int) (bottomRightPoint.getY() - 10),20, 20);

            if (painterHitbox.intersects(hitbox)) {
                roomView.setResizeElement(painter);

                Dimension dim = new Dimension(painter.getRoomElement().getWidth(), painter.getRoomElement().getHeight());
                roomView.setResizeUndo(dim); // nbtn
                AbstractCommand abstractCommand = new ResizeCommand(dim, painter.getRoomElement());
                roomView.getCommandManager().addCommand(abstractCommand);
                break;
            }
        }
    }

    @Override
    public void misPusten(MyTabPanel roomView, Point point) {
        roomView.getSelectionList().clear();
        roomView.setResizeElement(null);
        roomView.repaint();
    }

    @Override
    public void misUsao(MyTabPanel roomView, Point point) {

    }

    @Override
    public void misIzasao(MyTabPanel roomView, Point point) {

    }

    @Override
    public void misVuce(MyTabPanel roomView, Point point) throws NoninvertibleTransformException {
        if (roomView.getResizeElement() != null) {
            AffineTransform currentTransform = new AffineTransform();

            if (roomView.getZoomPoint() != null) {
                double zoomFactor = roomView.getZoomFactor();
                currentTransform.translate(roomView.getZoomPoint().x, roomView.getZoomPoint().y);
                currentTransform.scale(zoomFactor, zoomFactor);
                currentTransform.translate(-roomView.getZoomPoint().x, -roomView.getZoomPoint().y);
            }

            currentTransform.translate(roomView.getOffSet().x, roomView.getOffSet().y);
            Point2D realPoint = currentTransform.inverseTransform(point, null);

            Rectangle bounds = roomView.getResizeElement().getShape().getBounds();
            double resizeWidth = realPoint.getX() - bounds.x;
            double resizeHeight = realPoint.getY() - bounds.y;

            if (roomView.getResizeElement().getRoomElement().getRotationRatio() % 2 != 0) {
                resizeWidth = realPoint.getY() - bounds.y;
                resizeHeight = realPoint.getX() - bounds.x;
            }

            double scale = roomView.getScalingFactor();

            resizeWidth /= scale;
            resizeHeight /= scale;

            int newWidth = (int) Math.max(10, resizeWidth);
            int newHeight = (int) Math.max(10, resizeHeight);

            roomView.getResizeElement().getRoomElement().setWidth(newWidth);
            roomView.getResizeElement().getRoomElement().setHeight(newHeight);
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
