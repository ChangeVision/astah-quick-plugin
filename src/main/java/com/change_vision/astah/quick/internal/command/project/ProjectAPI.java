package com.change_vision.astah.quick.internal.command.project;

import java.io.IOException;

import com.change_vision.jude.api.inf.project.ProjectAccessor;
import com.change_vision.jude.api.inf.project.ProjectAccessorFactory;

public class ProjectAPI {
	
	public void createProject(){
		try {
			getProjectAccessor().create();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
	public void closeProject(){
		getProjectAccessor().close();
	}

	private ProjectAccessor getProjectAccessor(){
		try {
			return ProjectAccessorFactory.getProjectAccessor();
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("It may be occurred by class path issue.");
		}
	}

}
