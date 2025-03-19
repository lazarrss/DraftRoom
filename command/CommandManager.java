package raf.draft.dsw.controller.command;

import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.view.MainFrame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private List<AbstractCommand> commands = new ArrayList<>();
    int current = 0;

    public void doCommand(){
        if(current < commands.size()){
            commands.get(current++).doCommand();
            SwingUtilities.updateComponentTreeUI(MainFrame.getInstance().projectExplorer);
            ApplicationFramework.getInstance().enableUndo();
        }
        if(current == commands.size())
            ApplicationFramework.getInstance().disableRedo();
    }

    public void undoCommand(){
        if(current > 0){
            commands.get(--current).undoCommand();
            SwingUtilities.updateComponentTreeUI(MainFrame.getInstance().projectExplorer);
            ApplicationFramework.getInstance().enableRedo();
        }
        if(current == 0)
            ApplicationFramework.getInstance().disableUndo();
    }

    public void addCommand(AbstractCommand command){
        while(current < commands.size())
            commands.remove(current);
        commands.add(command);
        doCommand();
    }
    public int commandSize(){
        return this.commands.size();
    }
    public int getCurrent(){
        return this.current;
    }
}
