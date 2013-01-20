package com.change_vision.astah.quick.internal.command.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.jude.api.inf.editor.BasicModelEditor;
import com.change_vision.jude.api.inf.editor.IModelEditorFactory;
import com.change_vision.jude.api.inf.editor.ITransactionManager;
import com.change_vision.jude.api.inf.exception.InvalidEditingException;
import com.change_vision.jude.api.inf.exception.InvalidUsingException;
import com.change_vision.jude.api.inf.exception.ProjectNotFoundException;
import com.change_vision.jude.api.inf.model.IModel;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.project.ModelFinder;
import com.change_vision.jude.api.inf.project.ProjectAccessor;
import com.change_vision.jude.api.inf.project.ProjectAccessorFactory;
import com.change_vision.jude.api.inf.view.IProjectViewManager;
import com.change_vision.jude.api.inf.view.IViewManager;

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

	void createClass(String className) {
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

	INamedElement[] find(final String searchKey) {
		logger.trace("find:{}",searchKey);
		if(isClosedProject()) return new INamedElement[0];
		try {
			return getProjectAccessor().findElements(new ModelFinder() {
				@Override
				public boolean isTarget(INamedElement element) {
					String name = element.getName().toLowerCase();
					boolean nameStarts = name.startsWith(searchKey.toLowerCase());
					boolean alias1Starts = element.getAlias1().startsWith(searchKey);
					boolean alias2Starts = element.getAlias2().startsWith(searchKey);
					return nameStarts || alias1Starts || alias2Starts;
				}
			});
		} catch (ProjectNotFoundException e) {
			throw new IllegalArgumentException("It maybe occurred by class path issue.");
		}
	}
	
	private boolean isClosedProject(){
		try {
			String projectPath = getProjectAccessor().getProjectPath();
			logger.trace("isClosedProject project path : '{}'",projectPath);
			return projectPath == null;
		} catch (ProjectNotFoundException e) {
			logger.info("isClosedProject project not found.'{}'",e.getMessage());
			return true;
		}
	}

	void showInStructureTree(INamedElement model) {
		IProjectViewManager projectViewManager = getViewManager().getProjectViewManager();
		projectViewManager.showInStructureTree(model);
	}

	private IViewManager getViewManager() {
		try {
			return getProjectAccessor().getViewManager();
		} catch (InvalidUsingException e) {
			throw new IllegalStateException(e);
		}
	}
}
