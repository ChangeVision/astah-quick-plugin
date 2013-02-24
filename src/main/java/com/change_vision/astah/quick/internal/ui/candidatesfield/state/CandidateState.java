package com.change_vision.astah.quick.internal.ui.candidatesfield.state;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.Command;

public interface CandidateState {

	void filter(String key);

	Candidate[] getCandidates();

	void up();

	Candidate current();

	void down();
	
	Command currentCommand();
	
}
