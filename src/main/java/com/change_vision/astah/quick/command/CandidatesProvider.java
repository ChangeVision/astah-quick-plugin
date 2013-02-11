package com.change_vision.astah.quick.command;

public interface CandidatesProvider {
	
	public abstract Candidate[] getCandidates();

	public abstract void candidate(String searchKey);
	
	public abstract void reset();
	
	public abstract void execute(Candidate candidate);

}
