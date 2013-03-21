package com.change_vision.astah.quick.internal.command.diagram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.internal.AstahAPIWrapper;
import com.change_vision.astah.quick.internal.Messages;
import com.change_vision.astah.quick.internal.annotations.TestForMethod;
import com.change_vision.astah.quick.internal.modelfinder.ClassOrPackageFinder;
import com.change_vision.astah.quick.internal.modelfinder.DiagramFinder;
import com.change_vision.jude.api.inf.editor.ClassDiagramEditor;
import com.change_vision.jude.api.inf.editor.ITransactionManager;
import com.change_vision.jude.api.inf.editor.UseCaseDiagramEditor;
import com.change_vision.jude.api.inf.exception.InvalidEditingException;
import com.change_vision.jude.api.inf.exception.ProjectNotFoundException;
import com.change_vision.jude.api.inf.model.IClassDiagram;
import com.change_vision.jude.api.inf.model.IDiagram;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.model.IUseCaseDiagram;
import com.change_vision.jude.api.inf.project.ProjectAccessor;
import com.change_vision.jude.api.inf.view.IDiagramViewManager;

class DiagramAPI {

    private static final Logger logger = LoggerFactory.getLogger(DiagramAPI.class);

    private final AstahAPIWrapper wrapper;

    DiagramAPI() {
        this.wrapper = new AstahAPIWrapper();
    }

    @TestForMethod
    DiagramAPI(AstahAPIWrapper wrapper) {
        this.wrapper = wrapper;
    }

    void open(IDiagram diagram) {
        if (diagram == null)
            throw new IllegalArgumentException(Messages.getString("DiagramAPI.open_null_argument")); //$NON-NLS-1$
        IDiagramViewManager diagramViewManager = wrapper.getDiagramViewManager();
        diagramViewManager.open(diagram);
    }

    IDiagram[] find(final String name) {
        if (name == null)
            throw new IllegalArgumentException(Messages.getString("DiagramAPI.find_null_argument")); //$NON-NLS-1$
        logger.trace("find:{}", name); //$NON-NLS-1$
        ProjectAccessor projectAccessor = wrapper.getProjectAccessor();
        DiagramFinder diagramFinder = new DiagramFinder(name);
        INamedElement[] elements = null;
        try {
            elements = projectAccessor.findElements(diagramFinder);
        } catch (ProjectNotFoundException e) {
            throw new IllegalStateException(e);
        }
        IDiagram[] diagrams = new IDiagram[elements.length];
        for (int i = 0; i < elements.length; i++) {
            diagrams[i] = (IDiagram) elements[i];
        }
        return diagrams;
    }

    boolean isOpenedProject() {
        return wrapper.isOpenedProject();
    }

    void close(IDiagram diagram) {
        if (diagram == null)
            throw new IllegalArgumentException(Messages.getString("DiagramAPI.close_null_argument")); //$NON-NLS-1$
        IDiagramViewManager diagramViewManager = wrapper.getDiagramViewManager();
        diagramViewManager.close(diagram);
    }

    boolean isOpenDiagrams() {
        IDiagramViewManager diagramViewManager = wrapper.getDiagramViewManager();
        IDiagram[] openDiagrams = diagramViewManager.getOpenDiagrams();
        return openDiagrams.length != 0;
    }

    void closeCurrentDiagram() {
        IDiagramViewManager diagramViewManager = wrapper.getDiagramViewManager();
        diagramViewManager.closeCurrentDiagramEditor();
    }

    IDiagram[] openDiagrams() {
        IDiagramViewManager diagramViewManager = wrapper.getDiagramViewManager();
        return diagramViewManager.getOpenDiagrams();
    }

    IDiagram getCurrentDiagram() {
        IDiagramViewManager diagramViewManager = wrapper.getDiagramViewManager();
        return diagramViewManager.getCurrentDiagram();
    }

    void closeAll() {
        IDiagramViewManager diagramViewManager = wrapper.getDiagramViewManager();
        diagramViewManager.closeAll();
    }
    
    private ProjectAccessor getProjectAccessor() {
        return wrapper.getProjectAccessor();
    }
    
    private boolean isClosedProject() {
        return wrapper.isClosedProject();
    }

    INamedElement[] findClassOrPackage(final String searchKey) {
        logger.trace("findClassOrPackage:{}", searchKey); //$NON-NLS-1$
        if (isClosedProject()) return new INamedElement[0];
        try {
            ClassOrPackageFinder finder = new ClassOrPackageFinder(searchKey);
            return getProjectAccessor().findElements(finder);
        } catch (ProjectNotFoundException e) {
            throw new IllegalArgumentException("It maybe occurred by class path issue."); //$NON-NLS-1$
        }
    }

    IClassDiagram createClassDiagram(INamedElement owner, String name) {
        ClassDiagramEditor classDiagramEditor = wrapper.getClassDiagramEditor();
        ITransactionManager transactionManager = wrapper.getTransactionManager();
        IClassDiagram diagram;
        try {
            transactionManager.beginTransaction();
            diagram = classDiagramEditor.createClassDiagram(owner, name);
            transactionManager.endTransaction();
        } catch (InvalidEditingException e) {
            transactionManager.abortTransaction();
            throw new IllegalStateException("This API doesn't support in community edition.",e);
        }
        return diagram;
    }

    IUseCaseDiagram createUseCaseDiagram(INamedElement owner, String name) {
        UseCaseDiagramEditor usecaseDiagramEditor = wrapper.getUseCaseDiagramEditor();
        ITransactionManager transactionManager = wrapper.getTransactionManager();
        IUseCaseDiagram diagram;
        try {
            transactionManager.beginTransaction();
            diagram = usecaseDiagramEditor.createUseCaseDiagram(owner, name);
            transactionManager.endTransaction();
        } catch (InvalidEditingException e) {
            transactionManager.abortTransaction();
            throw new IllegalStateException("This API doesn't support in community edition.",e);
        }
        return diagram;
    }
}
