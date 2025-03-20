package raf.draft.dsw.gui.swing.view.my;

import raf.draft.dsw.controller.actions.*;
import raf.draft.dsw.controller.state.actions.*;
import raf.draft.dsw.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MyToolBar extends JToolBar {
    public MyToolBar(){
        super(HORIZONTAL);
        setFloatable(false);

        this.setBackground(new Color(215, 204, 200));

        ExitAction exitAction = MainFrame.getInstance().getActionManager().getExitAction();
        add(exitAction);

        AboutUsAction aboutUsAction = MainFrame.getInstance().getActionManager().getAboutUsAction();
        add(aboutUsAction);

        AddNewDraftNode addNewDraftNode = MainFrame.getInstance().getActionManager().getAddNewDraftNode();
        add(addNewDraftNode);

        DeleteNode deleteNode = MainFrame.getInstance().getActionManager().getDeleteNode();
        add(deleteNode);

        RenameDraftNode renameDraftNode = MainFrame.getInstance().getActionManager().getRenameDraftNode();
        add(renameDraftNode);

        CopyStateAction copyStateAction = MainFrame.getInstance().getActionManager().getCopyStateAction();
        add(copyStateAction);

        PasteStateAction pasteStateAction = MainFrame.getInstance().getActionManager().getPasteStateAction();
        add(pasteStateAction);

        RotateRightStateAction rotateRightState = MainFrame.getInstance().getActionManager().getRotateAction();
        add(rotateRightState);

        RotateLeftStateAction rotateLeftStateAction = MainFrame.getInstance().getActionManager().getRotateLeftState();
        add(rotateLeftStateAction);

        UndoAction undoAction = MainFrame.getInstance().getActionManager().getUndoAction();
        add(undoAction);

        RedoAction redoAction = MainFrame.getInstance().getActionManager().getRedoAction();
        add(redoAction);

        SaveAction saveAction = MainFrame.getInstance().getActionManager().getSaveAction();
        add(saveAction);

        SaveAsAction saveAsAction = MainFrame.getInstance().getActionManager().getSaveAsAction();
        add(saveAsAction);

        OpenAction openAction = MainFrame.getInstance().getActionManager().getOpenAction();
        add(openAction);

        SavePattern savePattern = MainFrame.getInstance().getActionManager().getSavePattern();
        add(savePattern);

        LoadPattern loadPattern = MainFrame.getInstance().getActionManager().getLoadPattern();
        add(loadPattern);

        Organize organize = MainFrame.getInstance().getActionManager().getOrganize();
        add(organize);

        AutoOrganize autoOrganize = MainFrame.getInstance().getActionManager().getAutoOrganize();
        add(autoOrganize);
    }
}
