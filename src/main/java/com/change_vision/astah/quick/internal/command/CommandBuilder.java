package com.change_vision.astah.quick.internal.command;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidateAndArgumentSupportCommand;
import com.change_vision.astah.quick.command.CandidateSupportCommand;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.command.annotations.Immediate;

public class CommandBuilder {
    public static final String PROP_OF_COMMAND = "command"; //$NON-NLS-1$
    public static final String PROP_OF_CANDIDATE = "candidate"; //$NON-NLS-1$
    public static final String SEPARATE_COMMAND_CHAR = " "; //$NON-NLS-1$

    private Command command;
    private List<Candidate> candidates = new ArrayList<Candidate>();
    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void commit(Command command){
        if (command == null) throw new IllegalArgumentException("command is null.");//$NON-NLS-1$
        Command old = this.command;
        this.command = command;
        support.firePropertyChange(PROP_OF_COMMAND, old, command);
    }

    public Command getCommand() {
        return this.command;
    }

    public boolean add(Candidate candidate) {
        boolean added = candidates.add(candidate);
        support.fireIndexedPropertyChange(PROP_OF_CANDIDATE, candidates.size() - 1, null, candidate);
        return added;
    }

    public Candidate[] getCandidates() {
        return candidates.toArray(new Candidate[0]);
    }

    public boolean remove(Candidate candidate) {
        int indexOf = candidates.indexOf(candidate);
        boolean removed = candidates.remove(candidate);
        support.fireIndexedPropertyChange(PROP_OF_CANDIDATE, indexOf, candidate, null);
        return removed;
    }

    @Override
    public String toString() {
        return "CommandBuilder [command=" + command + ", candidates=" + candidates + "]";//$NON-NLS-1$
    }

    public Candidate removeCandidate() {
        if (candidates.size() == 0) {
            if (isCommitted()) {
                Command old = this.command;
                this.command = null;
                return old;
            }
            return null;
        }
        return candidates.remove(candidates.size() - 1);
    }

    public String getCandidateText(String candidateText) {
        if (isUncommited()) {
            return candidateText;
        }
        Command command = getCommand();
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
    

    public String getCommandText() {
        if (isUncommited()) {
            return ""; //$NON-NLS-1$
        }
        Command command = getCommand();
        StringBuilder textBuilder = new StringBuilder(command.getName());
        Candidate[] candidates = getCandidates();
        for (Candidate candidate : candidates) {
            textBuilder.append(SEPARATE_COMMAND_CHAR);
            textBuilder.append(candidate.getName());
        }
        return textBuilder.toString();
    }
    
    public boolean isCommitted() {
        return command != null;
    }
    
    public boolean isSupportedCandidate(){
        return command instanceof CandidateSupportCommand;
    }
    
    public boolean isSupportedCandidateAndArgument(){
        return command instanceof CandidateAndArgumentSupportCommand;
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public boolean isImmediateCommand() {
        return command.getClass().isAnnotationPresent(Immediate.class);
    }

    public boolean isUncommited() {
        return ! isCommitted();
    }

    public void reset() {
        this.command = null;
        this.candidates.clear();
    }
    
}
