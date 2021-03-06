package com.change_vision.astah.quick.internal.command;

import static java.lang.String.format;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.Comparator;

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

    private final class NameComparator implements Comparator<Candidate> {
        @Override
        public int compare(Candidate o1, Candidate o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }

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

    private final CommandBuilder commandBuilder;

    public Candidates(Commands commands, CommandBuilder commandBuilder) {
        this.commands = commands;
        this.commandBuilder = commandBuilder;
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
        if (state instanceof SelectCommand && commandBuilder.isCommitted()) {
            SelectArgument newState = new SelectArgument(commandBuilder);
            setState(newState);
        }
        Candidate[] candidates = state.filter(searchKey);
        if (candidates == null) {
            String className = state.getClass().getSimpleName();
            if (state instanceof SelectArgument) {
                className = commandBuilder.getCommand().getClass().getSimpleName();
            }
            String message = format("state returns null candidates. %s",className);
            throw new IllegalStateException(message);
        }
        logger.trace("state:'{}' candidates:'{}'",state.getClass().getSimpleName(), candidates); //$NON-NLS-1$
        selector.setCandidates(candidates);
        if (isChangedArgumentState(key,candidates)) {
            logger.trace("change argument state");
            Command committed = (Command) candidates[0];
            if (commandBuilder.isCommitted() == false) {
                commandBuilder.commit(committed);
            }
            SelectArgument newState = new SelectArgument(commandBuilder);
            setState(newState);
            candidates = state.filter(searchKey);
            logger.trace("candidates:'{}'", candidates); //$NON-NLS-1$
            selector.setCandidates(candidates);
            return;
        }
        if (isCommitCandidate(key,candidates)){
            commandBuilder.add(candidates[0]);
        }
    }

    private boolean isCommitCandidate(String key, Candidate[] candidates) {
        boolean isCurrentArgumentState = state instanceof SelectArgument;
        boolean isFoundOnlyOneCandidate = candidates.length == 1 && candidates[0] instanceof Candidate;
        if (isCurrentArgumentState == false || isFoundOnlyOneCandidate == false) {
            return false;
        }
        Candidate candidate = candidates[0];
        String candidateName = candidate.getName().toLowerCase();
        String keyLowerCase = key.toLowerCase().trim();
        boolean isDecidedByKey = keyLowerCase.equals(candidateName);
        return isCurrentArgumentState && isFoundOnlyOneCandidate && isDecidedByKey;
    }

    private boolean isChangedArgumentState(String key,Candidate[] candidates) {
        boolean isCurrentCommandState = state instanceof SelectCommand;
        boolean isFoundOnlyOneCommand = candidates.length == 1 && candidates[0] instanceof Command;
        if (isCurrentCommandState == false || isFoundOnlyOneCommand == false) {
            return false;
        }
        Command committed = (Command) candidates[0];
        String commandName = committed.getName().toLowerCase();
        boolean isCommittedByKey = key.startsWith(commandName);
        return isCurrentCommandState && isFoundOnlyOneCommand && (isCommittedByKey || commandBuilder.isCommitted());
    }

    private boolean isChangedToCommandState(String key) {
        return (state instanceof SelectArgument) && commandBuilder.isCommitted() == false;
    }

    public void setState(CandidateState newState) {
        CandidateState oldState = this.state;
        this.state = newState;
        support.firePropertyChange(PROP_STATE, oldState, newState);
    }

    public CandidateState getState() {
        return state;
    }

    public Command currentCommand() {
        if (state instanceof SelectCommand) {
            return (Command) selector.current();
        }
        return commandBuilder.getCommand();
    }

    public Candidate[] getCandidates() {
        Candidate[] candidates = selector.getCandidates();
        if (isCommitted()) {
            Arrays.sort(candidates, new NameComparator());
        }
        return candidates;
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
    
    public CommandBuilder getCommandBuilder() {
        return commandBuilder;
    }

    public void reset() {
        this.state = commandFactory.create();
        this.commandBuilder.reset();
    }

}
