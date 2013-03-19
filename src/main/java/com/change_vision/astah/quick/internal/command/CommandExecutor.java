package com.change_vision.astah.quick.internal.command;

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

    public void execute(CommandBuilder builder,String candidateText) throws UncommitedCommandExcepition, ExecuteCommandException {
        logger.trace("execute:'{}'",candidateText); //$NON-NLS-1$
        if (builder.isUncommited()) throw new UncommitedCommandExcepition();
        candidateText = candidateText.trim();
        candidateText = candidateText.replaceAll("\\s+", SEPARATE_COMMAND_CHAR); //$NON-NLS-1$
        doExcecute(builder,candidateText);
        builder.reset();
    }

    private void doExcecute(CommandBuilder builder,String candidateText) throws ExecuteCommandException {
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

    public boolean isValid(CommandBuilder builder) {
        return builder.isCommitted();
    }
    
}
