package com.change_vision.astah.quick.internal.command.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.jude.api.inf.editor.BasicModelEditor;
import com.change_vision.jude.api.inf.editor.IModelEditorFactory;
import com.change_vision.jude.api.inf.editor.ITransactionManager;
import com.change_vision.jude.api.inf.exception.InvalidEditingException;
import com.change_vision.jude.api.inf.exception.ProjectNotFoundException;
import com.change_vision.jude.api.inf.model.IModel;
import com.change_vision.jude.api.inf.project.ProjectAccessor;
import com.change_vision.jude.api.inf.project.ProjectAccessorFactory;

class ModelAPI {

	private static final Logger logger = LoggerFactory.getLogger(ModelAPI.class);
	
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
	
	private ProjectAccessor getProjectAccessor(){
		try {
			return ProjectAccessorFactory.getProjectAccessor();
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("It maybe occurred by class path issue.");
		}
	}

	public void createClass(String className) {
		IModel project = getProject();
		ITransactionManager transactionManager = getProjectAccessor().getTransactionManager();
		try {
			transactionManager.beginTransaction();
			getBasicModelEditorFactory().createClass(project, className);
			transactionManager.endTransaction();
		} catch (InvalidEditingException e) {
			transactionManager.abortTransaction();
			throw new IllegalStateException(e);
		}
	}

	private BasicModelEditor getBasicModelEditorFactory() {
		try {
			return getModelEditorFactory().getBasicModelEditor();
		} catch (InvalidEditingException e) {
			throw new IllegalStateException(e);
		}
	}

	private IModelEditorFactory getModelEditorFactory() {
		return getProjectAccessor().getModelEditorFactory();
	}

	private IModel getProject() {
		try {
			return getProjectAccessor().getProject();
		} catch (ProjectNotFoundException e) {
			throw new IllegalStateException(e);
		}
	}

}
