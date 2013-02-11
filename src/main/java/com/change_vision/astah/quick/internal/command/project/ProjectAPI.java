package com.change_vision.astah.quick.internal.command.project;

import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.internal.AstahAPIWrapper;
import com.change_vision.jude.api.inf.exception.InvalidUsingException;
import com.change_vision.jude.api.inf.project.ProjectAccessor;
import com.change_vision.jude.api.inf.view.IViewManager;

class ProjectAPI {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectAPI.class);
	
	private AstahAPIWrapper wrapper = new AstahAPIWrapper();
	
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
		return wrapper.isOpenedProject();
	}
	
	boolean isClosedProject(){
		return isOpenedProject() == false;
	}
	
	JFrame getMainFrame(){
		return getViewManager().getMainFrame();
	}

	private IViewManager getViewManager() {
		try {
			return getProjectAccessor().getViewManager();
		} catch (InvalidUsingException e) {
			logger.error(e.getLocalizedMessage(), e);
			return null;
		}
	}

	private ProjectAccessor getProjectAccessor(){
		return wrapper.getProjectAccessor();
	}

	void openProject(File file) {
		try {
			getProjectAccessor().open(file.getAbsolutePath());
		} catch (Exception e) {
			throw new IllegalStateException(e.getLocalizedMessage());
		}
	}

}
