package com.change_vision.astah.quick.internal.command.model;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.internal.AstahAPIWrapper;
import com.change_vision.astah.quick.internal.annotations.TestForMethod;
import com.change_vision.jude.api.inf.editor.BasicModelEditor;
import com.change_vision.jude.api.inf.editor.IModelEditorFactory;
import com.change_vision.jude.api.inf.editor.ITransactionManager;
import com.change_vision.jude.api.inf.exception.InvalidEditingException;
import com.change_vision.jude.api.inf.exception.InvalidUsingException;
import com.change_vision.jude.api.inf.exception.ProjectNotFoundException;
import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.model.IModel;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.model.IPackage;
import com.change_vision.jude.api.inf.project.ModelFinder;
import com.change_vision.jude.api.inf.project.ProjectAccessor;
import com.change_vision.jude.api.inf.view.IProjectViewManager;
import com.change_vision.jude.api.inf.view.IViewManager;

public class ModelAPI {

    private static final Logger logger = LoggerFactory.getLogger(ModelAPI.class);

    private AstahAPIWrapper wrapper = new AstahAPIWrapper();

    boolean isOpenedProject() {
        return wrapper.isOpenedProject();
    }

    private ProjectAccessor getProjectAccessor() {
        return wrapper.getProjectAccessor();
    }

    void createClass(String className) {
        if (className == null) throw new IllegalArgumentException("className is null.");
        IPackage parent = getProject();
        ITransactionManager transactionManager = getTransactionManager();
        BasicModelEditor basicModelEditorFactory = getBasicModelEditorFactory();

        String[] namespaces = className.split("\\.");
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
        if (interfaceName == null) throw new IllegalArgumentException("interfaceName is null.");
        INamedElement[] found = find(interfaceName);
        if (found.length > 0)
            throw new IllegalArgumentException("The name of element is already existed.");
        IPackage parent = getProject();
        ITransactionManager transactionManager = getTransactionManager();
        BasicModelEditor basicModelEditorFactory = getBasicModelEditorFactory();

        String[] namespaces = interfaceName.split("\\.");
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
        String[] namespaces = packageName.split("\\.");
        createPackage(project, namespaces);
    }

    private IPackage createPackage(IPackage parent, String[] namespaces) {
        ITransactionManager transactionManager = getTransactionManager();
        BasicModelEditor basicModelEditorFactory = getBasicModelEditorFactory();
        for (int i = 0; i < namespaces.length; i++) {
            String namespace = namespaces[i];
            INamedElement[] ownedElements = parent.getOwnedElements();
            boolean found = false;
            for (INamedElement element : ownedElements) {
                logger.trace("check exist package {}", element.getName());
                if (element.getName().equals(namespace)) {
                    if (element instanceof IPackage) {
                        parent = (IPackage) element;
                        found = true;
                    } else {
                        throw new IllegalArgumentException("Same name is existed.");
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
        return getProjectAccessor().getTransactionManager();
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
        logger.trace("find:{}", searchKey);
        if (isClosedProject()) return new INamedElement[0];
        try {
            return getProjectAccessor().findElements(new ModelFinder() {
                @Override
                public boolean isTarget(INamedElement element) {
                    if (not(isClass(element)) && not(isPackage(element))) return false;
                    String name = element.getName().toLowerCase();
                    boolean nameStarts = name.startsWith(searchKey.toLowerCase());
                    boolean alias1Starts = element.getAlias1().startsWith(searchKey);
                    boolean alias2Starts = element.getAlias2().startsWith(searchKey);
                    return nameStarts || alias1Starts || alias2Starts;
                }

                private boolean not(boolean bool) {
                    return !bool;
                }

                private boolean isClass(INamedElement element) {
                    return element instanceof IClass;
                }

                private boolean isPackage(INamedElement element) {
                    return element instanceof IPackage;
                }
            });
        } catch (ProjectNotFoundException e) {
            throw new IllegalArgumentException("It maybe occurred by class path issue.");
        }
    }

    private boolean isClosedProject() {
        return wrapper.isClosedProject();
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

    @TestForMethod
    public void setWrapper(AstahAPIWrapper wrapper) {
        this.wrapper = wrapper;
    }

}
