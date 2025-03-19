package raf.draft.dsw.controller.state;

import raf.draft.dsw.controller.state.concrete.*;
import raf.draft.dsw.controller.state.concrete.addstates.*;

public class StateManager {
    private State currentState;
    private StartState startState;
    private EditRoomState editRoomState;
    private AddBojlerState addBojlerState;
    private AddKadaState addKadaState;
    private AddKrevetState addKrevetState;
    private AddLavaboState addLavaboState;
    private AddOrmarState addOrmarState;
    private AddStoState addStoState;
    private AddVesMasinaState addVesMasinaState;
    private AddVrataState addVrataState;
    private AddWCSoljaState addWCSoljaState;
    private EditState editState;
    private MoveState moveState;
    private ResizeState resizeState;
    private RotateRightState rotateRightState;
    private RotateLeftState rotateLeftState;
    private SelectState selectState;
    private ZoomState zoomState;
    private DeleteState deleteState;
    private CopyState copyState;
    private PasteState pasteState;

    public StateManager() {
        initStates();
    }
    public void initStates(){
        startState = new StartState();
        editRoomState = new EditRoomState();

        addBojlerState = new AddBojlerState();
        addKadaState = new AddKadaState();
        addKrevetState = new AddKrevetState();
        addLavaboState = new AddLavaboState();
        addOrmarState = new AddOrmarState();
        addStoState = new AddStoState();
        addVesMasinaState = new AddVesMasinaState();
        addVrataState = new AddVrataState();
        addWCSoljaState = new AddWCSoljaState();

        editState = new EditState();
        moveState = new MoveState();
        resizeState = new ResizeState();
        rotateRightState = new RotateRightState();
        selectState = new SelectState();
        zoomState = new ZoomState();
        deleteState = new DeleteState();
        copyState = new CopyState();
        pasteState = new PasteState();
        rotateLeftState = new RotateLeftState();

        //currentState = editRoomState;
    }

    public State getCurrentState() {
        return currentState;
    }
    public void setStartState() { currentState = startState; }
    public void setEditRoomState() { currentState = editRoomState; }
    public void setMoveState(){
        currentState = moveState;
    }
    public void setResizeState(){
        currentState = resizeState;
    }
    public void setRotateRightState(){
        currentState = rotateRightState;
    }
    public void setSelectState(){
        currentState = selectState;
    }
    public void setZoomState(){
        currentState = zoomState;
    }
    public void setEditState() { currentState = editState; }
    public void setAddBojlerState() {
        currentState = addBojlerState;
    }
    public void setAddKadaState(){
        currentState = addKadaState;
        System.out.println(currentState);
    }
    public void setAddKrevetState(){
        currentState = addKrevetState;
    }
    public void setAddLavaboState(){
        currentState = addLavaboState;
    }
    public void setAddOrmarState(){
        currentState = addOrmarState;
    }
    public void setAddStoState(){
        currentState = addStoState;
    }
    public void setAddVesMasinaState(){
        currentState = addVesMasinaState;
    }
    public void setAddVrataState(){
        currentState = addVrataState;
    }
    public void setAddWCSoljaState(){
        currentState = addWCSoljaState;
    }
    public void setDeleteState() {
        currentState = deleteState;
    }
    public void setCopyState(){
        currentState = copyState;
    }
    public void setPasteState(){
        currentState = pasteState;
    }
    public void setRotateLeftState() { currentState = rotateLeftState; }
}
