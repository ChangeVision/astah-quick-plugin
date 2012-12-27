package com.change_vision.astah.quick.internal.command.model;

import com.change_vision.astah.quick.command.Command;

public class CreateClassCommand implements Command{

	@Override
	public String getCommandName() {
		return "create class";
	}

	@Override
	public void execute() {
	}
	
	@Override
	public String toString() {
		return getCommandName();
	}

}
