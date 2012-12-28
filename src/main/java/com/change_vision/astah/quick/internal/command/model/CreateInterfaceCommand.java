package com.change_vision.astah.quick.internal.command.model;

import com.change_vision.astah.quick.command.Command;

public class CreateInterfaceCommand implements Command{

	@Override
	public String getCommandName() {
		return "create interface";
	}

	@Override
	public void execute(String... args) {
	}

	@Override
	public String getDescription() {
		return "create interface [interface name]";
	}
	
	@Override
	public boolean isEnable() {
		return true;
	}
}
