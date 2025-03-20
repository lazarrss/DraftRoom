package raf.draft.dsw.gui.swing.view.my;

import com.sun.tools.javac.Main;
import raf.draft.dsw.controller.state.actions.addactions.AddBojlerAction;
import raf.draft.dsw.controller.state.actions.addactions.AddVrataAction;
import raf.draft.dsw.controller.state.actions.addactions.AddWCSoljaAction;
import raf.draft.dsw.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.*;

public class MyToolBarState extends JToolBar {
    public MyToolBarState(){
        super(VERTICAL);
        setFloatable(false);
        this.setBackground(new Color(215,204,200));
        //this.setBackground(new Color(173, 100, 74));

        add(MainFrame.getInstance().getActionManager().getBojlerAction());
        add(MainFrame.getInstance().getActionManager().getKadaAction());
        add(MainFrame.getInstance().getActionManager().getKrevetAction());
        add(MainFrame.getInstance().getActionManager().getLavaboAction());
        add(MainFrame.getInstance().getActionManager().getOrmarAction());
        add(MainFrame.getInstance().getActionManager().getStoAction());
        add(MainFrame.getInstance().getActionManager().getVesMasinaAction());
        add(MainFrame.getInstance().getActionManager().getVrataAction());
        add(MainFrame.getInstance().getActionManager().getWcSoljaAction());
        add(MainFrame.getInstance().getActionManager().getDeleteAction());
        add(MainFrame.getInstance().getActionManager().getEditAction());
        add(MainFrame.getInstance().getActionManager().getMoveAction());
        add(MainFrame.getInstance().getActionManager().getResizeAction());
        add(MainFrame.getInstance().getActionManager().getRotateAction());
        add(MainFrame.getInstance().getActionManager().getRotateLeftState());
        add(MainFrame.getInstance().getActionManager().getSelectAction());
        add(MainFrame.getInstance().getActionManager().getZoomAction());
        add(MainFrame.getInstance().getActionManager().getCopyStateAction());
        add(MainFrame.getInstance().getActionManager().getPasteStateAction());
    }
}
