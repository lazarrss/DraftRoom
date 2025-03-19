package raf.draft.dsw.controller.messagegenerator;

import raf.draft.dsw.controller.observer.ISubscriber;
import raf.draft.dsw.model.messages.Message;
import raf.draft.dsw.model.messages.MessageType;

public interface Logger extends ISubscriber {
    void printMessage(Message message);
}
