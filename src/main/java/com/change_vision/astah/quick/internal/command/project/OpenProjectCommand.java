package com.change_vision.astah.quick.internal.command.project;

import com.change_vision.astah.quick.command.Command;

public class OpenProjectCommand implements Command{

	@Override
	public String getCommandName() {
		return "open project";
	}

	@Override
	public void execute(String... args) {
	}

	@Override
	public String getDescription() {
		return "open project...";
	}
	
	@Override
	public boolean isEnable() {
		return true;
	}
}
