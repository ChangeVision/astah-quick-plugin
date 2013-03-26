package com.change_vision.astah.quick.internal.command.model;

import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.command.annotations.Immediate;
import com.change_vision.astah.quick.command.annotations.LooseName;
import com.change_vision.astah.quick.command.candidates.ElementCandidate;
import com.change_vision.jude.api.inf.model.INamedElement;

@Immediate
@LooseName
public class SelectModelCommand implements Command {

    private final ElementCandidate candidate;
    private ModelAPI api = new ModelAPI();

    public SelectModelCommand(INamedElement foundModel) {
        this.candidate = new ElementCandidate(foundModel);
    }

    @Override
    public String getName() {
        return candidate.getName();
    }

    @Override
    public void execute(String... args) {
        INamedElement element = candidate.getElement();
        api.showInStructureTree(element);
    }

    @Override
    public String getDescription() {
        return candidate.getDescription();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public CandidateIconDescription getIconDescription() {
        return candidate.getIconDescription();
    }

    public void setApi(ModelAPI api) {
        this.api = api;
    }

    public INamedElement getElement() {
        return candidate.getElement();
    }
}
