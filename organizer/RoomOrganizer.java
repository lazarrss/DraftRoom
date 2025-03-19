package raf.draft.dsw.controller.organizer;

import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.gui.swing.view.my.MyRoomOrganizer;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;
import raf.draft.dsw.gui.swing.view.painters.RoomElementPainter;
import raf.draft.dsw.gui.swing.view.painters.RoomElementPainterFactory;
import raf.draft.dsw.model.messages.MessageType;
import raf.draft.dsw.model.nodefactory.RoomElementFactory;
import raf.draft.dsw.model.structures.Room;
import raf.draft.dsw.model.structures.roomelements.RoomElement;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoomOrganizer {
    private MyRoomOrganizer myRoomOrganizer;
    private MyTabPanel curr;
    private List<RoomElement> roomElementLIst;

    public RoomOrganizer() {
    }

    public void init() {
        curr = (MyTabPanel) MainFrame.getInstance().getTabbedPane().getSelectedComponent();
        myRoomOrganizer = new MyRoomOrganizer();
        initActionListeners();
        myRoomOrganizer.show();
    }

    public void initActionListeners() {
        myRoomOrganizer.getAddButton().addActionListener(e -> {
            String selectedItem = myRoomOrganizer.getAvailableList().getSelectedValue();
            String width = myRoomOrganizer.getWidthField().getText();
            String height = myRoomOrganizer.getHeightField().getText();

            if (selectedItem == null) {
                ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Choose an item from the list", MessageType.ERROR);
                return;
            }

            if (width.isEmpty() || height.isEmpty()) {
                ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Enter width and height", MessageType.ERROR);
                return;
            }

            try {
                int w = Integer.parseInt(width);
                int h = Integer.parseInt(height);

                if(w > ((MyTabPanel)MainFrame.getInstance().getTabbedPane().getSelectedComponent()).getRoom().getRoomWidth() ||
                    h > ((MyTabPanel)MainFrame.getInstance().getTabbedPane().getSelectedComponent()).getRoom().getRoomHeight()){
                    ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Element dimensions must be smaller than rooms", MessageType.ERROR);
                    return;
                }

                String element = String.format("%s (%dx%d)", selectedItem, w, h);
                myRoomOrganizer.getSelectedModel().addElement(element);
                myRoomOrganizer.getWidthField().setText("");
                myRoomOrganizer.getHeightField().setText("");

            } catch (NumberFormatException ex) {
                ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Enter numbers please", MessageType.ERROR);
            }
        });

        myRoomOrganizer.getRemoveButton().addActionListener(e -> {
            int selectedIndex = myRoomOrganizer.getSelectedList().getSelectedIndex();
            if (selectedIndex != -1) {
                String removedItem = myRoomOrganizer.getSelectedModel().getElementAt(selectedIndex);
                myRoomOrganizer.getSelectedModel().remove(selectedIndex);
            } else {
                ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Please choose an item from the list", MessageType.ERROR);
            }
        });

        myRoomOrganizer.getEnterButton().addActionListener(e -> {
            if (myRoomOrganizer.getSelectedModel().isEmpty()) {
                return;
            }
            List<RoomElement> roomElementList = new ArrayList<>();
            for (int i = 0; i < myRoomOrganizer.getSelectedModel().size(); i++) {
                String[] parts = myRoomOrganizer.getSelectedModel().getElementAt(i).split(" \\(");

                String name = parts[0].replace(" ", "");
                name = Character.toUpperCase(name.charAt(0)) + name.substring(1);

                //dimenzija (30x20)
                String dimensions = parts[1].replace(")", "");
                String[] dimensionParts = dimensions.split("x");

                int width = Integer.parseInt(dimensionParts[0]);
                int height = Integer.parseInt(dimensionParts[1]);
                RoomElement roomElement = RoomElementFactory.getRoomElement(name, width, height);
                RoomElementPainter painter = null;
                if(roomElement != null) {
                    painter = RoomElementPainterFactory.getPainter(roomElement);
                    roomElementList.add(roomElement);
                    roomElement.addSubscriber(curr);
                    MainFrame.getInstance().getDraftTree().addChild(curr.getRoom(), roomElement);
                }
                else return;
                curr.getPainterList().add(painter);

                myRoomOrganizer.getFrame().dispose();
            }
            this.roomElementLIst = roomElementList;
            arrangeRoom(roomElementList, curr);
        });
    }
    public void arrangeRoom(List<RoomElement> elements, MyTabPanel curr) {
        int maxWidth = 1;
        int maxHeight = 1;

        for (RoomElement element : elements) {
            if (element.getWidth() > maxWidth) {
                maxWidth = element.getWidth();
            }
            if (element.getHeight() > maxHeight) {
                maxHeight = element.getHeight();
            }
        }
        int cellWidth = maxWidth;
        int cellHeight = maxHeight;

        int rows = (int) (curr.getRoom().getRoomHeight() / cellHeight);
        int cols = (int) (curr.getRoom().getRoomWidth() / cellWidth);

        System.out.println("rows "+rows);
        System.out.println("cols "+cols);

        if (rows * cols < elements.size()) {
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Not enough space", MessageType.ERROR);
            return;
        }

        Collections.shuffle(elements);

        fillMatrixSpirally(elements, curr, cellWidth, cellHeight, rows, cols);
    }
    private void fillMatrixSpirally(List<RoomElement> elements, MyTabPanel curr, int cellWidth, int cellHeight, int rows, int cols) {
        double scalingFactor = curr.getScalingFactor();
        int top = 0, bottom = rows - 1;
        int left = 0, right = cols - 1;
        int elementIndex = 0;

        while (top <= bottom && left <= right && elementIndex < elements.size()) {
            for (int i = left; i <= right && elementIndex < elements.size(); i++) {
                placeElementRight(elements.get(elementIndex++), curr, i, top, cellWidth, cellHeight, scalingFactor, rows, cols);
            }
            top++;

            for (int i = top; i <= bottom && elementIndex < elements.size(); i++) {
                placeElementDown(elements.get(elementIndex++), curr, i, right, cellWidth, cellHeight, scalingFactor, rows, cols);
            }
            right--;

            for (int i = right; i >= left && elementIndex < elements.size(); i--) {
                placeElementLeft(elements.get(elementIndex++), curr, i, bottom, cellWidth, cellHeight, scalingFactor, rows, cols);
            }
            bottom--;

            for (int i = bottom; i >= top && elementIndex < elements.size(); i--) {
                placeElementUp(elements.get(elementIndex++), curr, left, i, cellWidth, cellHeight, scalingFactor, rows, cols);
            }
            left++;
        }
        curr.repaint();
    }
    private void placeElementRight(RoomElement element, MyTabPanel curr, int counter, int col, int cellWidth, int cellHeight, double scalingFactor, int rows, int cols) {
        int roomWidth = (int) (curr.getRoom().getRoomWidth() * scalingFactor);

        int x = (int) (counter * cellWidth * scalingFactor) + 10;
        int y = (int) (col * cellHeight * scalingFactor) + 15;

        element.setLocation(new Point(x, y));

        if(counter == cols-1) {
            int translateWidth = (int) (roomWidth + 10 - (x + element.getWidth() * scalingFactor));
            int xLoc = element.getLocation().x;
            xLoc += translateWidth;
            int yLoc = 15;
            element.setLocation(new Point(xLoc, yLoc));
        }
    }
    private void placeElementDown(RoomElement element, MyTabPanel curr, int counter, int row, int cellWidth, int cellHeight, double scalingFactor, int rows, int cols) {
        int roomWidth = (int) (curr.getRoom().getRoomWidth() * scalingFactor);
        int roomHeight = (int) (curr.getRoom().getRoomHeight() * scalingFactor);

        int x = (int) (rows * cellWidth * scalingFactor) + 10;
        int y = (int) (counter * cellHeight * scalingFactor) + 15;

        element.setLocation(new Point(x, y));

        int translateWidth = (int) (roomWidth + 10 - (x + element.getWidth() * scalingFactor));
        int xLoc = element.getLocation().x;
        xLoc += translateWidth;
        if(counter == rows-1){
            int translateHeight = (int) (roomHeight + 15 - (y + element.getHeight() * scalingFactor));
            y += translateHeight;
        }

        element.setLocation(new Point(xLoc, y));
    }
    private void placeElementLeft(RoomElement element, MyTabPanel curr, int counter, int col, int cellWidth, int cellHeight, double scalingFactor, int rows, int cols) {
        int x = (int) (counter * cellWidth * scalingFactor + 10);
        int y = (int) (col * cellHeight * scalingFactor + 15);

        int roomHeight = (int) (curr.getRoom().getRoomHeight() * scalingFactor);

//        int translateHeight = cellHeight - element.getHeight();
//        translateHeight *= (int) scalingFactor;
//        y+=translateHeight;

        int translateHeight = (int) ((roomHeight + 15) - (y + element.getHeight() * scalingFactor));
        y+=translateHeight;

        element.setLocation(new Point(x, y));

    }
    private void placeElementUp(RoomElement element, MyTabPanel curr, int row, int col, int cellWidth, int cellHeight, double scalingFactor, int rows, int cols) {
        int x = (int) (row * cellWidth * scalingFactor) + 10;
        int y = (int) (col * cellHeight * scalingFactor) + 15;

        element.setLocation(new Point(x, y));
    }
    public List<RoomElement> getRoomElementLIst() {
        return roomElementLIst;
    }

    public void setRoomElementLIst(List<RoomElement> roomElementLIst) {
        this.roomElementLIst = roomElementLIst;
    }
}
