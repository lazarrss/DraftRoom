package raf.draft.dsw.controller.actions;

import raf.draft.dsw.controller.AbstractRoomAction;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.model.structures.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class SaveAsAction extends AbstractRoomAction {
    public SaveAsAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        putValue(SMALL_ICON, loadIcon("/images/toolBar/saveAs.png"));
        putValue(NAME, "SaveAs");
        putValue(SHORT_DESCRIPTION, "SaveAs");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if((MainFrame.getInstance().getDraftTree().getSelectedNode() == null) || !(MainFrame.getInstance().getDraftTree().getSelectedNode().getDraftNode() instanceof Project) )
            return;

        Project project = (Project) MainFrame.getInstance().getDraftTree().getSelectedNode().getDraftNode();
        ApplicationFramework.getInstance().getProjectController().saveAs(project);
    }
}
