package raf.draft.dsw.gui.swing.jtree.controller;

import raf.draft.dsw.gui.swing.jtree.model.DraftTreeItem;
import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.model.messages.Message;
import raf.draft.dsw.model.messages.MessageType;
import raf.draft.dsw.model.structures.Room;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.EventObject;

public class DraftTreeCellEditor extends DefaultTreeCellEditor implements ActionListener{
    private Object clickedOn =null;
    private JTextField edit=null;

    public DraftTreeCellEditor(JTree tree, DefaultTreeCellRenderer renderer) {
        super(tree, renderer);
    }

    public Component getTreeCellEditorComponent(JTree tree, Object clickedOn, boolean arg2, boolean arg3, boolean arg4, int arg5) {
        //super.getTreeCellEditorComponent(arg0,arg1,arg2,arg3,arg4,arg5);
        this.clickedOn = clickedOn;
        edit=new JTextField(clickedOn.toString());
        edit.addActionListener(this);
        return edit;
    }

    public boolean isCellEditable(EventObject arg0) {
        if (arg0 instanceof MouseEvent)
            if (((MouseEvent) arg0).getClickCount() == 3)
                return true;
        if(arg0 instanceof KeyEvent)
            if(((KeyEvent)arg0).getKeyCode() == KeyEvent.VK_F2)
                return true;

        return false;
    }

    public void actionPerformed(ActionEvent e){

        if (!(this.clickedOn instanceof DraftTreeItem))
            return;
        DraftTreeItem clicked = (DraftTreeItem) clickedOn;
        clicked.setName(e.getActionCommand());
        if(clicked.getDraftNode() instanceof Room){
            Room room = (Room) clicked.getDraftNode();
            room.notifySubscribers(new Message("value changed: "+e.getActionCommand(), MessageType.INFO, LocalDateTime.now()));
        }
        stopCellEditing(); // kad se klikne enter staje i azurira
    }
}
