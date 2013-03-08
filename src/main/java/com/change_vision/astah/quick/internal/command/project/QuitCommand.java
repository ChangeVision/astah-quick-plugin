package com.change_vision.astah.quick.internal.command.project;

import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.command.ResourceCommandIconDescription;


public class QuitCommand implements Command{
		
	public String getName(){
		return "quit Astah";
	}
	
	public void execute(String... args){
	}
	
	@Override
	public String getDescription() {
		return "quit Astah(more suitable Ctrl+Q)";
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public CandidateIconDescription getIconDescription() {
		return new ResourceCommandIconDescription("/icons/glyphicons_207_remove_2.png");
	}
}
