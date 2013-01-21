package com.change_vision.astah.quick.internal;

import javax.swing.JFrame;

import com.change_vision.jude.api.inf.exception.InvalidUsingException;
import com.change_vision.jude.api.inf.project.ProjectAccessor;
import com.change_vision.jude.api.inf.project.ProjectAccessorFactory;
import com.change_vision.jude.api.inf.view.IViewManager;

public class AstahAPIWrapper {
    
    public JFrame getMainFrame(){
        IViewManager viewManager = getViewManager();
        JFrame frame = viewManager.getMainFrame();
        return frame;
    }

    private IViewManager getViewManager(){
        ProjectAccessor projectAccessor = getProjectAccessor();
        IViewManager viewManager;
        try {
            viewManager = projectAccessor.getViewManager();
        } catch (InvalidUsingException e) {
            throw new IllegalStateException(e);
        }
        return viewManager;
    }

    private ProjectAccessor getProjectAccessor() {
        try {
            return ProjectAccessorFactory.getProjectAccessor();
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("ProjectAccessor class is not found. It maybe classpath issue. Please check your classpath.");
        }
    }

}