package com.change_vision.astah.quick.command.candidates;

import static java.lang.String.format;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.Messages;

public class InvalidState implements Candidate {

    private Command command;
    
    private String message;
    
    public InvalidState(Command command,String message) {
        this.command = command;
        this.message = message;
    }

    @Override
    public String getName() {
        return format(Messages.getString("InvalidState.name"),command.getName()); //$NON-NLS-1$
    }

    @Override
    public String getDescription() {
        return message;
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