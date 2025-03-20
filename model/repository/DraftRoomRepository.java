package raf.draft.dsw.model.repository;

import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.structures.ProjectExplorer;

public interface DraftRoomRepository {
    ProjectExplorer getRoot();
}
