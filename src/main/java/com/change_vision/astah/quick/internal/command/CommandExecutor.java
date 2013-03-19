package com.change_vision.astah.quick.internal.command;

import java.beans.PropertyChangeListener;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidateAndArgumentSupportCommand;
import com.change_vision.astah.quick.command.CandidateSupportCommand;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.command.exception.ExecuteCommandException;
import com.change_vision.astah.quick.internal.exception.UncommitedCommandExcepition;

public class CommandExecutor {
    public static final String SEPARATE_COMMAND_CHAR = " "; //$NON-NLS-1$
    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(CommandExecutor.class);

    private CommandBuilder builder = new CommandBuilder();

    public Command getCommand() {
        return builder.getCommand();
    }

    public void commit(Command command) {
        builder.commit(command);
    }

    public boolean isCommited() {
        return builder.isCommitted();
    }

    public void add(Candidate candidate) {
        this.builder.add(candidate);
    }

    public boolean remove(Candidate candidate) {
        return builder.remove(candidate);
    }

    public void execute(String candidateText) throws UncommitedCommandExcepition, ExecuteCommandException {
        logger.trace("execute:'{}'",candidateText); //$NON-NLS-1$
        if (builder.isUncommited()) throw new UncommitedCommandExcepition();
        candidateText = candidateText.trim();
        candidateText = candidateText.replaceAll("\\s+", SEPARATE_COMMAND_CHAR); //$NON-NLS-1$
        doExcecute(candidateText);
        reset();
    }

    private void doExcecute(String candidateText) throws ExecuteCommandException {
        Command command = builder.getCommand();
        Candidate[] candidates = builder.getCandidates();
        if (candidates.length == 0) {
            if (builder.isImmediateCommand()) {
                command.execute();
                return;
            }
            executeByArguments(command, candidateText);
            return;
        }
        if (builder.isSupportedCandidateAndArgument()) {
            executeByCandidatesAndArguments(command, candidates, candidateText);
            return;
        }
        if (builder.isSupportedCandidate()) {
            executreByCandidates(command, candidates);
            return;
        }
        throw new IllegalStateException("candidates are not supported.");
    }


    private void executreByCandidates(Command command,Candidate[] candidates) throws ExecuteCommandException {
        CandidateSupportCommand candidateCommand = (CandidateSupportCommand) command;
        candidateCommand.execute(candidates);
    }

    private void executeByCandidatesAndArguments(Command command,Candidate[] candidates, String candidateText) throws ExecuteCommandException{
        String commandName = command.getName();
        String[] commandWords = commandName.split(SEPARATE_COMMAND_CHAR);
        String[] candidateWords = candidateText.split(SEPARATE_COMMAND_CHAR);
        int commitedLength = commandWords.length + candidates.length;
        String[] args = calcArgs(candidateText, commandName, candidateWords, commitedLength);

        CandidateAndArgumentSupportCommand argumentCommand = (CandidateAndArgumentSupportCommand) command;
        argumentCommand.execute(candidates,args);
    }

    private void executeByArguments(Command command,String candidateText) throws ExecuteCommandException {
        String commandName = command.getName();
        String[] commandWords = commandName.split(SEPARATE_COMMAND_CHAR);
        String[] candidateWords = candidateText.split(SEPARATE_COMMAND_CHAR);
        int commitedLength = commandWords.length;
        String[] args = calcArgs(candidateText, commandName, candidateWords, commitedLength);
        command.execute(args);
    }

    private String[] calcArgs(String candidateText, String commandName, String[] candidateWords,
            int commitedLength) {
        logger.trace("text:{},words:{}",candidateText,candidateWords); //$NON-NLS-1$
        String[] args = new String[]{};
        if (!candidateText.equals(commandName)) {
            args = Arrays.copyOfRange(candidateWords, commitedLength, candidateWords.length);
        }
        return args;
    }

    public String getCommandText() {
        if (builder.isUncommited()) {
            return ""; //$NON-NLS-1$
        }
        Command command = builder.getCommand();
        StringBuilder textBuilder = new StringBuilder(command.getName());
        Candidate[] candidates = builder.getCandidates();
        for (Candidate candidate : candidates) {
            textBuilder.append(SEPARATE_COMMAND_CHAR);
            textBuilder.append(candidate.getName());
        }
        return textBuilder.toString();
    }

    public String getCandidateText(String candidateText) {
        if (builder.isUncommited()) {
            return candidateText;
        }
        Command command = builder.getCommand();
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
        return ""; //$NON-NLS-1$
    }

    public void reset() {
        builder.reset();
    }
    
    public Candidate[] getCandidates() {
        return builder.getCandidates();
    }

    public boolean isValid() {
        return isCommited();
    }

    public Candidate removeCandidate() {
        return builder.removeCandidate();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        builder.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        builder.removePropertyChangeListener(listener);
    }
    
}
