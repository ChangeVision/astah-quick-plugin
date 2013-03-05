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
            executeByArguments(candidateText);
            return;
        }
        if (command instanceof CandidateAndArgumentSupportCommand) {
            executeByCandidatesAndArguments(candidateText);
            return;
        }
        if (command instanceof CandidateSupportCommand) {
            executreByCandidates();
            return;
        }
    }

    private void executreByCandidates() {
        Candidate[] arguments = candidates.toArray(new Candidate[]{});

        CandidateSupportCommand candidateCommand = (CandidateSupportCommand) command;
        candidateCommand.execute(arguments);
    }

    private void executeByCandidatesAndArguments(String candidateText) {
        Candidate[] candidateArguments = candidates.toArray(new Candidate[]{});
        
        String commandName = command.getName();
        String[] commandWords = commandName.split(SEPARATE_COMMAND_CHAR);
        String[] candidateWords = candidateText.split(SEPARATE_COMMAND_CHAR);
        int commitedLength = commandWords.length + candidates.size();
        String[] args = calcArgs(candidateText, commandName, candidateWords, commitedLength);

        CandidateAndArgumentSupportCommand argumentCommand = (CandidateAndArgumentSupportCommand) command;
        argumentCommand.execute(candidateArguments,args);
    }

    private void executeByArguments(String candidateText) {
        String commandName = command.getName();
        String[] commandWords = commandName.split(SEPARATE_COMMAND_CHAR);
        String[] candidateWords = candidateText.split(SEPARATE_COMMAND_CHAR);
        int commitedLength = commandWords.length;
        String[] args = calcArgs(candidateText, commandName, candidateWords, commitedLength);
        command.execute(args);
    }

    private String[] calcArgs(String candidateText, String commandName, String[] candidateWords,
            int commitedLength) {
        String[] args = new String[]{};
        if (!candidateText.equals(commandName)) {
            args = Arrays.copyOfRange(candidateWords, commitedLength, candidateWords.length);
        }
        return args;
    }

    private boolean isUncommited() {
        return command == null;
    }

    public boolean remove(Candidate candidate) {
        return candidates.remove(candidate);
    }

}
