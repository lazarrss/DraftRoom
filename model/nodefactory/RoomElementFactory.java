package raf.draft.dsw.model.nodefactory;

import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;
import raf.draft.dsw.gui.swing.view.painters.concrete.*;
import raf.draft.dsw.model.structures.roomelements.RoomElement;
import raf.draft.dsw.model.structures.roomelements.concrete.*;

import java.awt.*;

public class RoomElementFactory {
    public static RoomElement getRoomElement(String name, int width, int height){
        MyTabPanel curr = (MyTabPanel) MainFrame.getInstance().getTabbedPane().getSelectedComponent();
        return switch (name) {
            case "Bojler" -> new Bojler("", curr.getRoom(), new Point(0,0), width, height, 0);
            case "Krevet" -> new Krevet("", curr.getRoom(), new Point(0,0), width, height, 0);
            case "Lavabo" -> new Lavabo("", curr.getRoom(), new Point(0,0), width, height, 0);
            case "Kada" -> new Kada("", curr.getRoom(), new Point(0,0), width, height, 0);
            case "Ormar" -> new Ormar("", curr.getRoom(), new Point(0,0), width, height, 0);
            case "Sto" -> new Sto("", curr.getRoom(), new Point(0,0), width, height, 0);
            case "VesMasina" -> new VesMasina("", curr.getRoom(), new Point(0,0), width, height, 0);
            case "Vrata" -> new Vrata("", curr.getRoom(), new Point(0,0), width, height, 0);
            case "WCsolja" -> new WCSolja("", curr.getRoom(), new Point(0,0), width, height, 0);
            default -> null;
        };
    }
}
