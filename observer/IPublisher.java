package raf.draft.dsw.controller.observer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import raf.draft.dsw.model.messages.Message;
import raf.draft.dsw.model.messages.MessageType;

import java.util.concurrent.Flow;

public interface IPublisher {
    @JsonIgnore

    void addSubscriber(ISubscriber subscriber);
    @JsonIgnore

    void removeSubscriber(ISubscriber subscriber);
    @JsonIgnore

    void notifySubscribers(Message message);
}
