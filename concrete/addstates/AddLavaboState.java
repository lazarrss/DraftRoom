package raf.draft.dsw.controller.state.concrete.addstates;

import raf.draft.dsw.controller.command.AbstractCommand;
import raf.draft.dsw.controller.command.concrete.AddCommand;
import raf.draft.dsw.controller.state.State;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;
import raf.draft.dsw.gui.swing.view.painters.RoomElementPainter;
import raf.draft.dsw.gui.swing.view.painters.concrete.KadaPainter;
import raf.draft.dsw.gui.swing.view.painters.concrete.LavaboPainter;
import raf.draft.dsw.model.messages.MessageType;
import raf.draft.dsw.model.structures.roomelements.concrete.Kada;
import raf.draft.dsw.model.structures.roomelements.concrete.Krevet;
import raf.draft.dsw.model.structures.roomelements.concrete.Lavabo;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

public class AddLavaboState implements State {
    @Override
    public void misKliknut(MyTabPanel roomView, Point point1) throws NoninvertibleTransformException {
        int width = MainFrame.getInstance().getTabbedPane().getCurrentWidthCM();
        int height = MainFrame.getInstance().getTabbedPane().getCurrentHeightCM();

        double scale = roomView.getScalingFactor();
        AffineTransform currentTransform = new AffineTransform();

        if (roomView.getZoomPoint() != null) {
            double zoomFactor = roomView.getZoomFactor();
            currentTransform.translate(roomView.getZoomPoint().x, roomView.getZoomPoint().y);
            currentTransform.scale(zoomFactor, zoomFactor);
            currentTransform.translate(-roomView.getZoomPoint().x, -roomView.getZoomPoint().y);
        }
        currentTransform.translate(roomView.getOffSet().x, roomView.getOffSet().y);
        Point2D realPoint = currentTransform.inverseTransform(point1, null);

        Point point = new Point((int) realPoint.getX(), (int) realPoint.getY());

        for(RoomElementPainter painter : roomView.getPainterList()){
            if(painter.elementAt(new Rectangle(point.x, point.y, (int) (width * scale), (int) (height * scale)))){
                ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Your new element overlaps with an existing one", MessageType.ERROR);
                return;
            }
        }

        if(point.x < 10 || point.y < 15 || point.x + width * scale - 15> roomView.getRoom().getRoomWidth() * scale
                || point.y + height * scale - 10> roomView.getRoom().getRoomHeight() * scale){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("You cannot put element out of bounds", MessageType.ERROR);
            return;
        }

        Lavabo lavabo = new Lavabo("", roomView.getRoom(), point, width, height, 0);
        lavabo.addSubscriber(roomView);
        MainFrame.getInstance().getTabbedPane().setRoomElement(lavabo);

        LavaboPainter kp = new LavaboPainter(lavabo);

        AbstractCommand abstractCommand = new AddCommand(roomView, kp);
        roomView.getCommandManager().addCommand(abstractCommand);
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
