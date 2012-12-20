package com.change_vision.astah.quick.internal.command.project;

import com.change_vision.astah.quick.command.Command;


public class NewProjectCommand implements Command{
	
	public String getCommandName(){
		return "new";
	}
	
	public void execute(){
		ProjectAPI api = new ProjectAPI();
		api.createProject();
	}

}
