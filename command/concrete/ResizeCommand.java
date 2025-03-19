package raf.draft.dsw.controller.command.concrete;

import raf.draft.dsw.controller.command.AbstractCommand;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.view.my.MyTabPanel;
import raf.draft.dsw.gui.swing.view.painters.RoomElementPainter;
import raf.draft.dsw.model.structures.roomelements.RoomElement;

import java.awt.*;
import java.util.List;

public class ResizeCommand extends AbstractCommand {
    private Dimension dimension;
    private Dimension dimensionRedo;
    private RoomElement roomElement;
    public ResizeCommand(Dimension dimension, RoomElement resizeElement) {
        this.dimension = dimension;
        this.roomElement = resizeElement;
    }

    @Override
    public void doCommand() {
        if(dimensionRedo != null){
            this.roomElement.setWidth(dimensionRedo.width);
            this.roomElement.setHeight(dimensionRedo.height);
            ApplicationFramework.getInstance().getProjectController().setOnChanged(this.roomElement);
        }
    }

    @Override
    public void undoCommand() {
        dimensionRedo = new Dimension(this.roomElement.getWidth(), this.roomElement.getHeight());
        this.roomElement.setWidth(dimension.width);
        this.roomElement.setHeight(dimension.height);;
        ApplicationFramework.getInstance().getProjectController().setOnChanged(this.roomElement);
    }
}
