package raf.draft.dsw.model.structures;

import com.fasterxml.jackson.annotation.JsonTypeName;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.DraftNodeComposite;
@JsonTypeName("projectExplorer")

public class ProjectExplorer extends DraftNodeComposite {
    public ProjectExplorer(String name, DraftNode parent) {
        super(name, parent);
    }

    @Override
    public void removeFromTree() {}

    public boolean childNameTaken(String name){
        for (DraftNode node : this.getChildren()) {
            if (node.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    @Override
    protected boolean validChild(DraftNode draftNode) {
        return draftNode instanceof Project;
    }
}
