package com.change_vision.astah.quick.internal.ui.candidatesfield.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidatesProvider;
import com.change_vision.astah.quick.command.Command;

public class SelectArgument implements CandidateState {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(SelectArgument.class);

    private Command committed;

    public SelectArgument(Command committed) {
        this.committed = committed;
    }

    @Override
    public Candidate[] filter(String searchKey) {
        logger.trace("candidates:{}", searchKey);
        Candidate[] candidates;
        if (committed instanceof CandidatesProvider) {
            CandidatesProvider provider = (CandidatesProvider) committed;
            candidates = provider.candidate(searchKey);
        } else {
            candidates = new Candidate[] {};
        }
        return candidates;
    }

    public Command currentCommand() {
        return committed;
    }

}
