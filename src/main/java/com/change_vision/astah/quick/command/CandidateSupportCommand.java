package com.change_vision.astah.quick.command;

public interface CandidateSupportCommand extends Command, CandidatesProvider {

    public abstract void execute(Candidate... candidates);

}
