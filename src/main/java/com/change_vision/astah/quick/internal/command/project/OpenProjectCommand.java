package com.change_vision.astah.quick.internal.command.project;

import com.change_vision.astah.quick.command.Command;

public class OpenProjectCommand implements Command{

	@Override
	public String getCommandName() {
		return "open project";
	}

	@Override
	public void execute() {
	}

	@Override
	public String toString() {
		return getCommandName();
	}

}
