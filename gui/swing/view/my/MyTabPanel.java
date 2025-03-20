package raf.draft.dsw.gui.swing.view.my;

import raf.draft.dsw.controller.command.CommandManager;
import raf.draft.dsw.controller.listeners.MouseRoomListener;
import raf.draft.dsw.controller.observer.ISubscriber;
import raf.draft.dsw.controller.state.StateManager;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.jtree.controller.OpenTab;
import raf.draft.dsw.gui.swing.jtree.model.DraftTreeItem;
import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.gui.swing.view.painters.RoomElementPainter;
import raf.draft.dsw.gui.swing.view.painters.RoomPainter;
import raf.draft.dsw.model.messages.Message;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.structures.ProjectExplorer;
import raf.draft.dsw.model.structures.Room;
import raf.draft.dsw.model.structures.roomelements.Prototype;
import raf.draft.dsw.model.structures.roomelements.RoomElement;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyTabPanel extends JPanel implements ISubscriber {
    private MyTabbedPane myTabbedPane;
    private JLabel path = new JLabel();
    private JLabel author = new JLabel();
    private MyTabHeader header;

    private double scalingFactor;
    private double zoomFactor;
    private boolean moveFlag = true;
    private boolean dragAndDropFlag = false;

    private Point Start = new Point(10, 15);
    private Point offSet = new Point(0, 0);
    private Point dragStartPoint;
    private Point dragEndPoint;
    private Point zoomPoint;
    private double xZoom;
    private double yZoom;

    private Room room;
    private DraftTreeItem roomItem;
    private RoomPainter roomPainter;
    private List<DraftTreeItem> roomElements = new ArrayList<>();

    private List<RoomElementPainter> painterList = new ArrayList<>();
    private List<RoomElementPainter> selectionList = new ArrayList<>();
    private Point selectionPointLT;
    private Point selectionPointRB;
    private List<RoomElementPainter> removeList = new ArrayList<>();
    private RoomElementPainter resizeElement;
    private Dimension resizeUndo;
    private Dimension resizeDo;

    private int width;
    private int height;

    private CommandManager commandManager;

    private MouseRoomListener mouseRoomListener;
    public MyTabPanel(BorderLayout borderLayout, Room room, MyTabbedPane myTabbedPane, DraftTreeItem draftTreeItem){
        this.myTabbedPane = myTabbedPane;
        this.setLayout(borderLayout);
        centerLabels();
        this.add(path, BorderLayout.NORTH);
        this.add(author, BorderLayout.SOUTH);

        this.room = room;
        this.roomItem = draftTreeItem;

        roomPainter = new RoomPainter(room);
        room.addSubscriber(this);

        mouseRoomListener = new MouseRoomListener(this);
        this.addMouseListener(mouseRoomListener);
        this.addMouseMotionListener(mouseRoomListener);
        this.addMouseWheelListener(mouseRoomListener);

        selectionPointLT = new Point();
        selectionPointRB = new Point();

        zoomFactor = 1.0;

        commandManager = new CommandManager();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        AffineTransform start = g2d.getTransform();
        AffineTransform dragAndDrop = new AffineTransform();
        AffineTransform zoom = new AffineTransform();

        if (getZoomPoint() != null) {
            double zoomFactor = getZoomFactor();

            zoom.translate(getZoomPoint().x, getZoomPoint().y);
            zoom.scale(zoomFactor, zoomFactor);
            zoom.translate(-getZoomPoint().x, -getZoomPoint().y);

            g2d.transform(zoom);
        }

        dragAndDrop.translate(offSet.x, offSet.y);
        g2d.transform(dragAndDrop);

        roomPainter.paint(g2d);

        for (RoomElementPainter painter : painterList) {
            painter.paint(g2d);
        }

        for (RoomElementPainter painter : selectionList) {
            painter.fillSelected(g2d);
        }

        g2d.setTransform(start);
        if (selectionPointLT != null && selectionPointRB != null) {
            int x, y;
                x = Math.min(selectionPointLT.x, selectionPointRB.x);
                y = Math.min(selectionPointLT.y, selectionPointRB.y);
            int width = Math.abs(selectionPointRB.x - selectionPointLT.x);
            int height = Math.abs(selectionPointRB.y - selectionPointLT.y);

            g2d.setColor(new Color(255, 122, 16, 30));
            g2d.fillRect(x, y, width, height);

            g2d.setColor(Color.ORANGE);
            g2d.drawRect(x, y, width, height);
        }

    }

    public DraftTreeItem getRoomItem() {
        return roomItem;
    }

    public void setRoomItem(DraftTreeItem roomItem) {
        this.roomItem = roomItem;
    }

    public Room getRoom() {
        return room;
    }

    public JLabel getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path.setText(path);
    }

    public JLabel getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author.setText(author);
    }

    public List<RoomElementPainter> getPainterList() {
        return painterList;
    }

    public void setPainterList(List<RoomElementPainter> painterList) {
        this.painterList = painterList;
    }

    public RoomPainter getRoomPainter() {
        return roomPainter;
    }

    public void setRoomPainter(RoomPainter roomPainter) {
        this.roomPainter = roomPainter;
    }
    public int myGetWidth(){
        return width;
    }
    public int myGetHeight(){
        return height;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    public List<RoomElementPainter> getSelectionList() {
        return selectionList;
    }

    public void setSelectionList(List<RoomElementPainter> selectionList) {
        this.selectionList = selectionList;
    }

    public Point getSelectionPointLT() {
        return selectionPointLT;
    }

    public void setSelectionPointLT(Point selectionPointLT) {
        this.selectionPointLT = selectionPointLT;
    }

    public boolean isDragAndDropFlag() {
        return dragAndDropFlag;
    }

    public void setDragAndDropFlag(boolean dragAndDropFlag) {
        this.dragAndDropFlag = dragAndDropFlag;
    }

    public Point getSelectionPointRB() {
        return selectionPointRB;
    }

    public void setSelectionPointRB(Point selectionPointRB) {
        this.selectionPointRB = selectionPointRB;
    }

    public List<RoomElementPainter> getRemoveList() {
        return removeList;
    }

    public void setRemoveList(List<RoomElementPainter> removeList) {
        this.removeList = removeList;
    }

    public List<DraftTreeItem> getRoomElements() {
        return roomElements;
    }

    public void setRoomElements(List<DraftTreeItem> roomElements) {
        this.roomElements = roomElements;
    }

    public RoomElementPainter getResizeElement() {
        return resizeElement;
    }

    public void setResizeElement(RoomElementPainter resizeElement) {
        this.resizeElement = resizeElement;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public double getScalingFactor() {
        return scalingFactor;
    }

    public void setScalingFactor(double scalingFactor) {
        this.scalingFactor = scalingFactor;
    }

    public double getZoomFactor() {
        return zoomFactor;
    }

    public void setZoomFactor(double zoomFactor) {
        if(zoomFactor > 3)
            this.zoomFactor = 3;
        else if(zoomFactor < 0.5)
            this.zoomFactor = 0.5;
        else this.zoomFactor = zoomFactor;
    }

    public Point getDragStartPoint() {
        return dragStartPoint;
    }

    public void setDragStartPoint(Point dragStartPoint) {
        this.dragStartPoint = dragStartPoint;
    }

    public Dimension getResizeUndo() {
        return resizeUndo;
    }

    public void setResizeUndo(Dimension resizeUndo) {
        this.resizeUndo = resizeUndo;
    }

    public Dimension getResizeDo() {
        return resizeDo;
    }

    public void setResizeDo(Dimension resizeDo) {
        this.resizeDo = resizeDo;
    }

    public Point getDragEndPoint() {
        return dragEndPoint;
    }

    public void setDragEndPoint(Point dragEndPoint) {
        this.dragEndPoint = dragEndPoint;
    }

    public Point getZoomPoint() {
        return zoomPoint;
    }

    public void setZoomPoint(Point zoomPoint) {
        this.zoomPoint = zoomPoint;
    }

    public Point getOffSet() {
        return offSet;
    }

    public void setOffSet(Point offSet) {
        this.offSet = offSet;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public MyTabHeader getHeader() {
        return header;
    }

    public void setHeader(MyTabHeader header) {
        this.header = header;
    }

    @Override
    public void update(Message message) {
        String newPath;
        String change = message.getContent().substring(message.getContent().indexOf(':') + 2);
        String[] split = this.path.getText().split("/");
        if(message.getContent().startsWith("building")){
            split[1] = change;
            newPath = STR."\{split[0]}/\{split[1]}";
            this.path.setText(newPath);
        }else if(message.getContent().startsWith("project name")){
            split[0] = change;
            if(split.length>1)
                newPath = STR."\{split[0]}/\{split[1]}";
            else newPath = split[0] + '/';
            this.path.setText(newPath);
        }else if(message.getContent().startsWith("project author")){
            this.author.setText(change);
        }else if(message.getContent().startsWith("removed")) {
            myTabbedPane.remove(this);
        }else if(message.getContent().startsWith("repaint")){
            repaint();
//            ApplicationFramework.getInstance().getProjectController().setOnChanged((DraftNode) message.getObject());
        }
    }
    private void centerLabels() {
        author.setHorizontalAlignment(SwingConstants.CENTER);
        author.setVerticalAlignment(SwingConstants.CENTER);
        path.setVerticalAlignment(SwingConstants.CENTER);
        path.setHorizontalAlignment(SwingConstants.CENTER);
    }
    public int commandSize(){
        return commandManager.commandSize();
    }
    public int commandPos(){
        return commandManager.getCurrent();
    }

    public void editScaleFactor() {
        if(this.getRoom().getRoomWidth() == 0 || this.getRoom().getRoomHeight() == 0)
            return;
        double panelWidth = this.myGetWidth() - 20; //100
        double panelHeight = this.myGetHeight() - 30; //100
        double roomHeight = (this.getRoom().getRoomHeight()); //50
        double roomWidth = (this.getRoom().getRoomWidth()); //50

        double odnosW = panelWidth / roomWidth; // 418 / 2000
        double odnosH = panelHeight / roomHeight; // 291 / 22000

        this.setScalingFactor(Math.min(odnosW, odnosH));
    }
}
