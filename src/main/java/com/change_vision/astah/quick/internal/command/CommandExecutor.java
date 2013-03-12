package com.change_vision.astah.quick.internal.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidateAndArgumentSupportCommand;
import com.change_vision.astah.quick.command.CandidateSupportCommand;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.command.annotations.Immediate;
import com.change_vision.astah.quick.command.exception.ExecuteCommandException;
import com.change_vision.astah.quick.internal.exception.UncommitedCommandExcepition;

public class CommandExecutor {
    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(CommandExecutor.class);

    private Command command;
    private List<Candidate> candidates = new ArrayList<Candidate>();
    public static final String SEPARATE_COMMAND_CHAR = " ";

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

    public boolean remove(Candidate candidate) {
        return candidates.remove(candidate);
    }

    public void execute(String candidateText) throws UncommitedCommandExcepition, ExecuteCommandException {
        logger.trace("execute:'{}'",candidateText);
        if (isUncommited()) throw new UncommitedCommandExcepition();
        candidateText = candidateText.trim();
        candidateText = candidateText.replaceAll("\\s+", SEPARATE_COMMAND_CHAR);
        doExcecute(candidateText);
        reset();
    }

    private void doExcecute(String candidateText) throws ExecuteCommandException {
        if (candidates.isEmpty()) {
            if (isImmediateCommand()) {
                command.execute();
                return;
            }
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

    private boolean isImmediateCommand() {
        return command.getClass().isAnnotationPresent(Immediate.class);
    }

    private void executreByCandidates() throws ExecuteCommandException {
        Candidate[] arguments = candidates.toArray(new Candidate[]{});

        CandidateSupportCommand candidateCommand = (CandidateSupportCommand) command;
        candidateCommand.execute(arguments);
    }

    private void executeByCandidatesAndArguments(String candidateText) throws ExecuteCommandException{
        Candidate[] candidateArguments = candidates.toArray(new Candidate[]{});
        
        String commandName = command.getName();
        String[] commandWords = commandName.split(SEPARATE_COMMAND_CHAR);
        String[] candidateWords = candidateText.split(SEPARATE_COMMAND_CHAR);
        int commitedLength = commandWords.length + candidates.size();
        String[] args = calcArgs(candidateText, commandName, candidateWords, commitedLength);

        CandidateAndArgumentSupportCommand argumentCommand = (CandidateAndArgumentSupportCommand) command;
        argumentCommand.execute(candidateArguments,args);
    }

    private void executeByArguments(String candidateText) throws ExecuteCommandException {
        String commandName = command.getName();
        String[] commandWords = commandName.split(SEPARATE_COMMAND_CHAR);
        String[] candidateWords = candidateText.split(SEPARATE_COMMAND_CHAR);
        int commitedLength = commandWords.length;
        String[] args = calcArgs(candidateText, commandName, candidateWords, commitedLength);
        command.execute(args);
    }

    private String[] calcArgs(String candidateText, String commandName, String[] candidateWords,
            int commitedLength) {
        logger.trace("text:{},words:{}",candidateText,candidateWords);
        String[] args = new String[]{};
        if (!candidateText.equals(commandName)) {
            args = Arrays.copyOfRange(candidateWords, commitedLength, candidateWords.length);
        }
        return args;
    }

    private boolean isUncommited() {
        return command == null;
    }

    public String getCommandText() {
        if (isUncommited()) {
            return "";
        }
        StringBuilder builder = new StringBuilder(command.getName());
        for (Candidate candidate : candidates) {
            builder.append(SEPARATE_COMMAND_CHAR);
            builder.append(candidate.getName());
        }
        return builder.toString();
    }

    public String getCandidateText(String candidateText) {
        if (isUncommited()) {
            return candidateText;
        }
        String commandName = command.getName();
        String[] commandWords = commandName.split(SEPARATE_COMMAND_CHAR);
        String[] candidateWords = candidateText.split(SEPARATE_COMMAND_CHAR);
        if (candidateWords.length > commandWords.length) {
            StringBuilder builder = new StringBuilder();
            for(int i = commandWords.length; i < candidateWords.length; i++){
                builder.append(candidateWords[i]);
                builder.append(SEPARATE_COMMAND_CHAR);
            }
            return builder.toString().trim();
        }
        if (candidateText.length() > commandName.length()) {
            return candidateText.substring(commandName.length());
        }
        return "";
    }

    public void reset() {
        this.command = null;
        this.candidates.clear();
    }
    
    public Candidate[] getCandidates() {
        if (candidates.size() == 0) {
            return new Candidate[0];
        }
        return candidates.toArray(new Candidate[0]);
    }

    public boolean isValid() {
        return isCommited();
    }

    public Candidate removeCandidate() {
        if (candidates.size() == 0) {
            if (isCommited()) {
                Command old = this.command;
                this.command = null;
                return old;
            }
            return null;
        }
        return candidates.remove(candidates.size() - 1);
    }

}
