package com.change_vision.astah.quick.command.candidates;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.command.annotations.Immediate;
import com.change_vision.astah.quick.internal.command.AstahCommandIconDescription;
import com.change_vision.jude.api.inf.view.IconDescription;

@Immediate
public class PrimitiveCandidate implements Candidate {

    private String key;

    public PrimitiveCandidate(String key) {
        this.key = key;
    }

    @Override
    public String getName() {
        return key;
    }

    @Override
    public String getDescription() {
        return "primitive: " + key;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public CandidateIconDescription getIconDescription() {
        return new AstahCommandIconDescription(IconDescription.UML_CLASS_OPE);
    }

}
