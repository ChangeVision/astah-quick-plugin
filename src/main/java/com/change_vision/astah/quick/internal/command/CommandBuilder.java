package com.change_vision.astah.quick.internal.command;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.Command;

public class CommandBuilder {
    public static final String PROP_OF_COMMAND = "command"; //$NON-NLS-1$
    public static final String PROP_OF_CANDIDATE = "candidate"; //$NON-NLS-1$

    private Command command;
    private List<Candidate> candidates = new ArrayList<Candidate>();
    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void commit(Command command){
        if (command == null) throw new IllegalArgumentException("command is null.");
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
        return "CommandBuilder [command=" + command + ", candidates=" + candidates + "]";
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

    public boolean isCommitted() {
        return command != null;
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
    
}
