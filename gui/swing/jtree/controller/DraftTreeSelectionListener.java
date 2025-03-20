package raf.draft.dsw.gui.swing.jtree.controller;

import raf.draft.dsw.gui.swing.jtree.model.DraftTreeItem;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;


public class DraftTreeSelectionListener implements TreeSelectionListener {
    @Override
    public void valueChanged(TreeSelectionEvent e) {
        TreePath path = e.getPath();
        DraftTreeItem treeItemSelected = (DraftTreeItem) path.getLastPathComponent();
        System.out.println(STR."Selektovan cvor:\{treeItemSelected.getDraftNode().getName()}");
        System.out.println(STR."getPath: \{e.getPath()}");
    }
}
