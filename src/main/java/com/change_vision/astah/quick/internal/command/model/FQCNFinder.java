package com.change_vision.astah.quick.internal.command.model;

import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.project.ModelFinder;

public class FQCNFinder implements ModelFinder{

    private String searchKey;

    public FQCNFinder(String searchKey) {
        if (searchKey == null) throw new IllegalArgumentException("searchKey is null.");
        this.searchKey = searchKey;
    }

    @Override
    public boolean isTarget(INamedElement element) {
        return element.getFullName(".").equals(searchKey);
    }

    public String getSearchKey() {
        return searchKey;
    }

}
