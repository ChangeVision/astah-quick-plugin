package com.change_vision.astah.quick.internal.modelfinder;

import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.project.ModelFinder;

public final class ClassFinder implements ModelFinder {
    private final String searchKey;

    public ClassFinder(String searchKey) {
        if (searchKey == null) throw new IllegalArgumentException("searchKey is null.");
        this.searchKey = searchKey;
    }

    @Override
    public boolean isTarget(INamedElement element) {
        if (not(isClass(element))) return false;
        String name = element.getName().toLowerCase();
        if (name.isEmpty()) return false;
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

    public String getSearchKey() {
        return this.searchKey;
    }
}
