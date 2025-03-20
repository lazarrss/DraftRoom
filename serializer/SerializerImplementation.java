package raf.draft.dsw.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import raf.draft.dsw.controller.filecontrollers.RoomController;
import raf.draft.dsw.core.ApplicationFramework;
import raf.draft.dsw.gui.swing.view.MainFrame;
import raf.draft.dsw.model.messages.Message;
import raf.draft.dsw.model.messages.MessageType;
import raf.draft.dsw.model.structures.Project;
import raf.draft.dsw.model.structures.Room;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class SerializerImplementation implements Serializer{
    private ObjectMapper objectMapper;
    public SerializerImplementation(){
        this.objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Color.class, new ColorSerializer());
        module.addDeserializer(Color.class, new ColorDeserializer());
        objectMapper.registerModule(module);
    }

    @Override
    public void serialize(Project project) throws IOException {
        objectMapper.writeValue(new File(project.getPathToFile()), project);
    }

    @Override
    public Project deserialize(String path) throws IOException {
        try {
            return objectMapper.readValue(new File(path), Project.class);
        } catch (IOException e) {
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(e.getMessage(), MessageType.ERROR);
            throw e;
        }
    }

    @Override
    public void saveTemplate(Room room) throws IOException {
        objectMapper.writeValue(new File(RoomController.dest + "/" + room.getName()), room);
    }

    @Override
    public Room loadTemplate(String path) throws IOException {
        try {
            return objectMapper.readValue(new File(path), Room.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
