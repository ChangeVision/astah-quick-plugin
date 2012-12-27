package com.change_vision.astah.quick.command;

public interface Command {

	public abstract String getCommandName();
	
	public abstract void execute();
}
