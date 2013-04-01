package com.change_vision.astah.quick.command.candidates;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.internal.Messages;
import com.change_vision.astah.quick.internal.command.ResourceCommandIconDescription;

public class NotFound implements Candidate {

    @Override
    public String getName() {
        return "Not Found"; //$NON-NLS-1$
    }

    @Override
    public String getDescription() {
        return Messages.getString("NotFound.description"); //$NON-NLS-1$
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public CandidateIconDescription getIconDescription() {
        return new ResourceCommandIconDescription("/icons/glyphicons_207_remove_2.png"); //$NON-NLS-1$
    }

}