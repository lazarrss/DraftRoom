package raf.draft.dsw.controller.observer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import raf.draft.dsw.model.messages.Message;
import raf.draft.dsw.model.messages.MessageType;
import raf.draft.dsw.model.nodes.DraftNode;

public interface ISubscriber {
    @JsonIgnore

    void update(Message message);
}
