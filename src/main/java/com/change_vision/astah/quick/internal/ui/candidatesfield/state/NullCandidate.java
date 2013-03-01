package com.change_vision.astah.quick.internal.ui.candidatesfield.state;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.internal.command.ResourceCommandIconDescription;

public class NullCandidate implements Candidate {

	@Override
	public String getName() {
		return "Not Found";
	}

	@Override
	public String getDescription() {
		return "Candidates are not found.";
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public CandidateIconDescription getIconDescription() {
		return new ResourceCommandIconDescription("/icons/glyphicons_207_remove_2.png");
	}

}