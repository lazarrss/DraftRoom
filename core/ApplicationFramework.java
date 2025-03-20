package raf.draft.dsw.core;

import raf.draft.dsw.controller.filecontrollers.ProjectController;
import raf.draft.dsw.controller.messagegenerator.*;
import raf.draft.dsw.controller.filecontrollers.RoomController;
import raf.draft.dsw.gui.swing.jtree.DraftTreeImplementation;
import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.model.repository.DraftRoomRepositoryImplementation;
import raf.draft.dsw.model.repository.DraftRoomRepository;
import raf.draft.dsw.serializer.Serializer;
import raf.draft.dsw.serializer.SerializerImplementation;

public class ApplicationFramework {
    private static ApplicationFramework instance;
    private final MessageGenerator messageGenerator = new MessageGenerator();
    private final LoggerFactory loggerFactory = new LoggerFactory();
    private Logger consoleLogger;
    private Logger fileLogger;
    private DraftRoomRepository draftRoomRepository;
    private Serializer serializer;
    private ProjectController projectController;
    private RoomController roomController;
    private ApplicationFramework(){
        initialize();
    }

    public void initializeLoggers() {
        consoleLogger = loggerFactory.createLogger("consolelogger");
        fileLogger = loggerFactory.createLogger("filelogger");
    }

    public void initialize(){
        messageGenerator.addSubscriber(MainFrame.getInstance());
        MainFrame.getInstance().setVisible(true);
        this.draftRoomRepository = new DraftRoomRepositoryImplementation();
        ((DraftTreeImplementation) MainFrame.getInstance().getDraftTree()).getDraftNodeFactory().addSubscriber(MainFrame.getInstance());
        serializer = new SerializerImplementation();
        projectController = new ProjectController(serializer);
        roomController = new RoomController(serializer);
    }

    public static ApplicationFramework getInstance() {
        if (instance==null) instance=new ApplicationFramework();
        return instance;
    }

    public MessageGenerator getMessageGenerator() {
        return messageGenerator;
    }

    public DraftRoomRepository getDraftRoomRepository() {
        return draftRoomRepository;
    }

    public ProjectController getProjectController() {
        return projectController;
    }

    public RoomController getRoomController() {
        return roomController;
    }

    public void enableUndo(){
        MainFrame.getInstance().getActionManager().getUndoAction().setEnabled(true);
    }
    public void disableUndo(){
        MainFrame.getInstance().getActionManager().getUndoAction().setEnabled(false);
    }
    public void enableRedo(){
        MainFrame.getInstance().getActionManager().getRedoAction().setEnabled(true);
    }

    public void disableRedo(){
        MainFrame.getInstance().getActionManager().getRedoAction().setEnabled(false);
    }
}
