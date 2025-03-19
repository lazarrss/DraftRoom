package raf.draft.dsw.controller.actions;

import raf.draft.dsw.controller.AbstractRoomAction;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.jtree.model.DraftTreeItem;
import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.model.messages.MessageType;
import raf.draft.dsw.model.nodefactory.DraftNodeFactory;
import raf.draft.dsw.model.structures.Building;
import raf.draft.dsw.model.structures.Project;
import raf.draft.dsw.model.structures.ProjectExplorer;
import raf.draft.dsw.model.structures.Room;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class AddNewDraftNode extends AbstractRoomAction {
    public AddNewDraftNode(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        putValue(SMALL_ICON, loadIcon("/images/toolBar/add.png"));
        putValue(NAME, "Add");
        putValue(SHORT_DESCRIPTION, "AddNewDraftNode");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DraftTreeItem selected = MainFrame.getInstance().getDraftTree().getSelectedNode();
        if(selected == null){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("You need to select a node", MessageType.ERROR);
            return;
        }
        String[] attributes = null;
        String opt;
        String type = null;
        if(selected.getDraftNode() instanceof Building) {
            type = DraftNodeFactory.ROOM;
            opt = JOptionPane.showInputDialog("Enter a name");
            if(opt == null)
                return;
            if(opt.isBlank()) {
                ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Node name cannot be empty", MessageType.ERROR);
                return;
            }
            if(((Building) selected.getDraftNode()).childNameTaken(opt)){
                ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Building with that name already exists in this project", MessageType.ERROR);
                return;
            }
            attributes = new String[] {opt};
        }else if(selected.getDraftNode() instanceof Room){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("You cannot add a node to room", MessageType.ERROR);
            return;
        }
        else if(selected.getDraftNode() instanceof ProjectExplorer){
            JTextField name = new JTextField();
            JTextField author = new JTextField();
            JTextField path = new JTextField();
            Object[] message = {
                    "Name: ", name,
                    "Author: ", author,
                    "Path: ", path
            };
            int option = JOptionPane.showConfirmDialog(null, message, "Project attributes", JOptionPane.OK_CANCEL_OPTION);
            if(option == -1 || option == 2)
                return;
            if(((ProjectExplorer) selected.getDraftNode()).childNameTaken(name.getText())){
                ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Project with that name already exists",MessageType.ERROR);
                return;
            }

            if(name.getText().isBlank()){
                ApplicationFramework.getInstance().getMessageGenerator().generateMessage("You must enter a name", MessageType.ERROR);
                return;
            }
            if(option == 0){
                attributes = new String[] {name.getText(), author.getText(), path.getText()};
            }
            else if(option == 2) {
                return;
            }
            type = DraftNodeFactory.PROJECT;

        }else if (selected.getDraftNode() instanceof Project){
            int choice = JOptionPane.showOptionDialog(null, "Choose building or room", "Add New",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[] {"Building", "Room"}, "Building");
            if (choice == 0) {
                type = DraftNodeFactory.BUILDING;
                opt = JOptionPane.showInputDialog("Enter a name");
                if(opt == null)
                    return;
                if(opt.isBlank()) {
                    ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Node name cannot be empty", MessageType.ERROR);
                    return;
                }
                if(((Project) selected.getDraftNode()).childNameTaken(opt, type)){
                    ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Building with that name already exists in this project", MessageType.ERROR);
                    return;
                }
                attributes = new String[] {opt};
            } else if(choice == 1){

                type = DraftNodeFactory.ROOM;
                opt = JOptionPane.showInputDialog("Enter a name");
                if(opt == null)
                    return;
                if(opt.isBlank()) {
                    ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Node name cannot be empty", MessageType.ERROR);
                    return;
                }
                if(((Project) selected.getDraftNode()).childNameTaken(opt, type)){
                    ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Building with that name already exists in this project", MessageType.ERROR);
                    return;
                }
                attributes = new String[] {opt};

            } else if(choice == -1){
                return;
            }
        }
        MainFrame.getInstance().getDraftTree().addChild(selected, attributes, type);
    }
}