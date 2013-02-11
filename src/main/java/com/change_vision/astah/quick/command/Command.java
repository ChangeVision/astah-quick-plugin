package com.change_vision.astah.quick.command;

public interface Command extends Candidate {

	public abstract void execute(String... args);

}
