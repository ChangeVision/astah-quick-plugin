package com.change_vision.astah.quick.command;

import com.change_vision.astah.quick.command.exception.ExecuteCommandException;

public interface CandidateSupportCommand extends Command, CandidatesProvider {

    public abstract void execute(Candidate[] candidates) throws ExecuteCommandException;

}
