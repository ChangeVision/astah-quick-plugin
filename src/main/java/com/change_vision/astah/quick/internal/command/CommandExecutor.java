package com.change_vision.astah.quick.internal.command;

import java.util.ArrayList;
import java.util.List;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidateSupportCommand;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.command.exception.UncommitedCommandExcepition;

public class CommandExecutor {

    private Command command;
    private List<Candidate> candidates = new ArrayList<Candidate>();

    public Command getCommand() {
        return command;
    }

    public void commit(Command command) {
        this.command = command;
    }

    public boolean isCommited() {
        return command != null;
    }

    public void add(Candidate candidate) {
        this.candidates.add(candidate);
    }

    public void execute() throws UncommitedCommandExcepition{
        if (isUncommited()) throw new UncommitedCommandExcepition();
        if (candidates.isEmpty()) {
            command.execute();
            return;
        }
        if (command instanceof CandidateSupportCommand) {
            CandidateSupportCommand candidateCommand = (CandidateSupportCommand) command;
            candidateCommand.execute(candidates.toArray(new Candidate[]{}));
        }
    }

    private boolean isUncommited() {
        return command == null;
    }

}
