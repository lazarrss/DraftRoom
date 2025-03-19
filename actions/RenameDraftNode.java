package raf.draft.dsw.controller.actions;

import raf.draft.dsw.controller.AbstractRoomAction;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.jtree.DraftTreeImplementation;
import raf.draft.dsw.gui.swing.jtree.model.DraftTreeItem;
import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.model.messages.MessageType;
import raf.draft.dsw.model.structures.Building;
import raf.draft.dsw.model.structures.Project;
import raf.draft.dsw.model.structures.ProjectExplorer;
import raf.draft.dsw.model.structures.Room;

import javax.swing.*;
import java.awt.event.*;

public class RenameDraftNode extends AbstractRoomAction {
    public RenameDraftNode(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F2, InputEvent.CTRL_DOWN_MASK));
        putValue(SMALL_ICON, loadIcon("/images/toolBar/edit.png"));
        putValue(NAME, "Edit");
        putValue(SHORT_DESCRIPTION, "Edit draft node");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        DraftTreeItem selected = MainFrame.getInstance().getDraftTree().getSelectedNode();
        if(selected == null){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Select something to rename", MessageType.ERROR);
            return;
        }
        if(selected.getDraftNode() instanceof ProjectExplorer){
            String opt = JOptionPane.showInputDialog("New name");
            if(opt == null)
                return;
            if(opt.isBlank()) {
                ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Node name cannot be empty", MessageType.ERROR);
                return;
            }
            selected.setName(opt);
        }
        else if(selected.getDraftNode() instanceof Building){
            String opt = JOptionPane.showInputDialog("New name");
            if(opt == null)
                return;
            if(opt.isBlank()) {
                ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Node name cannot be empty", MessageType.ERROR);
                return;
            }
            if(((Building) selected.getDraftNode()).doesNameExists(opt)){
                ApplicationFramework.getInstance().getMessageGenerator().generateMessage("This name is taken", MessageType.ERROR);
                return;
            }

            selected.setName(opt);
        }else if(selected.getDraftNode() instanceof Room) {
            String opt = JOptionPane.showInputDialog("New name");
            if(opt == null)
                return;
            if(opt.isBlank()) {
                ApplicationFramework.getInstance().getMessageGenerator().generateMessage("Node name cannot be empty", MessageType.ERROR);
                return;
            }
            if(((Room) selected.getDraftNode()).doesNameExsists(opt))
            {
                ApplicationFramework.getInstance().getMessageGenerator().generateMessage("This name is taken", MessageType.ERROR);
                return;
            }

            selected.setName(opt);
        }else{
            JTextField name = new JTextField();
            JTextField author = new JTextField();
            JTextField path = new JTextField();
            Object[] message = {
                    "Name: ", name,
                    "Author: ", author,
                    "Path: ", path
            };
            int option = JOptionPane.showConfirmDialog(null, message, "Edit", JOptionPane.OK_CANCEL_OPTION);
            if(option == 0){
                if(name.getText().isBlank() && author.getText().isBlank() && path.getText().isBlank()){
                    ApplicationFramework.getInstance().getMessageGenerator().generateMessage("All attributes cannot be empty", MessageType.ERROR);
                    return;
                }
                else if(name.getText().isBlank() && author.getText().isBlank() && !path.getText().isBlank()){
                    ((Project)selected.getDraftNode()).setPathToFile(path.getText());
                }
                else if(name.getText().isBlank() && !author.getText().isBlank()){
                    if(!path.getText().isBlank())
                        ((Project)selected.getDraftNode()).setPathToFile(path.getText());
                    ((Project) selected.getDraftNode()).setAuthor(author.getText());
                }
                else if(!name.getText().isBlank() && author.getText().isBlank()){
                    if(((ProjectExplorer)selected.getDraftNode().getParent()).getChildren() == null)
                        return;
                    if(((Project) selected.getDraftNode()).doesNameExsists(name.getText())){
                        ApplicationFramework.getInstance().getMessageGenerator().generateMessage("This name is taken", MessageType.ERROR);
                        return;
                    }

                    if(!path.getText().isBlank())
                        ((Project)selected.getDraftNode()).setPathToFile(path.getText());
                    selected.getDraftNode().setName(name.getText());
                }else {
                    selected.getDraftNode().setName(name.getText());
                    ((Project) selected.getDraftNode()).setAuthor(author.getText());
                    ((Project) selected.getDraftNode()).setPathToFile(path.getText());
                }
            }
        }
        SwingUtilities.updateComponentTreeUI(((DraftTreeImplementation) MainFrame.getInstance().getDraftTree()).getDraftTreeView());
        MainFrame.getInstance().projectExplorer.clearSelection();
    }

}
