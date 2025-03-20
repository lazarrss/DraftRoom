package raf.draft.dsw.serializer;

import raf.draft.dsw.model.structures.Project;
import raf.draft.dsw.model.structures.Room;

import java.io.IOException;

public interface Serializer {
    void serialize(Project project) throws IOException;
    void saveTemplate(Room room) throws IOException;
    Project deserialize(String path) throws IOException;
    Room loadTemplate(String path) throws IOException;
}
