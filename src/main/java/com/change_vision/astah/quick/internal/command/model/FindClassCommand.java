package com.change_vision.astah.quick.internal.command.model;

import com.change_vision.astah.quick.command.Command;

public class FindClassCommand implements Command{

	@Override
	public String getCommandName() {
		return "find class";
	}

	@Override
	public void execute() {
	}
	
	@Override
	public String getDescription() {
		return "find class [classname]";
	}
}
