package raf.draft.dsw.gui.swing.view.painters;

import raf.draft.dsw.gui.swing.view.painters.concrete.*;
import raf.draft.dsw.model.structures.roomelements.RoomElement;
import raf.draft.dsw.model.structures.roomelements.concrete.*;

public class RoomElementPainterFactory {
    public static RoomElementPainter getPainter(RoomElement roomElement){
        return switch (roomElement) {
            case Bojler ignored -> new BojlerPainter(roomElement);
            case Kada ignored -> new KadaPainter(roomElement);
            case Krevet ignored -> new KrevetPainter(roomElement);
            case Lavabo ignored -> new LavaboPainter(roomElement);
            case Ormar ignored -> new OrmarPainter(roomElement);
            case Sto ignored -> new StoPainter(roomElement);
            case VesMasina ignored -> new VesMasinaPainter(roomElement);
            case Vrata ignored -> new VrataPainter(roomElement);
            case WCSolja ignored ->  new WCSoljaPainter(roomElement);
            default -> null;
        };
    }
}
