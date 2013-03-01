package com.change_vision.astah.quick.internal.command.diagram;

import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.command.ResourceCommandIconDescription;

public class CloseCurrentDiagramCommand implements Command{
    
    private DiagramAPI api = new DiagramAPI();

    @Override
    public String getName() {
        return "close current diagram";
    }

    @Override
    public String getDescription() {
        return "close current editor diagram";
    }

    @Override
    public boolean isEnabled() {
        return api.isOpenedProject() && api.isOpenDiagrams();
    }

    @Override
    public CandidateIconDescription getIconDescription() {
        return new ResourceCommandIconDescription("/icons/glyphicons_207_remove_2.png");

    }

    @Override
    public void execute(String... args) {
        api.closeCurrentDiagram();
    }

}
