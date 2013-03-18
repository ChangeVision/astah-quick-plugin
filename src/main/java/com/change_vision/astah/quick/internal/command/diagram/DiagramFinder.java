package com.change_vision.astah.quick.internal.command.diagram;

import com.change_vision.jude.api.inf.model.IDiagram;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.project.ModelFinder;

final class DiagramFinder implements ModelFinder {
    final String key;

    DiagramFinder(String key) {
        this.key = key;
    }

    @Override
    public boolean isTarget(INamedElement element) {
        if (isDiagram(element) == false) {
            return false;
        }
        if (key.isEmpty()) {
            return true;
        }
    	String targetName = element.getName().toLowerCase();
    	String lowerCaseKey = key.toLowerCase();
        boolean isSameNameLowerCase = targetName.startsWith(lowerCaseKey);
    	boolean isSameNameSpaceLowerCase = element.getFullName(".").toLowerCase().startsWith(lowerCaseKey);
        return isSameNameLowerCase || isSameNameSpaceLowerCase;
    }

    private boolean isDiagram(INamedElement element) {
    	return element instanceof IDiagram;
    }
}
