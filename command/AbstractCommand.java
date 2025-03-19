package raf.draft.dsw.controller.command;

public abstract class AbstractCommand {
    public abstract void doCommand();
    public abstract void undoCommand();
}
