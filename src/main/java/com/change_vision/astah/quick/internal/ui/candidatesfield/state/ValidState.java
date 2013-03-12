package com.change_vision.astah.quick.internal.ui.candidatesfield.state;

import static java.lang.String.format;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.command.Command;

public class ValidState implements Candidate {

    private Command command;
    
    private String args;
    public ValidState(Command command,String args) {
        this.command = command;
        this.args = args;
    }

    @Override
    public String getName() {
        return format("Execute '%s' '%s'",command.getName(), args);
    }

    @Override
    public String getDescription() {
        return "Execute specified command";
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public CandidateIconDescription getIconDescription() {
        return command.getIconDescription();
    }

}