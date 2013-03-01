package com.change_vision.astah.quick.internal.command.diagram;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.command.CandidatesProvider;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.command.ResourceCommandIconDescription;
import com.change_vision.jude.api.inf.model.IDiagram;

public class CloseDiagramCommand implements Command,CandidatesProvider{
    
    private DiagramAPI api = new DiagramAPI();

    @Override
    public String getName() {
        return "close diagram";
    }

    @Override
    public String getDescription() {
        return "close specified diagram or current one if no one specified.";
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
        if(args.length == 0){
            api.closeCurrentDiagram();
        }
    }

    @Override
    public Candidate[] candidate(String searchKey) {
        IDiagram[] openDiagrams = api.openDiagrams();
        Candidate[] candidates = new Candidate[openDiagrams.length + 1];
        candidates[0] = new DiagramCandidate(api.getCurrentDiagram()){
            @Override
            public String getName() {
                return "Current Diagram(" + super.getName() + ")";
            }
        };
        for (int i = 0; i < openDiagrams.length; i++) {
            candidates[i + 1] = new DiagramCandidate(openDiagrams[i]);
        }
        return candidates;
    }

    @Override
    public void execute(Candidate candidate) {
        if (candidate == null || candidate instanceof CloseDiagramCommand) {
            api.closeCurrentDiagram();
            return;
        }
        if (candidate instanceof DiagramCandidate) {
            DiagramCandidate diagramCandidate = (DiagramCandidate) candidate;
            IDiagram diagram = diagramCandidate.getDiagram();
            api.close(diagram);
        }
    }

}
