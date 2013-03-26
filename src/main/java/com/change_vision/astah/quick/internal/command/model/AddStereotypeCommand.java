package com.change_vision.astah.quick.internal.command.model;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidateAndArgumentSupportCommand;
import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.command.CommittedNameTrimer;
import com.change_vision.astah.quick.command.candidates.ElementCandidate;
import com.change_vision.astah.quick.command.candidates.NotFound;
import com.change_vision.astah.quick.command.candidates.StereotypeCandidate;
import com.change_vision.astah.quick.command.exception.ExecuteCommandException;
import com.change_vision.astah.quick.internal.Messages;
import com.change_vision.astah.quick.internal.annotations.TestForMethod;
import com.change_vision.astah.quick.internal.command.ResourceCommandIconDescription;
import com.change_vision.astah.quick.internal.exception.NotImplementationException;
import com.change_vision.jude.api.inf.model.INamedElement;

public class AddStereotypeCommand implements CandidateAndArgumentSupportCommand {
    
    private ModelAPI api = new ModelAPI();
    
    private CommittedNameTrimer trimer = new CommittedNameTrimer();
    
    private final StereotypeCandidates definedStereotypes = new StereotypeCandidates();


    @Override
    public String getName() {
        return "add stereotype"; //$NON-NLS-1$
    }

    @Override
    public String getDescription() {
        return Messages.getString("AddStereotypeCommand.description"); //$NON-NLS-1$
    }

    @Override
    public boolean isEnabled() {
        return api.isOpenedProject();
    }

    @Override
    public CandidateIconDescription getIconDescription() {
        return new ResourceCommandIconDescription("/icons/glyphicons_117_embed.png"); //$NON-NLS-1$
    }

    @Override
    public void execute(String... args) throws ExecuteCommandException {
        throw new NotImplementationException();
    }

    @Override
    public Candidate[] candidate(Candidate[] committeds,String searchKey) {
        if (searchKey.isEmpty()) {
            if (committeds == null || committeds.length == 0) {
                return findTargetElement(searchKey);
            }
            return new Candidate[0];
        }
        String key = trimer.trim(committeds,searchKey);
        if (committeds.length == 0) {
            return findTargetElement(key);
        }
        return findStereotypes(key);
    }

    private Candidate[] findStereotypes(String key) {
        StereotypeCandidate[] found = definedStereotypes.find(key);
        if (found.length > 0) {
            return found;
        }
        return new Candidate[]{
                new StereotypeCandidate(key)
        };
    }

    private Candidate[] findTargetElement(String key) {
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
    public void execute(Candidate[] candidates, String[] arguments) throws ExecuteCommandException {
        Candidate target = candidates[0];
        INamedElement element = null;
        if (target instanceof ElementCandidate) {
            ElementCandidate elementCandidate = (ElementCandidate) target;
            element = elementCandidate.getElement();
        }else {
            throw new ExecuteCommandException(Messages.getString("AddStereotypeCommand.element_must_be_set_error_message")); //$NON-NLS-1$
        }
        
        for (int i = 1; i < candidates.length; i++) {
            Candidate candidate = candidates[i];
            if (candidate instanceof StereotypeCandidate) {
                StereotypeCandidate stereotype = (StereotypeCandidate) candidate;
                api.addStereotype(element, stereotype.getName());
            }
        }
    }
    
    @TestForMethod
    void setAPI(ModelAPI api) {
        this.api = api;
    }

}
