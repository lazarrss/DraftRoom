package raf.draft.dsw.utils;

import raf.draft.dsw.controller.organizer.RoomOrganizer;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.gui.swing.view.my.MyRoomOrganizer;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;
import raf.draft.dsw.gui.swing.view.painters.RoomElementPainter;
import raf.draft.dsw.gui.swing.view.painters.RoomElementPainterFactory;
import raf.draft.dsw.model.messages.MessageType;
import raf.draft.dsw.model.structures.roomelements.RoomElement;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DialogUtils {
    public static void showDialog(MyTabPanel roomView, RoomElement edit) {

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(roomView), "Edit Element Properties", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new GridLayout(5, 2, 5, 5));

        JLabel rotationLabel = new JLabel("Rotation Ratio:");
        JTextField rotationField = new JTextField(String.valueOf(edit.getRotationRatio()));

        JLabel locationLabel = new JLabel("Point (x, y):");
        JTextField locationField = new JTextField(edit.getLocation().x + ", " + edit.getLocation().y);

        JLabel widthLabel = new JLabel("Element Width:");
        JTextField widthField = new JTextField(String.valueOf(edit.getWidth()));

        JLabel heightLabel = new JLabel("Element Height:");
        JTextField heightField = new JTextField(String.valueOf(edit.getHeight()));

        JButton saveButton = new JButton("Update");
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            dialog.dispose();
        });
        saveButton.addActionListener(e -> {
            if (!rotationField.getText().matches("\\d+")) {
                ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Rotation must be a number", MessageType.ERROR);
                return;
            }

            if (!locationField.getText().matches("\\d+\\s*,\\s*\\d+")) {
                ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Location must be in the format 'x, y' where x and y are numbers greater than 0", MessageType.ERROR);
                return;
            }

            if (!widthField.getText().matches("\\d+")) {
                ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Width must be a number", MessageType.ERROR);
                return;
            }

            if (!heightField.getText().matches("\\d+")) {
                ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Height must be a number", MessageType.ERROR);
                return;
            }
            int rotationRatio = Integer.parseInt(rotationField.getText());

            String[] LocationValues = locationField.getText().split(",");
            Point location = new Point(
                    Integer.parseInt(LocationValues[0].trim()),
                    Integer.parseInt(LocationValues[1].trim())
            );

            int newWidth = Integer.parseInt(widthField.getText());
            int newHeight = Integer.parseInt(heightField.getText());

            RoomElementPainter painter = RoomElementPainterFactory.getPainter(edit);
            if(GeometryUtils.overlaps(painter, roomView.getPainterList())){
                ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Shapes overlap", MessageType.ERROR);
                return;
            }

            edit.setWidth(newWidth);
            edit.setHeight(newHeight);
            edit.setLocation(location);
            edit.setRotationRatio(rotationRatio);

            dialog.dispose();

        });
        dialog.add(widthLabel);
        dialog.add(widthField);
        dialog.add(heightLabel);
        dialog.add(heightField);
        dialog.add(rotationLabel);
        dialog.add(rotationField);
        dialog.add(locationLabel);
        dialog.add(locationField);
        dialog.add(saveButton);
        dialog.add(cancelButton);

        dialog.setLocationRelativeTo(roomView);
        dialog.setVisible(true);
    }

    public static void showPatternDialog() {
        int response = JOptionPane.showConfirmDialog(null, "Auto organize?",
                "Organize My Room", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
            MainFrame.getInstance().getOrganizer().init();
        }
    }
}
