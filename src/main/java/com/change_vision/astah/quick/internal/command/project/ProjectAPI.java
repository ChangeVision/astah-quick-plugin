package com.change_vision.astah.quick.internal.command.project;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.internal.AstahAPIWrapper;
import com.change_vision.jude.api.inf.exception.InvalidUsingException;
import com.change_vision.jude.api.inf.project.ProjectAccessor;
import com.change_vision.jude.api.inf.system.SystemPropertyAccessor;
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

    File[] getRecentFiles() {
        SystemPropertyAccessor systemPropertyAccessor = wrapper.getSystemPropertyAccessor();
        Properties systemProperties = systemPropertyAccessor.getSystemProperties();
        int recentFileNumber = 0;
        String recentFileNumberString = systemProperties.getProperty("jude.recent_file_number");
        if (recentFileNumberString == null ||recentFileNumberString.isEmpty()) {
            return new File[recentFileNumber];
        }
        recentFileNumber = Integer.valueOf(recentFileNumberString);
        File[] recentFiles = new File[recentFileNumber];
        for (int i = 0; i < recentFileNumber; i++) {
            String path = systemProperties.getProperty("jude.recent_file_" + i);
            recentFiles[i] = new File(path );
        }
        return recentFiles;
    }
    
    void setWrapper(AstahAPIWrapper wrapper) {
        this.wrapper = wrapper;
    }

}
