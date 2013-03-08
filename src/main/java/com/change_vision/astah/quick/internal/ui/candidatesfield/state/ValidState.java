package com.change_vision.astah.quick.internal.ui.candidatesfield.state;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.internal.command.ResourceCommandIconDescription;

public class ValidState implements Candidate {

    private String args;

    public ValidState(String args) {
        this.args = args;
    }

    @Override
    public String getName() {
        return "Execute Command '" + args + "'";
    }

    @Override
    public String getDescription() {
        return "Execute Command";
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public CandidateIconDescription getIconDescription() {
        return new ResourceCommandIconDescription("/icons/astah_icon_professional.png");
    }

}