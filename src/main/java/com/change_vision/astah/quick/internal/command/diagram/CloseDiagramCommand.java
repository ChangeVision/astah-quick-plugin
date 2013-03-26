package com.change_vision.astah.quick.internal.command.diagram;

import static java.lang.String.format;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.command.CandidateSupportCommand;
import com.change_vision.astah.quick.command.annotations.Immediate;
import com.change_vision.astah.quick.command.candidates.DiagramCandidate;
import com.change_vision.astah.quick.internal.Messages;
import com.change_vision.astah.quick.internal.command.AstahCommandIconDescription;
import com.change_vision.astah.quick.internal.command.ResourceCommandIconDescription;
import com.change_vision.jude.api.inf.model.IDiagram;
import com.change_vision.jude.api.inf.view.IconDescription;

public class CloseDiagramCommand implements CandidateSupportCommand{
    
    private final class CurrentDiagramCandidate extends DiagramCandidate {
        private CurrentDiagramCandidate(IDiagram diagram) {
            super(diagram);
        }

        @Override
        public String getName() {
            String message = format(Messages.getString("CloseDiagramCommand.current_diagram_name"), super.getName()); //$NON-NLS-1$
            return message;
        }
    }
    
    @Immediate
    private final class AllDiagramsCandidate implements Candidate {

        @Override
        public String getName() {
            return Messages.getString("CloseDiagramCommand.all_diagram_name");
        }

        @Override
        public String getDescription() {
            return Messages.getString("CloseDiagramCommand.all_diagram_description");
        }

        @Override
        public boolean isEnabled() {
            return true;
        }

        @Override
        public CandidateIconDescription getIconDescription() {
            return new AstahCommandIconDescription(IconDescription.PROJECT);
        }
    }

    private DiagramAPI api = new DiagramAPI();

    @Override
    public String getName() {
        return "close diagram"; //$NON-NLS-1$
    }

    @Override
    public String getDescription() {
        return Messages.getString("CloseDiagramCommand.description"); //$NON-NLS-1$
    }

    @Override
    public boolean isEnabled() {
        return api.isOpenedProject() && api.isOpenDiagrams();
    }

    @Override
    public CandidateIconDescription getIconDescription() {
        return new ResourceCommandIconDescription("/icons/glyphicons_207_remove_2.png"); //$NON-NLS-1$

    }

    @Override
    public void execute(String... args) {
        if(args.length == 0){
            api.closeCurrentDiagram();
        }
    }

    @Override
    public Candidate[] candidate(Candidate[] committed,String searchKey) {
        IDiagram[] openDiagrams = api.openDiagrams();
        Candidate[] candidates = new Candidate[openDiagrams.length + 2];
        candidates[0] = new CurrentDiagramCandidate(api.getCurrentDiagram());
        candidates[1] = new AllDiagramsCandidate();
        for (int i = 0; i < openDiagrams.length; i++) {
            candidates[i + 2] = new DiagramCandidate(openDiagrams[i]);
        }
        return candidates;
    }

    @Override
    public void execute(Candidate[] candidates) {
        if (candidates == null) {
            api.closeCurrentDiagram();
            return;
        }
        for (Candidate candidate : candidates) {
            if (candidate instanceof AllDiagramsCandidate) {
                api.closeAll();
                return;
            }
            if (candidate instanceof DiagramCandidate) {
                DiagramCandidate diagramCandidate = (DiagramCandidate) candidate;
                IDiagram diagram = diagramCandidate.getDiagram();
                api.close(diagram);
            }
        }
    }

}
