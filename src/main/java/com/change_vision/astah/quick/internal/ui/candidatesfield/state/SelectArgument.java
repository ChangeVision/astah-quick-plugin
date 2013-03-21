package com.change_vision.astah.quick.internal.ui.candidatesfield.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidatesProvider;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.command.CommandBuilder;

public class SelectArgument implements CandidateState {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(SelectArgument.class);

    private CommandBuilder commandBuilder;

    public SelectArgument(CommandBuilder commandBuilder) {
        this.commandBuilder = commandBuilder;
    }

    @Override
    public Candidate[] filter(String key) {
        logger.trace("candidates:{}", key);
        Candidate[] candidates;
        Command committed = commandBuilder.getCommand();
        if (committed instanceof CandidatesProvider) {
            CandidatesProvider provider = (CandidatesProvider) committed;
            Candidate[] committedCandidates = commandBuilder.getCandidates();
            candidates = provider.candidate(committedCandidates,key);
            if (candidates == null) {
                return new Candidate[]{
                        new NotFound()
                };
            }
            return candidates;
        } else {
            return new Candidate[] {
                    new ValidState(committed,key)
            };
        }
    }

}
