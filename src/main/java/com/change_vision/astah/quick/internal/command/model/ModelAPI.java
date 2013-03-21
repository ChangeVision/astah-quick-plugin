package com.change_vision.astah.quick.internal.command.model;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.internal.AstahAPIWrapper;
import com.change_vision.astah.quick.internal.Messages;
import com.change_vision.astah.quick.internal.annotations.TestForMethod;
import com.change_vision.astah.quick.internal.modelfinder.ClassFinder;
import com.change_vision.astah.quick.internal.modelfinder.ClassOrPackageFinder;
import com.change_vision.astah.quick.internal.modelfinder.FQCNFinder;
import com.change_vision.jude.api.inf.editor.BasicModelEditor;
import com.change_vision.jude.api.inf.editor.ITransactionManager;
import com.change_vision.jude.api.inf.exception.InvalidEditingException;
import com.change_vision.jude.api.inf.exception.InvalidUsingException;
import com.change_vision.jude.api.inf.exception.ProjectNotFoundException;
import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.model.IElement;
import com.change_vision.jude.api.inf.model.IModel;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.model.IOperation;
import com.change_vision.jude.api.inf.model.IPackage;
import com.change_vision.jude.api.inf.project.ModelFinder;
import com.change_vision.jude.api.inf.project.ProjectAccessor;
import com.change_vision.jude.api.inf.view.IProjectViewManager;
import com.change_vision.jude.api.inf.view.IViewManager;

public class ModelAPI {

    private static final String PACKAGE_SEPARATOR_REGEX = "\\."; //$NON-NLS-1$

    private static final Logger logger = LoggerFactory.getLogger(ModelAPI.class);

    private AstahAPIWrapper wrapper = new AstahAPIWrapper();

    boolean isOpenedProject() {
        return wrapper.isOpenedProject();
    }
    
    void addStereotype(IElement element,String stereotype){
        ITransactionManager transactionManager = getTransactionManager();
        try {
            transactionManager.beginTransaction();
            element.addStereotype(stereotype);
            transactionManager.endTransaction();
        } catch (InvalidEditingException e) {
            transactionManager.abortTransaction();
            throw new IllegalStateException(e);
        }
        
    }
    
    void createClass(String className) {
        if (className == null) throw new IllegalArgumentException(Messages.getString("ModelAPI.createClass_null_argument_message")); //$NON-NLS-1$
        IPackage parent = getProject();
        ITransactionManager transactionManager = getTransactionManager();
        BasicModelEditor basicModelEditorFactory = getBasicModelEditor();

        String[] namespaces = className.split(PACKAGE_SEPARATOR_REGEX);
        if (namespaces.length != 1) {
            className = namespaces[namespaces.length - 1];
            namespaces = Arrays.copyOfRange(namespaces, 0, namespaces.length - 1);
            parent = createPackage(parent, namespaces);
        }
        try {
            transactionManager.beginTransaction();
            basicModelEditorFactory.createClass(parent, className);
            transactionManager.endTransaction();
        } catch (InvalidEditingException e) {
            transactionManager.abortTransaction();
            throw new IllegalStateException(e);
        }
    }

    void createInterface(String interfaceName) {
        if (interfaceName == null) throw new IllegalArgumentException(Messages.getString("ModelAPI.createInterface_null_argument_message")); //$NON-NLS-1$
        IPackage parent = getProject();
        ITransactionManager transactionManager = getTransactionManager();
        BasicModelEditor basicModelEditorFactory = getBasicModelEditor();

        String[] namespaces = interfaceName.split(PACKAGE_SEPARATOR_REGEX);
        if (namespaces.length != 1) {
            interfaceName = namespaces[namespaces.length - 1];
            namespaces = Arrays.copyOfRange(namespaces, 0, namespaces.length - 1);
            parent = createPackage(parent, namespaces);
        }
        try {
            transactionManager.beginTransaction();
            basicModelEditorFactory.createInterface(parent, interfaceName);
            transactionManager.endTransaction();
        } catch (InvalidEditingException e) {
            transactionManager.abortTransaction();
            throw new IllegalStateException(e);
        }
    }

    void createPackage(String packageName) {
        IModel project = getProject();
        String[] namespaces = packageName.split(PACKAGE_SEPARATOR_REGEX);
        createPackage(project, namespaces);
    }

    INamedElement[] findClass(String searchKey) {
	    logger.trace("findClass:{}", searchKey); //$NON-NLS-1$
	    if (isClosedProject()) return new INamedElement[0];
	    try {
	        ModelFinder finder = new ClassFinder(searchKey);
	        return getProjectAccessor().findElements(finder);
	    } catch (ProjectNotFoundException e) {
	        throw new IllegalArgumentException("It maybe occurred by class path issue."); //$NON-NLS-1$
	    }
	}

