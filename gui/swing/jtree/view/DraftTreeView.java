package raf.draft.dsw.gui.swing.jtree.view;

import raf.draft.dsw.gui.swing.jtree.controller.DraftTreeSelectionListener;
import raf.draft.dsw.gui.swing.jtree.controller.DraftTreeCellEditor;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

public class DraftTreeView extends JTree{
    public DraftTreeView(DefaultTreeModel defaultTreeModel) {
        setModel(defaultTreeModel);

        DraftTreeCellRenderer renderer = new DraftTreeCellRenderer();
        renderer.setBackgroundNonSelectionColor(new Color(215, 204, 200));

        addTreeSelectionListener(new DraftTreeSelectionListener());

        setCellEditor(new DraftTreeCellEditor(this,renderer));

        setCellRenderer(renderer);

        setEditable(true);

        this.setBackground(new Color(215, 204, 200));
    }
}
