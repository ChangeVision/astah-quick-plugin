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


    private static final String DEFAULT_TEMPLATE_NAME = "Java6.asta"; //$NON-NLS-1$

    private static final String SYSTEM_PROPERTY_OF_FILE_NEW_PROJECT_TEMPLATE_FILE = "file.new_project_template_file"; //$NON-NLS-1$

    private static final String SYSTEM_PROPERTY_OF_JUDE_RECENT_FILE_NUMBER = "jude.recent_file_number"; //$NON-NLS-1$

    private static final String PATH_TO_TEMPLATE_PROJECT = "template/project"; //$NON-NLS-1$

    private static final Logger logger = LoggerFactory.getLogger(ProjectAPI.class);

    private AstahAPIWrapper wrapper = new AstahAPIWrapper();

    void createProject() {
        ProjectAccessor projectAccessor = getProjectAccessor();
        try {
            projectAccessor.create();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        mergeTemplate();
    }

    private void mergeTemplate() {
        ProjectAccessor projectAccessor = getProjectAccessor();
        String installPath = projectAccessor.getAstahInstallPath();
        StringBuilder pathBuilder = new StringBuilder(installPath);
        String templateName = getTemplateName();
        pathBuilder.append(PATH_TO_TEMPLATE_PROJECT);
        pathBuilder.append(File.separator);
        pathBuilder.append(templateName);
        String path = pathBuilder.toString();
        logger.trace("template:{}",path); //$NON-NLS-1$
        try {
            projectAccessor.easyMerge(path, true);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private String getTemplateName() {
        SystemPropertyAccessor accessor = wrapper.getSystemPropertyAccessor();
        Properties systemProperties = accessor.getSystemProperties();
        return systemProperties.getProperty(SYSTEM_PROPERTY_OF_FILE_NEW_PROJECT_TEMPLATE_FILE,DEFAULT_TEMPLATE_NAME);
    }

    void closeProject() {
        getProjectAccessor().close();
    }

    boolean isModifiedProject() {
        return wrapper.isModifiedProject();
    }

    boolean isOpenedProject() {
        return wrapper.isOpenedProject();
    }

    boolean isClosedProject() {
        return isOpenedProject() == false;
    }

    JFrame getMainFrame() {
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

    private ProjectAccessor getProjectAccessor() {
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
        String recentFileNumberString = systemProperties.getProperty(SYSTEM_PROPERTY_OF_JUDE_RECENT_FILE_NUMBER);
        if (recentFileNumberString == null || recentFileNumberString.isEmpty()) {
            return new File[recentFileNumber];
        }
        recentFileNumber = Integer.valueOf(recentFileNumberString);
        File[] recentFiles = new File[recentFileNumber];
        for (int i = 0; i < recentFileNumber; i++) {
            String path = systemProperties.getProperty("jude.recent_file_" + i); //$NON-NLS-1$
            recentFiles[i] = new File(path);
        }
        return recentFiles;
    }

    void setWrapper(AstahAPIWrapper wrapper) {
        this.wrapper = wrapper;
    }

    boolean save(){
        return wrapper.save();
    }

}
