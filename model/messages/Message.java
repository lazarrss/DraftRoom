package raf.draft.dsw.model.messages;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Message<T> {
    private String content;
    private MessageType messageType;
    private LocalDateTime messageDateTime;
    private T object;

    public Message(String content, MessageType messageType, LocalDateTime messageDateTime) {
        this.content = content;
        this.messageType = messageType;
        this.messageDateTime = messageDateTime;
    }

    public Message(String content, MessageType messageType, LocalDateTime messageDateTime, T object) {
        this.content = content;
        this.messageType = messageType;
        this.messageDateTime = messageDateTime;
        this.object = object;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public LocalDateTime getMessageDateTime() {
        return messageDateTime;
    }

    public void setMessageDateTime(LocalDateTime messageDateTime) {
        this.messageDateTime = messageDateTime;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return STR."[\{content}] [\{messageType}] [\{messageDateTime}\{']'}";
    }
}
