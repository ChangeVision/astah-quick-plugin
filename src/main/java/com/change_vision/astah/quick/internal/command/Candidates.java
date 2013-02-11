package com.change_vision.astah.quick.internal.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.CandidateState;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.CommandCommitted;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.CommandSelecting;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.NullCandidate;

public class Candidates {

	/**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(Candidates.class);

	private CandidateState state = new CommandSelecting();


	public void candidates(String searchKey) {
		logger.trace("candidate searchKey:'{}'",searchKey);
		if(
				(state instanceof CommandCommitted) &&
				state.currentCommand().getName().length() > searchKey.length()
				){
			state = new CommandSelecting();
		}
		state.candidates(searchKey);
		Candidate[] candidates = state.getCandidates();
		if(
				(state instanceof CommandSelecting) &&
				candidates.length == 1 &&
				candidates[0] instanceof Command &&
				!(candidates[0] instanceof NullCandidate)){
			state = new CommandCommitted((Command)candidates[0]);
		}
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

	public void setCandidateState(CandidateState state) {
		this.state = state;
	}

	public boolean isCommitted() {
		return state instanceof CommandCommitted;
	}
}
