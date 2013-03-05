package com.change_vision.astah.quick.command;

import com.change_vision.astah.quick.command.exception.ExecuteCommandException;

public interface Command extends Candidate {

	public abstract void execute(String... args) throws ExecuteCommandException;

}
