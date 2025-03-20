package raf.draft.dsw.gui.swing.view.my;

import raf.draft.dsw.controller.observer.ISubscriber;
import raf.draft.dsw.controller.state.StateManager;
import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.model.messages.Message;
import raf.draft.dsw.model.structures.roomelements.RoomElement;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.NoninvertibleTransformException;
import java.util.HashMap;

public class MyTabbedPane extends JTabbedPane implements ISubscriber {
    private StateManager stateManager;
    private int currentHeightCM;
    private int currentWidthCM;
    private RoomElement roomElement;
    private HashMap<Integer, MyTabPanel> mapOfClosedTabs = new HashMap<>();
    public MyTabbedPane(int top, int scrollTabLayout) {
        super(top, scrollTabLayout);
        stateManager = new StateManager();
    }
    public void defaultState() { this.stateManager.setStartState(); }
    public void startAddBojlerState() {
        this.stateManager.setAddBojlerState();
    }
    public void startEditRoomState() { this.stateManager.setEditRoomState();
    }
    public void startAddKadaState(){
        this.stateManager.setAddKadaState();
    }
    public void startAddKrevetState(){
        this.stateManager.setAddKrevetState();
    }
    public void startAddLavaboState(){
        this.stateManager.setAddLavaboState();
    }
    public void startAddOrmarState(){
        this.stateManager.setAddOrmarState();
    }
    public void startAddStoState(){
        this.stateManager.setAddStoState();
    }
    public void startAddVesMasinaState(){
        this.stateManager.setAddVesMasinaState();
    }
    public void startAddVrataState(){
        this.stateManager.setAddVrataState();
    }
    public void startAddWCSoljaState() {
        this.stateManager.setAddWCSoljaState();
    }
    public void startEditState() { this.stateManager.setEditState(); }
    public void startMoveState(){
        this.stateManager.setMoveState();
    }
    public void startResizeState(){
        this.stateManager.setResizeState();
    }
    public void startRotateRightState(){
        this.stateManager.setRotateRightState();
        triggerOnAction((MyTabPanel) MainFrame.getInstance().getTabbedPane().getSelectedComponent(), new Point());
    }
    public void startRotateLeftState(){
        this.stateManager.setRotateLeftState();
        triggerOnAction((MyTabPanel) MainFrame.getInstance().getTabbedPane().getSelectedComponent(), new Point());
    }
    public void startSelectState(){
        this.stateManager.setSelectState();
    }
    public void startZoomState(){
        this.stateManager.setZoomState();
        //triggerOnAction((MyTabPanel) MainFrame.getInstance().getTabbedPane().getSelectedComponent(), new Point());
    }
    public void startDeleteState() {
        this.stateManager.setDeleteState();
        triggerOnAction((MyTabPanel) MainFrame.getInstance().getTabbedPane().getSelectedComponent(), new Point());
    }
    public void startCopyState(){
        this.stateManager.setCopyState();
        triggerOnAction((MyTabPanel) MainFrame.getInstance().getTabbedPane().getSelectedComponent(), new Point());
    }
    public void startPasteState(){
        this.stateManager.setPasteState();
        triggerOnAction((MyTabPanel) MainFrame.getInstance().getTabbedPane().getSelectedComponent(), new Point());
    }
    public void misKliknut(MyTabPanel roomView, Point point) throws NoninvertibleTransformException, InterruptedException {
        this.stateManager.getCurrentState().misKliknut(roomView, point);
        System.out.println(point);
    }
    public void misPritisnut(MyTabPanel roomView, Point point) throws NoninvertibleTransformException {
        this.stateManager.getCurrentState().misPritisnut(roomView, point);
    }
    public void misPusten(MyTabPanel roomView, Point point) throws NoninvertibleTransformException {
        this.stateManager.getCurrentState().misPusten(roomView, point);
    }
    public void misUsao(MyTabPanel roomView, Point point){
        this.stateManager.getCurrentState().misUsao(roomView, point);
    }
    public void misIzasao(MyTabPanel roomView, Point point){
        this.stateManager.getCurrentState().misIzasao(roomView, point);
    }
    public void misVuce(MyTabPanel roomView, Point point) throws NoninvertibleTransformException {
        this.stateManager.getCurrentState().misVuce(roomView, point);
    }
    public void misPomeren(MyTabPanel roomView, Point point){
        this.stateManager.getCurrentState().misPomeren(roomView, point);
    }
    public void misSkrolGore(MyTabPanel roomView, Point point) {
        this.stateManager.getCurrentState().misSkrolGore(roomView, point);
    }

    public void misSkrolDole(MyTabPanel roomView, Point point) {
        this.stateManager.getCurrentState().misSkrolDole(roomView, point);
    }
    public void triggerOnAction(MyTabPanel roomView, Point point){
        this.stateManager.getCurrentState().triggerOnAction(roomView, point);
    }

    public int getCurrentHeightCM() {
        return currentHeightCM;
    }

    public void setCurrentHeightCM(int currentHeightCM) {
        this.currentHeightCM = currentHeightCM;
    }

    public int getCurrentWidthCM() {
        return currentWidthCM;
    }

    public void setCurrentWidthCM(int currentWidthCM) {
        this.currentWidthCM = currentWidthCM;
    }
    public int convertToPixels(double cm) {
        return (int) Math.round((cm / 2.54) * 96);
    }
    public int getWidthPixels() {
        return convertToPixels(currentWidthCM);
    }
    public int getHeightPixels() {
        return convertToPixels(currentHeightCM);
    }

    public HashMap<Integer, MyTabPanel> getMapOfClosedTabs() {
        return mapOfClosedTabs;
    }

    public void setMapOfClosedTabs(HashMap<Integer, MyTabPanel> mapOfClosedTabs) {
        this.mapOfClosedTabs = mapOfClosedTabs;
    }

    public RoomElement getRoomElement() {
        return roomElement;
    }

    public void setRoomElement(RoomElement roomElement) {
        this.roomElement = roomElement;
    }

    @Override
    public void update(Message message) {
        if(message.getContent().startsWith("Open tab item")){
            this.stateManager.setEditRoomState();
        }
    }
}
