package com.change_vision.astah.quick.internal.command.project;

import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.internal.command.ResourceCommandIconDescription;


public class NewProjectCommand implements Command{
	
	private ProjectAPI api = new ProjectAPI();
	
	public String getName(){
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
	public boolean isEnabled() {
		return api.isClosedProject();
	}
	
	@Override
	public CandidateIconDescription getIconDescription() {
		return new ResourceCommandIconDescription("/icons/glyphicons_151_new_window.png");
	}
}
