package com.change_vision.astah.quick.internal.command.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.jude.api.inf.model.INamedElement;

public class SelectModelCommandFactory {

    private ModelAPI api = new ModelAPI();

    public List<Candidate> create(String key) {
        INamedElement[] foundModels = api.findClassOrPackage(key);
        if (foundModels == null || foundModels.length == 0) {
            return new ArrayList<Candidate>();
        }
        List<Candidate> candidates = new ArrayList<Candidate>();
        for (INamedElement foundModel : foundModels) {
            Command selectCommand = createSelectCommand(foundModel);
            candidates.add(selectCommand);
        }
        Collections.sort(candidates, new Comparator<Candidate>() {
            @Override
            public int compare(Candidate o1, Candidate o2) {
                return o1.getName().compareTo(o2.getName());
            }
            
        });
        return candidates;
    }

    public void setApi(ModelAPI api) {
        this.api = api;
    }

    private Command createSelectCommand(INamedElement foundModel) {
        return new SelectModelCommand(foundModel);
    }

}