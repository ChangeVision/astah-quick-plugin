package com.change_vision.astah.quick.command;

public interface CandidateAndArgumentSupportCommand extends Command, CandidatesProvider {

    public abstract void execute(Candidate[] candidates,String[] arguments);

}
