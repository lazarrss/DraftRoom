package raf.draft.dsw.controller.listeners;

import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;

import java.awt.event.*;
import java.awt.geom.NoninvertibleTransformException;

public class MouseRoomListener implements MouseListener, MouseMotionListener, MouseWheelListener {
    private MyTabPanel roomView;

    public MouseRoomListener(MyTabPanel roomView) {
        this.roomView = roomView;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            MainFrame.getInstance().getTabbedPane().misKliknut(roomView, e.getPoint());
        } catch (NoninvertibleTransformException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        try {
            MainFrame.getInstance().getTabbedPane().misPritisnut(roomView, e.getPoint());
        } catch (NoninvertibleTransformException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        try {
            MainFrame.getInstance().getTabbedPane().misPusten(roomView, e.getPoint());
        } catch (NoninvertibleTransformException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        try {
            MainFrame.getInstance().getTabbedPane().misVuce(roomView, e.getPoint());
        } catch (NoninvertibleTransformException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
       // if (e.isControlDown()) {
            if (e.getWheelRotation() < 0) {
                System.out.println("up");
                MainFrame.getInstance().getTabbedPane().misSkrolGore(roomView, e.getPoint());
            } else {
                System.out.println("down");
                MainFrame.getInstance().getTabbedPane().misSkrolDole(roomView, e.getPoint());
            }
        //}
    }
}
