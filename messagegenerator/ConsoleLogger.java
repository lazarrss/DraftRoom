package raf.draft.dsw.controller.messagegenerator;

import raf.draft.dsw.model.messages.Message;
import raf.draft.dsw.model.messages.MessageType;

import java.time.LocalDateTime;

public class ConsoleLogger implements Logger{
    public ConsoleLogger(MessageGenerator messageGenerator){
        messageGenerator.addSubscriber(this);
    }
    @Override
    public void update(Message message) {
        printMessage(message);
    }

    @Override
    public void printMessage(Message message) {
        System.out.println(message);
    }

}
