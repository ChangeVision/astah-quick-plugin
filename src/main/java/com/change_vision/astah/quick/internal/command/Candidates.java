package com.change_vision.astah.quick.internal.command;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.CandidateState;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.SelectArgument;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.SelectCommand;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.NullCandidate;

public class Candidates {

	public static final String PROP_STATE = "state";

	/**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(Candidates.class);

	private CandidateState state = new SelectCommand();
	
	private PropertyChangeSupport support = new PropertyChangeSupport(this);

	public void filter(String key) {
		if (key == null) throw new IllegalArgumentException("key is null.");
		logger.trace("key:'{}'",key);
		if(isChangedToCommandState(key)){
			SelectCommand newState = new SelectCommand();
			setState(newState);
		}
		state.filter(key);
		Candidate[] candidates = state.getCandidates();
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
		return (state instanceof SelectArgument) &&
		state.currentCommand().getName().length() > searchKey.length();
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
		return state.currentCommand();
	}
	
	public Candidate[] getCandidates() {
		return state.getCandidates();
	}

	public void up() {
		state.up();
	}

	public Candidate current() {
		return state.current();
	}

	public void down() {
		state.down();
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

}
