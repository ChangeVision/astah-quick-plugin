package com.change_vision.astah.quick.internal.command;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidateSupportCommand;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.command.exception.UncommitedCommandExcepition;

public class CommandExecutor {

    private Command command;
    private Candidate candidate;

    public Command getCommand() {
        return command;
    }

    public void commit(Command command) {
        this.command = command;
    }

    public boolean isCommited() {
        return command != null;
    }

    public void execute() throws UncommitedCommandExcepition{
        if (isUncommited()) throw new UncommitedCommandExcepition();
        if (candidate == null) {
            command.execute(new String[]{});
            return;
        }
        if (command instanceof CandidateSupportCommand) {
            CandidateSupportCommand candidateCommand = (CandidateSupportCommand) command;
            candidateCommand.execute(candidate);
        }
    }

    private boolean isUncommited() {
        return command == null;
    }

    public void add(Candidate candidate) {
        this.candidate = candidate;
    }

}
