package com.change_vision.astah.quick.internal;

import java.io.IOException;

import javax.swing.JFrame;

import com.change_vision.jude.api.inf.editor.ClassDiagramEditor;
import com.change_vision.jude.api.inf.editor.IDiagramEditorFactory;
import com.change_vision.jude.api.inf.editor.ITransactionManager;
import com.change_vision.jude.api.inf.editor.SequenceDiagramEditor;
import com.change_vision.jude.api.inf.editor.StateMachineDiagramEditor;
import com.change_vision.jude.api.inf.editor.UseCaseDiagramEditor;
import com.change_vision.jude.api.inf.exception.InvalidUsingException;
import com.change_vision.jude.api.inf.exception.LicenseNotFoundException;
import com.change_vision.jude.api.inf.exception.ProjectLockedException;
import com.change_vision.jude.api.inf.exception.ProjectNotFoundException;
import com.change_vision.jude.api.inf.project.ProjectAccessor;
import com.change_vision.jude.api.inf.project.ProjectAccessorFactory;
import com.change_vision.jude.api.inf.system.SystemPropertyAccessor;
import com.change_vision.jude.api.inf.system.SystemPropertyAccessorFactory;
import com.change_vision.jude.api.inf.view.IDiagramViewManager;
import com.change_vision.jude.api.inf.view.IIconManager;
import com.change_vision.jude.api.inf.view.IViewManager;

public class AstahAPIWrapper {
    
    public JFrame getMainFrame(){
        IViewManager viewManager = getViewManager();
        JFrame frame = viewManager.getMainFrame();
        return frame;
    }

    public IViewManager getViewManager(){
        ProjectAccessor projectAccessor = getProjectAccessor();
        IViewManager viewManager;
        try {
            viewManager = projectAccessor.getViewManager();
        } catch (InvalidUsingException e) {
            throw new IllegalStateException(e);
        }
        return viewManager;
    }

    public ProjectAccessor getProjectAccessor() {
        try {
            return ProjectAccessorFactory.getProjectAccessor();
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("ProjectAccessor class is not found. It maybe classpath issue. Please check your classpath.");
        }
    }
    
    public boolean isModifiedProject() {
       return  isOpenedProject() && getProjectAccessor().isProjectModified();
    }

	public boolean isOpenedProject(){
		return getProjectAccessor().hasProject();
	}
    
	public IIconManager getIconManager() {
		return getViewManager().getIconManager();
	}

	public boolean isClosedProject() {
		return isOpenedProject() == false;
	}

    public IDiagramViewManager getDiagramViewManager() {
        return getViewManager().getDiagramViewManager();
    }
    
    public IDiagramEditorFactory getDiagramEditorFactory() {
        return getProjectAccessor().getDiagramEditorFactory();
    }
    
    public ClassDiagramEditor getClassDiagramEditor(){
        try {
            return getDiagramEditorFactory().getClassDiagramEditor();
        } catch (InvalidUsingException e) {
            throw new IllegalStateException("This API doesn't support in community edition.",e);
        }
    }
    
    public SystemPropertyAccessor getSystemPropertyAccessor(){
        try {
            return SystemPropertyAccessorFactory.getSystemPropertyAccessor();
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("SystemPropertyAccessor class is not found. It maybe classpath issue. Please check your classpath.");
        }
    }

    public void save(){
        try {
            getProjectAccessor().save();
        } catch (LicenseNotFoundException e) {
            throw new IllegalStateException("License is not installed or using community.",e);
        } catch (ProjectNotFoundException e) {
            throw new IllegalStateException("Project is not found.",e);
        } catch (ProjectLockedException e) {
            throw new IllegalStateException("Project is locked.",e);
        } catch (IOException e) {
            if (e.getMessage() == null) {
                // maybe choosing cancel when file selection dialog
                return;
            }
            throw new IllegalStateException("IOException is occurred.",e);
        }
    }

    public ITransactionManager getTransactionManager() {
        return getProjectAccessor().getTransactionManager();
    }

    public UseCaseDiagramEditor getUseCaseDiagramEditor() {
        try {
            return getDiagramEditorFactory().getUseCaseDiagramEditor();
        } catch (InvalidUsingException e) {
            throw new IllegalStateException("This API doesn't support in community edition.",e);
        }
    }

    public StateMachineDiagramEditor getStateMachineDiagramEditor() {
        try {
            return getDiagramEditorFactory().getStateMachineDiagramEditor();
        } catch (InvalidUsingException e) {
            throw new IllegalStateException("This API doesn't support in community edition.",e);
        }
    }

    public SequenceDiagramEditor getSequenceDiagramEditor() {
        try {
            return getDiagramEditorFactory().getSequenceDiagramEditor();
        } catch (InvalidUsingException e) {
            throw new IllegalStateException("This API doesn't support in community edition.",e);
        }
    }

}
