package raf.draft.dsw.gui.swing.jtree.view;

import raf.draft.dsw.gui.swing.jtree.model.DraftTreeItem;
import raf.draft.dsw.model.structures.Building;
import raf.draft.dsw.model.structures.Project;
import raf.draft.dsw.model.structures.ProjectExplorer;
import raf.draft.dsw.model.structures.Room;
import raf.draft.dsw.model.structures.roomelements.concrete.*;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.net.URL;

public class DraftTreeCellRenderer extends DefaultTreeCellRenderer{
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {

        super.getTreeCellRendererComponent(tree, value, sel,expanded, leaf, row, hasFocus);
        this.setBackground(new Color(215, 204, 200));
        URL imageURL = null;

        if (((DraftTreeItem)value).getDraftNode() instanceof ProjectExplorer) {
            imageURL = getClass().getResource("/images/jtree/projectExplorer.png");
        }
        else if (((DraftTreeItem)value).getDraftNode() instanceof Project) {
            imageURL = getClass().getResource("/images/jtree/project.png");
        }
        else if (((DraftTreeItem)value).getDraftNode() instanceof Building){
            imageURL = getClass().getResource("/images/jtree/building.png");
        }
        else if (((DraftTreeItem)value).getDraftNode() instanceof Room){
            imageURL = getClass().getResource("/images/jtree/room.png");
        }
        else if (((DraftTreeItem)value).getDraftNode() instanceof Bojler){
            imageURL = getClass().getResource("/images/jtree/boilerjtree.png");
        }
        else if (((DraftTreeItem)value).getDraftNode() instanceof Kada){
            imageURL = getClass().getResource("/images/jtree/bathjtree.png");
        }
        else if (((DraftTreeItem)value).getDraftNode() instanceof Krevet){
            imageURL = getClass().getResource("/images/jtree/bedjtree.png");
        }
        else if (((DraftTreeItem)value).getDraftNode() instanceof Vrata){
            imageURL = getClass().getResource("/images/jtree/doorjtree.png");
        }
        else if (((DraftTreeItem)value).getDraftNode() instanceof Lavabo){
            imageURL = getClass().getResource("/images/jtree/sinkjtree.png");
        }
        else if (((DraftTreeItem)value).getDraftNode() instanceof WCSolja){
            imageURL = getClass().getResource("/images/jtree/toiletbowljtree.png");
        }
        else if (((DraftTreeItem)value).getDraftNode() instanceof Ormar){
            imageURL = getClass().getResource("/images/jtree/wardrobejtree.png");
        }
        else if (((DraftTreeItem)value).getDraftNode() instanceof VesMasina){
            imageURL = getClass().getResource("/images/jtree/washingmachinejtree.png");
        }
        else if (((DraftTreeItem)value).getDraftNode() instanceof Sto){
            imageURL = getClass().getResource("/images/jtree/tablejtree.png");
        }
        Icon icon = null;
        if (imageURL != null)
            icon = new ImageIcon(imageURL);
        setIcon(icon);

        return this;
    }
}
