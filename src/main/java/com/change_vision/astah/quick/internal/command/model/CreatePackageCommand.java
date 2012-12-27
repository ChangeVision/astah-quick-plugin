package com.change_vision.astah.quick.internal.command.model;

import com.change_vision.astah.quick.command.Command;

public class CreatePackageCommand implements Command{

	@Override
	public String getCommandName() {
		return "create package";
	}

	@Override
	public void execute() {
	}
	
	@Override
	public String getDescription() {
		return "create package [package name]";
	}

}
