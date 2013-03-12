package com.change_vision.astah.quick.command;

public interface CandidatesProvider {

	public abstract Candidate[] candidate(Candidate[] committed,String searchKey);
	
}
