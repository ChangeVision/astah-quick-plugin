package com.change_vision.astah.quick.internal.ui.candidatesfield.state;

import com.change_vision.astah.quick.command.Candidate;

public interface CandidateState {

	Candidate[] filter(String key);
	
}
