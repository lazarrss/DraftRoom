package raf.draft.dsw.controller.messagegenerator;

import raf.draft.dsw.controller.observer.IPublisher;
import raf.draft.dsw.controller.observer.ISubscriber;
import raf.draft.dsw.model.messages.Message;
import raf.draft.dsw.model.messages.MessageType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MessageGenerator implements IPublisher {
    private List<ISubscriber> subscriberList = new ArrayList<>();

    public Message generateMessage(String content, MessageType messageType) {
        Message message = new Message(content, messageType, LocalDateTime.now());
        this.notifySubscribers(message);
        return message;
    }

    @Override
    public void addSubscriber(ISubscriber subscriber) {
        if(!subscriberList.contains(subscriber))
            subscriberList.add(subscriber);
    }

    @Override
    public void removeSubscriber(ISubscriber subscriber) {
        subscriberList.remove(subscriber);
    }

    @Override
    public void notifySubscribers(Message message) {
        for(ISubscriber iSubscriber : subscriberList)
            iSubscriber.update(message);
    }
}
