package com.change_vision.astah.quick.internal.command.model;

import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.model.IPackage;
import com.change_vision.jude.api.inf.project.ModelFinder;

final class ClassOrPackageFinder implements ModelFinder {
    private final String searchKey;

    ClassOrPackageFinder(String searchKey) {
        this.searchKey = searchKey;
    }

    @Override
    public boolean isTarget(INamedElement element) {
        if (not(isClass(element)) && not(isPackage(element))) return false;
        String name = element.getName().toLowerCase();
        String lowerCaseKey = searchKey.toLowerCase();
        boolean nameStarts = name.startsWith(lowerCaseKey);
        boolean alias1Starts = element.getAlias1().startsWith(searchKey);
        boolean alias2Starts = element.getAlias2().startsWith(searchKey);
        boolean isSameNameSpaceLowerCase = element.getFullName(".").toLowerCase().startsWith(lowerCaseKey);
        return nameStarts || alias1Starts || alias2Starts || isSameNameSpaceLowerCase;
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

    public String getSearchKey() {
        return this.searchKey;
    }
}
