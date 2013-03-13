package com.change_vision.astah.quick.internal.command.project;

import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.Messages;
import com.change_vision.astah.quick.internal.command.ResourceCommandIconDescription;

public class CloseProjectCommand implements Command{
	
	private ProjectAPI api = new ProjectAPI();

	@Override
	public String getName() {
		return "close project"; //$NON-NLS-1$
	}

	@Override
	public void execute(String... args) {
		ProjectAPI api = new ProjectAPI();
		api.closeProject();
	}
	
	@Override
	public String getDescription() {
		return Messages.getString("CloseProjectCommand.description"); //$NON-NLS-1$
	}
	
	@Override
	public boolean isEnabled() {
		return api.isOpenedProject();
	}
	
	@Override
	public CandidateIconDescription getIconDescription() {
		return new ResourceCommandIconDescription("/icons/glyphicons_207_remove_2.png"); //$NON-NLS-1$
	}

}
