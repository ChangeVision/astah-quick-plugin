package com.change_vision.astah.quick.internal.command.diagram;

import static java.lang.String.format;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.command.CandidateSupportCommand;
import com.change_vision.astah.quick.command.CommittedNameTrimer;
import com.change_vision.astah.quick.command.annotations.Immediate;
import com.change_vision.astah.quick.command.candidates.ElementCandidate;
import com.change_vision.astah.quick.command.candidates.InvalidState;
import com.change_vision.astah.quick.command.candidates.NotFound;
import com.change_vision.astah.quick.command.exception.ExecuteCommandException;
import com.change_vision.astah.quick.internal.Messages;
import com.change_vision.astah.quick.internal.annotations.TestForMethod;
import com.change_vision.astah.quick.internal.command.AstahCommandIconDescription;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.model.IUseCaseDiagram;
import com.change_vision.jude.api.inf.view.IconDescription;

public class CreateUseCaseDiagramCommand implements CandidateSupportCommand {
    
    private CommittedNameTrimer trimer = new CommittedNameTrimer();

    @Immediate
    class UseCaseDiagramCandidate implements Candidate {

        private final ElementCandidate owner;
        private final String name;

        public UseCaseDiagramCandidate(ElementCandidate owner, String name) {
            this.owner = owner;
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getDescription() {
            String description = format(Messages.getString("CreateUseCaseDiagramCommand.UseCaseDiagramCandidate.description"), owner.getName()); //$NON-NLS-1$
            return description;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }

        @Override
        public CandidateIconDescription getIconDescription() {
            return new AstahCommandIconDescription(IconDescription.UML_DGM_USECASE);
        }

    }

    private DiagramAPI api = new DiagramAPI();
    
    @Override
    public void execute(String... args) throws ExecuteCommandException {
    }

    @Override
    public String getName() {
        return "create usecase diagram"; //$NON-NLS-1$
    }

    @Override
    public String getDescription() {
        return Messages.getString("CreateUseCaseDiagramCommand.description"); //$NON-NLS-1$
    }

    @Override
    public boolean isEnabled() {
        return api.isOpenedProject();
    }

    @Override
    public CandidateIconDescription getIconDescription() {
        return new AstahCommandIconDescription(IconDescription.UML_DGM_USECASE);
    }

    @Override
    public Candidate[] candidate(Candidate[] committed, String searchKey) {
        if (committed == null || committed.length == 0) {
            return findOwner(searchKey);
        }
        String name = trimer.trim(committed,searchKey);
        if (name.isEmpty()) {
            return new Candidate[]{
                    new InvalidState(this,Messages.getString("CreateUseCaseDiagramCommand.invalid_message")) //$NON-NLS-1$
            };
        }
        Candidate owner = committed[0];
        if ( ! (owner instanceof ElementCandidate)) {
            throw new IllegalArgumentException("owner is not ElementCandidate");
        }
        ElementCandidate elementCandidate = (ElementCandidate) owner;
        return new Candidate[]{
                new UseCaseDiagramCandidate(elementCandidate,name)
        };
    }

    private Candidate[] findOwner(String key) {
        Candidate[] candidates = new Candidate[0];
        INamedElement[] founds = api.findClassOrPackage(key);
        if (founds.length == 0) {
            return new Candidate[]{
                    new NotFound()
            };
        }
        candidates = new Candidate[founds.length];
        for (int i = 0; i < founds.length; i++) {
            INamedElement element = founds[i];
            candidates[i] = new ElementCandidate(element);
        }
        return candidates;
    }

    @Override
    public void execute(Candidate[] candidates) throws ExecuteCommandException {
        if (candidates.length != 2) {
            throw new IllegalArgumentException(Messages.getString("CreateUseCaseDiagramCommand.argument_length_error")); //$NON-NLS-1$
        }
        INamedElement owner;
        if ( (candidates[0] instanceof ElementCandidate) == false) {
            String message = format(Messages.getString("CreateUseCaseDiagramCommand.candidate_error"),candidates[0]); //$NON-NLS-1$
            throw new IllegalArgumentException(message);
        }
        ElementCandidate ownerCandidate = (ElementCandidate) candidates[0];
        owner = ownerCandidate.getElement();
        Candidate nameCandidate = candidates[1];
        IUseCaseDiagram diagram = api.createUseCaseDiagram(owner,nameCandidate.getName());
        api.open(diagram);
    }
    
    @TestForMethod
    void setApi(DiagramAPI api) {
        this.api = api;
    }

}
