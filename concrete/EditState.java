package raf.draft.dsw.controller.state.concrete;

import raf.draft.dsw.controller.state.State;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.utils.DialogUtils;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;
import raf.draft.dsw.gui.swing.view.painters.RoomElementPainter;
import raf.draft.dsw.model.structures.roomelements.RoomElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

public class EditState implements State {
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
        RoomElement edit = null;
        for(RoomElementPainter painter : roomView.getPainterList()){
            if(painter.elementAt(new Point((int) realPoint.getX(), (int) realPoint.getY()))){
                edit = painter.getRoomElement();
                break;
            }
        }
        if(edit == null) return;
        ApplicationFramework.getInstance().getProjectController().setOnChanged(edit);
        DialogUtils.showDialog(roomView, edit);
    }
    @Override
    public void misPritisnut(MyTabPanel roomView, Point point) {

    }

    @Override
    public void misPusten(MyTabPanel roomView, Point point) {

    }

    @Override
    public void misUsao(MyTabPanel roomView, Point point) {

    }

    @Override
    public void misIzasao(MyTabPanel roomView, Point point) {

    }

    @Override
    public void misVuce(MyTabPanel roomView, Point point) {

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
