package com.change_vision.astah.quick.internal.command.project;

import com.change_vision.astah.quick.command.Command;


public class NewProjectCommand implements Command{
	
	private ProjectAPI api = new ProjectAPI();
	
	public String getCommandName(){
		return "new project";
	}
	
	public void execute(String... args){
		ProjectAPI api = new ProjectAPI();
		api.createProject();
	}
	
	@Override
	public String getDescription() {
		return "create new project";
	}
	
	@Override
	public boolean isEnable() {
		return api.isClosedProject();
	}
}
