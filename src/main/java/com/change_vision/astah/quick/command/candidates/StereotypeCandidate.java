package com.change_vision.astah.quick.command.candidates;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.internal.command.ResourceCommandIconDescription;

public class StereotypeCandidate implements Candidate {

    private String key;

    public StereotypeCandidate(String key) {
        this.key = key;
    }

    @Override
    public String getName() {
        return key;
    }

    @Override
    public String getDescription() {
        return "stereotype: " + key;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public CandidateIconDescription getIconDescription() {
        return new ResourceCommandIconDescription("/icons/glyphicons_117_embed.png");
    }

}
