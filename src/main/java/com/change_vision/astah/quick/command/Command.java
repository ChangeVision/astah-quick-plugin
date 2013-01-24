package com.change_vision.astah.quick.command;

public interface Command {

	public abstract String getCommandName();
	
	public abstract void execute(String... args);
	
	public abstract String getDescription();
	
	public abstract boolean isEnable();
	
	public abstract CommandIconDescription getIconDescription();
	
}
