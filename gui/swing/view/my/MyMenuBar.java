package raf.draft.dsw.gui.swing.view.my;

import raf.draft.dsw.controller.actions.*;
import raf.draft.dsw.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MyMenuBar extends JMenuBar{
    public MyMenuBar(){
        this.setBackground(new Color(215, 204, 200));

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        ExitAction ea = MainFrame.getInstance().getActionManager().getExitAction();
        AboutUsAction au = MainFrame.getInstance().getActionManager().getAboutUsAction();
        fileMenu.add(ea);
        fileMenu.add(au);
        add(fileMenu);

        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);
        AddNewDraftNode addNewDraftNode = MainFrame.getInstance().getActionManager().getAddNewDraftNode();
        editMenu.add(addNewDraftNode);
        DeleteNode deleteNode = MainFrame.getInstance().getActionManager().getDeleteNode();
        editMenu.add(deleteNode);
        RenameDraftNode renameDraftNode = MainFrame.getInstance().getActionManager().getRenameDraftNode();
        editMenu.add(renameDraftNode);
        add(editMenu);
    }
}
