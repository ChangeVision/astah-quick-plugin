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
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.NullCandidate;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.SelectArgument;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.SelectCommand;

public class Candidates {
    
    class SelectCommandFactory {
        SelectCommand create(){
            return new SelectCommand();
        }
    }

	public static final String PROP_STATE = "state";

	/**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(Candidates.class);

    private SelectCommandFactory commandFactory = new SelectCommandFactory();

    private CandidateState state = commandFactory.create();
	
	private PropertyChangeSupport support = new PropertyChangeSupport(this);

	private CandidatesSelector<Candidate> selector = new CandidatesSelector<Candidate>();
	

	public void filter(String key) {
		if (key == null) throw new IllegalArgumentException("key is null.");
		String searchKey = key.trim();
		logger.trace("key:'{}'",searchKey);
		if(isChangedToCommandState(searchKey)){
			SelectCommand newState = commandFactory.create();
			setState(newState);
		}
		Candidate[] candidates = state.filter(searchKey);
		logger.trace("candidates:'{}'",candidates);
		selector.setCandidates(candidates);
		if(isChangedToArgumentState(candidates)){
			Command committed = (Command)candidates[0];
			SelectArgument newState = new SelectArgument(committed);
			setState(newState);
		}
	}

	private boolean isChangedToArgumentState(Candidate[] candidates) {
		boolean isCurrentCommandState = state instanceof SelectCommand;
		return isCurrentCommandState &&
		candidates.length == 1 &&
		candidates[0] instanceof Command &&
		!(candidates[0] instanceof NullCandidate);
	}

	private boolean isChangedToCommandState(String searchKey) {
		boolean isSelectArgument = state instanceof SelectArgument;
		if (!isSelectArgument) {
			return false;
		}
		SelectArgument argument = (SelectArgument) state;
		Command currentCommand = argument.currentCommand();
        String commandName = currentCommand.getName();
        return commandName.length() > searchKey.length();
	}
	
	public void setState(CandidateState newState) {
		CandidateState oldState = this.state;
		this.state = newState;
		firePropertyChange(PROP_STATE, oldState, newState);
	}
	
	public CandidateState getState() {
		return state;
	}
	
	public Command currentCommand(){
		if (state instanceof SelectCommand) {
			return (Command) selector.current();
		}
		return ((SelectArgument) state).currentCommand();
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
	
	public void firePropertyChange(String propertyName, Object oldValue, Object newValue){
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

}
