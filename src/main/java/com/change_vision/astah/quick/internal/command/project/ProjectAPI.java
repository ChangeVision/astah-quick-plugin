package com.change_vision.astah.quick.internal.command.project;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.jude.api.inf.exception.ProjectNotFoundException;
import com.change_vision.jude.api.inf.project.ProjectAccessor;
import com.change_vision.jude.api.inf.project.ProjectAccessorFactory;

class ProjectAPI {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectAPI.class);
	
	void createProject(){
		try {
			getProjectAccessor().create();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
	void closeProject(){
		getProjectAccessor().close();
	}
	
	boolean isOpenedProject(){
		try {
			String projectPath = getProjectAccessor().getProjectPath();
			logger.trace("isOpenedProject project path : '{}'",projectPath);
			return projectPath != null;
		} catch (ProjectNotFoundException e) {
			logger.trace("isOpenedProject project not found.'{}'",e.getMessage());
			return false;
		}
	}
	
	boolean isClosedProject(){
		return isOpenedProject() == false;
	}

	private ProjectAccessor getProjectAccessor(){
		try {
			return ProjectAccessorFactory.getProjectAccessor();
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("It maybe occurred by class path issue.");
		}
	}

}
