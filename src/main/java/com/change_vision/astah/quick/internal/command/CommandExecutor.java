package com.change_vision.astah.quick.internal.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidateAndArgumentSupportCommand;
import com.change_vision.astah.quick.command.CandidateSupportCommand;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.command.exception.UncommitedCommandExcepition;

public class CommandExecutor {

    private Command command;
    private List<Candidate> candidates = new ArrayList<Candidate>();
    private static final String SEPARATE_COMMAND_CHAR = " ";

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

    public void execute(String candidateText) throws UncommitedCommandExcepition{
        if (isUncommited()) throw new UncommitedCommandExcepition();
        candidateText = candidateText.trim();
        if (candidates.isEmpty()) {
            String commandName = command.getName();
            String[] args = new String[]{};
            if (!candidateText.equals(commandName)) {
                String[] commandWords = commandName.split(SEPARATE_COMMAND_CHAR);
                String[] candidateWords = candidateText.split(SEPARATE_COMMAND_CHAR);
                args = Arrays.copyOfRange(candidateWords, commandWords.length, candidateWords.length);
            }
            command.execute(args);
            return;
        }
        if (command instanceof CandidateAndArgumentSupportCommand) {
            CandidateAndArgumentSupportCommand argumentCommand = (CandidateAndArgumentSupportCommand) command;
            Candidate[] candidateArguments = candidates.toArray(new Candidate[]{});
            String commandName = command.getName();
            String[] args = new String[]{};
            if (!candidateText.equals(commandName)) {
                String[] commandWords = commandName.split(SEPARATE_COMMAND_CHAR);
                String[] candidateWords = candidateText.split(SEPARATE_COMMAND_CHAR);
                int commitedLength = commandWords.length + candidates.size();
                args = Arrays.copyOfRange(candidateWords, commitedLength, candidateWords.length);
            }
            argumentCommand.execute(candidateArguments,args);
            return;
        }
        if (command instanceof CandidateSupportCommand) {
            CandidateSupportCommand candidateCommand = (CandidateSupportCommand) command;
            Candidate[] arguments = candidates.toArray(new Candidate[]{});
            candidateCommand.execute(arguments);
            return;
        }
    }

    private boolean isUncommited() {
        return command == null;
    }

    public boolean remove(Candidate candidate) {
        return candidates.remove(candidate);
    }

}