	INamedElement[] findClassOrPackage(final String searchKey) {
        logger.trace("findClassOrPackage:{}", searchKey); //$NON-NLS-1$
        if (isClosedProject()) return new INamedElement[0];
        try {
        	ModelFinder finder = new ClassOrPackageFinder(searchKey);
            return getProjectAccessor().findElements(finder);
        } catch (ProjectNotFoundException e) {
            throw new IllegalArgumentException("It maybe occurred by class path issue."); //$NON-NLS-1$
        }
    }

    INamedElement[] findByFQCN(String searchKey) {
        logger.trace("findFQCN:{}", searchKey); //$NON-NLS-1$
        if (isClosedProject()) return new INamedElement[0];
        try {
        	ModelFinder finder = new FQCNFinder(searchKey);
            return getProjectAccessor().findElements(finder);
        } catch (ProjectNotFoundException e) {
            throw new IllegalArgumentException("It maybe occurred by class path issue."); //$NON-NLS-1$
        }
    }

    void showInStructureTree(INamedElement model) {
        IProjectViewManager projectViewManager = getViewManager().getProjectViewManager();
        projectViewManager.showInStructureTree(model);
    }

    IOperation createOperation(IClass owner, String name, IClass retType){
    	BasicModelEditor editor = getBasicModelEditor();
        ITransactionManager transactionManager = getTransactionManager();

    	try {
    		transactionManager.beginTransaction();
			IOperation operation = editor.createOperation(owner, name, retType);
			transactionManager.endTransaction();
			return operation;
		} catch (InvalidEditingException e) {
			transactionManager.abortTransaction();
            throw new IllegalStateException("This API doesn't support in community edition.",e);
		}
    }

    IOperation createOperation(IClass owner, String name, String retType){
    	BasicModelEditor editor = getBasicModelEditor();
        ITransactionManager transactionManager = getTransactionManager();

    	try {
    		transactionManager.beginTransaction();
			IOperation operation = editor.createOperation(owner, name, retType);
			transactionManager.endTransaction();
			return operation;
		} catch (InvalidEditingException e) {
			transactionManager.abortTransaction();
            throw new IllegalStateException("This API doesn't support in community edition.",e);
		}
    }

	@TestForMethod
    void setWrapper(AstahAPIWrapper wrapper) {
        this.wrapper = wrapper;
    }

	private ProjectAccessor getProjectAccessor() {
	    return wrapper.getProjectAccessor();
	}

	private IPackage createPackage(IPackage parent, String[] namespaces) {
	    ITransactionManager transactionManager = getTransactionManager();
	    BasicModelEditor basicModelEditorFactory = getBasicModelEditor();
	    for (int i = 0; i < namespaces.length; i++) {
	        String namespace = namespaces[i];
	        INamedElement[] ownedElements = parent.getOwnedElements();
	        boolean found = false;
	        for (INamedElement element : ownedElements) {
	            logger.trace("check exist package {}", element.getName()); //$NON-NLS-1$
	            if (element.getName().equals(namespace)) {
	                if (element instanceof IPackage) {
	                    parent = (IPackage) element;
	                    found = true;
	                } else {
	                    throw new IllegalArgumentException(Messages.getString("ModelAPI.sameName_existed_error_message")); //$NON-NLS-1$
	                }
	            }
	        }
	        if (found) continue;
	        try {
	            transactionManager.beginTransaction();
	            parent = basicModelEditorFactory.createPackage(parent, namespace);
	            transactionManager.endTransaction();
	        } catch (InvalidEditingException e) {
	            transactionManager.abortTransaction();
	            throw new IllegalStateException(e);
	        }
	    }
	    return parent;
	}

	private ITransactionManager getTransactionManager() {
	    return wrapper.getTransactionManager();
	}

	private IModel getProject() {
	    try {
	        return getProjectAccessor().getProject();
	    } catch (ProjectNotFoundException e) {
	        throw new IllegalStateException(e);
	    }
	}

	private boolean isClosedProject() {
	    return wrapper.isClosedProject();
	}

	private BasicModelEditor getBasicModelEditor() {
		return wrapper.getBasicModelEditor();
	}

	private IViewManager getViewManager() {
	    try {
	        return getProjectAccessor().getViewManager();
	    } catch (InvalidUsingException e) {
	        throw new IllegalStateException(e);
	    }
	}

}
