package com.change_vision.astah.quick.internal.command.project;

import com.change_vision.astah.quick.command.Command;

public class CloseProjectCommand implements Command{

	@Override
	public String getCommandName() {
		return "close project";
	}

	@Override
	public void execute() {
		ProjectAPI api = new ProjectAPI();
		api.closeProject();
	}

}
