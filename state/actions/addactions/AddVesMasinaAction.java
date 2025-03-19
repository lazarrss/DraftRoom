package raf.draft.dsw.controller.state.actions.addactions;

import raf.draft.dsw.controller.AbstractRoomAction;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;
import raf.draft.dsw.model.messages.MessageType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class AddVesMasinaAction extends AbstractRoomAction {
    public AddVesMasinaAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F2, InputEvent.CTRL_DOWN_MASK));
        putValue(SMALL_ICON, loadIcon("/images/roomElements/washingMachine.png"));
        putValue(NAME, "Washing Machine");
        putValue(SHORT_DESCRIPTION, "Add washing machine");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MyTabPanel roomView = (MyTabPanel) MainFrame.getInstance().getTabbedPane().getSelectedComponent();

        if(roomView.getRoom().getRoomWidth() == 0 ||
                roomView.getRoom().getRoomHeight() == 0)
            return;

        MainFrame.getInstance().getTabbedPane().startAddVesMasinaState();

        JTextField height = new JTextField();
        JTextField width = new JTextField();
        Object[] message = {
                "Height: ", height,
                "Width: ", width
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Room attributes", JOptionPane.OK_CANCEL_OPTION);
        if(option == -1 || option == 2)
            return;
        if(height.getText().isBlank() || width.getText().isBlank() || height.getText().isEmpty() || width.getText().isEmpty()) {
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Enter both parameters", MessageType.ERROR);
            return;
        }
        if (!height.getText().matches("\\d+") || !width.getText().matches("\\d+")) {
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("You cannot enter text", MessageType.ERROR);
            return;
        }
        int heightInt = Integer.parseInt(height.getText());
        int widthInt = Integer.parseInt(width.getText());

        if(heightInt > roomView.getRoom().getRoomHeight() || widthInt > roomView.getRoom().getRoomWidth()){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Element dimensions are bigger than room dimensions", MessageType.ERROR);
            return;
        }

        MainFrame.getInstance().getTabbedPane().setCurrentHeightCM(heightInt);
        MainFrame.getInstance().getTabbedPane().setCurrentWidthCM(widthInt);
    }
}
