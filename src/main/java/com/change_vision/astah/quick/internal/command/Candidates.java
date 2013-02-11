package com.change_vision.astah.quick.internal.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.CandidateState;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.CommandCommitted;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.CommandSelecting;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.CommandSelecting.NullCommand;

public class Candidates {
    public  static final String COMMIT_COMMAND = "COMMIT";

	/**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(Candidates.class);

	private CandidateState state = new CommandSelecting();


	public void candidates(String searchKey) {
		logger.trace("candidate searchKey:'{}'",searchKey);
		if(
				(this.state instanceof CommandCommitted) &&
				this.state.currentCommand().getName().length() > searchKey.length()
				){
			this.state = new CommandSelecting();
		}
		this.state.candidates(searchKey);
		Candidate[] candidates = this.state.getCandidates();
		if(
				(this.state instanceof CommandSelecting) &&
				candidates.length == 1 &&
				candidates[0] instanceof Command &&
				!(candidates[0] instanceof NullCommand)){
			this.state = new CommandCommitted((Command)candidates[0]);
		}
	}
	
	public Command currentCommand(){
		return this.state.currentCommand();
	}
	
	public Candidate[] getCandidates() {
		return this.state.getCandidates();
	}

	public void up() {
		this.state.up();
	}

	public Candidate current() {
		return this.state.current();
	}

	public void down() {
		this.state.down();
	}

	public void setCandidateState(CandidateState state) {
		this.state = state;
	}

	public boolean isCommitted() {
		return this.state instanceof CommandCommitted;
	}
}
