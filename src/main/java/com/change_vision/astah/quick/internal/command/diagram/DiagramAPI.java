package com.change_vision.astah.quick.internal.command.diagram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.internal.AstahAPIWrapper;
import com.change_vision.jude.api.inf.exception.ProjectNotFoundException;
import com.change_vision.jude.api.inf.model.IDiagram;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.project.ModelFinder;
import com.change_vision.jude.api.inf.project.ProjectAccessor;
import com.change_vision.jude.api.inf.view.IDiagramViewManager;
import com.change_vision.jude.api.inf.view.IViewManager;

class DiagramAPI {
	
	private static final Logger logger = LoggerFactory.getLogger(DiagramAPI.class);
	
	private AstahAPIWrapper wrapper = new AstahAPIWrapper();
	
	void open(IDiagram diagram){
		if(diagram == null) throw new IllegalArgumentException("diagram is null");
		IViewManager viewManager = wrapper.getViewManager();
		IDiagramViewManager diagramViewManager = viewManager.getDiagramViewManager();
		diagramViewManager.open(diagram);
	}
	
	IDiagram[] find(final String name){
		logger.trace("find:{}",name);
		ProjectAccessor projectAccessor = wrapper.getProjectAccessor();
		INamedElement[] elements = null;
		try {
			elements = projectAccessor.findElements(new ModelFinder() {
				@Override
				public boolean isTarget(INamedElement element) {
					String targetName = element.getName().toLowerCase();
					return isDiagram(element) && targetName.startsWith(name);
				}

				private boolean isDiagram(INamedElement element) {
					return element instanceof IDiagram;
				}
			});
		} catch (ProjectNotFoundException e) {
			throw new IllegalStateException(e);
		}
		IDiagram[] diagrams = new IDiagram[elements.length];
		for (int i = 0; i < elements.length; i++) {
			diagrams[i] = (IDiagram)elements[i];
		}
		return diagrams;
	}

	boolean isOpenedProject(){
		return wrapper.isOpenedProject();
	}
}
