package com.change_vision.astah.quick.internal.command.project;

import com.change_vision.astah.quick.command.Command;

public class CloseProjectCommand implements Command{
	
	private ProjectAPI api = new ProjectAPI();

	@Override
	public String getCommandName() {
		return "close project";
	}

	@Override
	public void execute(String... args) {
		ProjectAPI api = new ProjectAPI();
		api.closeProject();
	}
	
	@Override
	public String getDescription() {
		return "close current project";
	}
	
	@Override
	public boolean isEnable() {
		return api.isOpenedProject();
	}
	
}