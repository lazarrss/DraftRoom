package raf.draft.dsw.gui.swing.view;

import raf.draft.dsw.controller.ActionManager;
import raf.draft.dsw.controller.organizer.RoomOrganizer;
import raf.draft.dsw.gui.swing.jtree.controller.OpenTab;
import raf.draft.dsw.controller.observer.ISubscriber;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.jtree.DraftTree;
import raf.draft.dsw.gui.swing.jtree.DraftTreeImplementation;
import raf.draft.dsw.gui.swing.jtree.model.DraftTreeItem;
import raf.draft.dsw.gui.swing.view.my.*;
import raf.draft.dsw.model.messages.Message;
import raf.draft.dsw.model.messages.MessageType;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements ISubscriber {
    private static MainFrame instance;
    private final ActionManager actionManager;
    private final DraftTree draftTree;
    public JTree projectExplorer;
    public JScrollPane scrollPane;
    public JScrollPane upperScroll;
    public JSplitPane split;
    public MyTabbedPane tabbedPane;
    public OpenTab openTab;
    private RoomOrganizer organizer;

    private MainFrame(){
        initialize();
        actionManager = new ActionManager();
        draftTree = new DraftTreeImplementation();
    }

    private void initialize(){
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth / 2, screenHeight / 2);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("DraftRoom");
    }
    public void initializeAll(){
        MyMenuBar menu = new MyMenuBar();
        setJMenuBar(menu);
        MyToolBar toolBar = new MyToolBar();
        upperScroll = new JScrollPane(toolBar);
        add(upperScroll, BorderLayout.NORTH);

        projectExplorer = draftTree.generateTree(ApplicationFramework.getInstance().getDraftRoomRepository().getRoot());

        tabbedPane = new MyTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

        MyToolBarState myToolBarState = new MyToolBarState();
        scrollPane = new JScrollPane(myToolBarState);
        scrollPane.setPreferredSize(new Dimension(70,myToolBarState.getComponentCount()*35));
        scrollPane.setMinimumSize(new Dimension(70, myToolBarState.getComponentCount()*35));
        JScrollPane scroll=new JScrollPane(projectExplorer);
        scroll.setMinimumSize(new Dimension(200,150));
        split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,scroll,tabbedPane);
        split.setDividerLocation(250);
        split.setOneTouchExpandable(true);

        getContentPane().add(split,BorderLayout.CENTER);
        getContentPane().add(scrollPane, BorderLayout.EAST);
        scrollPane.setVisible(false);
        openTab = new OpenTab(projectExplorer,tabbedPane);

        organizer = new RoomOrganizer();

        super.setVisible(true);
    }


    @Override
    public void update(Message message) {
        if(message.getMessageType() == MessageType.ERROR)
            JOptionPane.showMessageDialog(null, message.getContent(), message.getMessageType().toString(),
                JOptionPane.ERROR_MESSAGE);
        else if(message.getMessageType() == MessageType.WARNING)
            JOptionPane.showMessageDialog(null, message.getContent(), message.getMessageType().toString(),
                    JOptionPane.WARNING_MESSAGE);
        else if(message.getContent().startsWith("Open tab item")){
            openTab.sendRoom((DraftTreeItem) message.getObject());
        }else
            JOptionPane.showMessageDialog(null, message.getContent(), message.getMessageType().toString(),
                    JOptionPane.INFORMATION_MESSAGE);

    }


    public static MainFrame getInstance() {
        if(instance==null) instance = new MainFrame();
        return instance;
    }

    public ActionManager getActionManager() {
        return actionManager;
    }

    public DraftTree getDraftTree() {
        return draftTree;
    }

    public MyTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public RoomOrganizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(RoomOrganizer organizer) {
        this.organizer = organizer;
    }
}
