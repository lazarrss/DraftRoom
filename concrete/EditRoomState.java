package raf.draft.dsw.controller.state.concrete;

import raf.draft.dsw.controller.state.State;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;
import raf.draft.dsw.model.messages.MessageType;
import raf.draft.dsw.utils.DialogUtils;

import javax.swing.*;
import java.awt.*;

public class EditRoomState implements State {
    @Override
    public void misKliknut(MyTabPanel roomView, Point point) throws InterruptedException {
        if(!roomView.getRoom().getChildren().isEmpty())
            return;
        JTextField height = new JTextField();
        JTextField width = new JTextField();
        Object[] message = {
                "Height: ", height,
                "Width: ", width
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Room attributes", JOptionPane.OK_CANCEL_OPTION);
        if(option == -1 || option == 2)
            return;
        if (!height.getText().matches("\\d+") || !width.getText().matches("\\d+")) {
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("You cannot enter text", MessageType.ERROR);
            return;
        }
        int heightInt = Integer.parseInt(height.getText());
        int widthInt = Integer.parseInt(width.getText());

        if(heightInt < 20 || widthInt < 20){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Dimensions must be bigger than 20cm", MessageType.ERROR);
            return;
        }

        roomView.getRoom().setRoomHeight(heightInt);
        roomView.getRoom().setRoomWidth(widthInt);

        double panelWidth = roomView.myGetWidth() - 20; //100
        double panelHeight = roomView.myGetHeight() - 30; //100
        double roomHeight = (roomView.getRoom().getRoomHeight()); //50
        double roomWidth = (roomView.getRoom().getRoomWidth()); //50

        double odnosW = panelWidth / roomWidth; // 418 / 2000
        double odnosH = panelHeight / roomHeight; // 291 / 22000

        roomView.setScalingFactor(Math.min(odnosW, odnosH));

        Thread.sleep(150);
        DialogUtils.showPatternDialog();

        roomView.repaint();
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
