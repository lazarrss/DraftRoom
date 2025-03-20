package raf.draft.dsw.model.repository;

import raf.draft.dsw.model.structures.ProjectExplorer;

public class DraftRoomRepositoryImplementation implements DraftRoomRepository{

    private final ProjectExplorer projectExplorer;
    public DraftRoomRepositoryImplementation(){
        projectExplorer = new ProjectExplorer("Project Explorer", null);
    }
    @Override
    public ProjectExplorer getRoot() {
        return projectExplorer;
    }
}
