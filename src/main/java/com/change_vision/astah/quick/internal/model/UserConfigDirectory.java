package com.change_vision.astah.quick.internal.model;

import java.io.File;

import com.change_vision.jude.api.inf.project.ProjectAccessor;
import com.change_vision.jude.api.inf.project.ProjectAccessorFactory;

public class UserConfigDirectory {
	
	private File directory;

	public UserConfigDirectory(){
		File astahRoot = new File(System.getProperty("user.home"),".astah");
		ProjectAccessor projectAccessor;
		try {
			projectAccessor = ProjectAccessorFactory.getProjectAccessor();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(e);
		}
		directory = new File(astahRoot,projectAccessor.getAstahEdition());
	}
	
	public File getDirectory() {
		return directory;
	}
}