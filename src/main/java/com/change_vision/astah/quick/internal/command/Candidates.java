package com.change_vision.astah.quick.internal.command;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.annotations.TestForMethod;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.CandidateState;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.CandidatesSelector;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.SelectArgument;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.SelectCommand;

public class Candidates {

    private Commands commands;

    class SelectCommandFactory {
        SelectCommand create() {
            return new SelectCommand(commands);
        }
    }

    public static final String PROP_STATE = "state"; //$NON-NLS-1$

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(Candidates.class);

    private SelectCommandFactory commandFactory = new SelectCommandFactory();

    private CandidateState state;

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    private CandidatesSelector<Candidate> selector;

    private CommandExecutor executor;
        
    public Candidates(Commands commands, CommandExecutor executor) {
        this.commands = commands;
        this.executor = executor;
        this.state = commandFactory.create();
        this.selector = new CandidatesSelector<Candidate>();
    }

    public void filter(String key) {
        if (key == null) throw new IllegalArgumentException("key is null."); //$NON-NLS-1$
        String searchKey = key.trim();
        logger.trace("key:'{}'", searchKey); //$NON-NLS-1$
        if (isChangedToCommandState(searchKey)) {
            SelectCommand newState = commandFactory.create();
            setState(newState);
        }
        if (state instanceof SelectCommand && executor.isCommited()) {
            SelectArgument newState = new SelectArgument(executor);
            setState(newState);
        }
        Candidate[] candidates = state.filter(searchKey);
        logger.trace("state:'{}' candidates:'{}'",state.getClass().getSimpleName(), candidates); //$NON-NLS-1$
        selector.setCandidates(candidates);
        if (isChangedToArgumentState(key,candidates)) {
            Command committed = (Command) candidates[0];
            if (executor.isCommited() == false) {
                executor.commit(committed);
            }
            SelectArgument newState = new SelectArgument(executor);
            setState(newState);
            candidates = state.filter(searchKey);
            logger.trace("candidates:'{}'", candidates); //$NON-NLS-1$
            selector.setCandidates(candidates);
        }
    }

    private boolean isChangedToArgumentState(String key,Candidate[] candidates) {
        boolean isCurrentCommandState = state instanceof SelectCommand;
        boolean isFoundOnlyOneCommand = candidates.length == 1 && candidates[0] instanceof Command;
        if (isCurrentCommandState == false || isFoundOnlyOneCommand == false) {
            return false;
        }
        Command committed = (Command) candidates[0];
        String commandName = committed.getName().toLowerCase();
        boolean isCommittedByKey = key.startsWith(commandName);
        return isCurrentCommandState && isFoundOnlyOneCommand && (isCommittedByKey || executor.isCommited());
    }

    private boolean isChangedToCommandState(String key) {
        return (state instanceof SelectArgument) && executor.isCommited() == false;
    }

    public void setState(CandidateState newState) {
        CandidateState oldState = this.state;
        this.state = newState;
        firePropertyChange(PROP_STATE, oldState, newState);
    }

    public CandidateState getState() {
        return state;
    }

    public Command currentCommand() {
        if (state instanceof SelectCommand) {
            return (Command) selector.current();
        }
        return executor.getCommand();
    }

    public Candidate[] getCandidates() {
        return selector.getCandidates();
    }

    public void up() {
        selector.up();
    }

    public Candidate current() {
        return selector.current();
    }

    public void down() {
        selector.down();
    }

    public boolean isCommitted() {
        return state instanceof SelectArgument;
    }

    public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        support.firePropertyChange(propertyName, oldValue, newValue);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    @TestForMethod
    void setCommandFactory(SelectCommandFactory commandFactory) {
        this.commandFactory = commandFactory;
        this.state = commandFactory.create();
    }

    public void setCurrentIndex(int index) {
        selector.setCurrentIndex(index);
    }

}
