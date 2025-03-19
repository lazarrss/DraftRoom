package raf.draft.dsw.controller.messagegenerator;

import raf.draft.dsw.model.messages.Message;
import raf.draft.dsw.model.messages.MessageType;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class FileLogger implements Logger{

    private File f = new File("src/main/resources/log.txt");
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;


    public FileLogger(MessageGenerator messageGenerator){
        try {
            fileWriter = new FileWriter(f, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        bufferedWriter = new BufferedWriter(fileWriter);
        messageGenerator.addSubscriber(this);
    }
    @Override
    public void update(Message message) {
        printMessage(message);
    }

    @Override
    public void printMessage(Message message) {
        if(f == null) {
            JOptionPane.showMessageDialog(null, "Pogresna putanja", "Nece",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            try {
                bufferedWriter.write(message.toString().concat("\n"));
                bufferedWriter.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
