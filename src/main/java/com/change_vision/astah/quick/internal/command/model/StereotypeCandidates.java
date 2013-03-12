package com.change_vision.astah.quick.internal.command.model;

import java.util.ArrayList;
import java.util.List;

import com.change_vision.astah.quick.command.candidates.StereotypeCandidate;

class StereotypeCandidates {
    
    private static final StereotypeCandidate[] DEFINED_CANDIDATES = new StereotypeCandidate[]{
        new StereotypeCandidate("actor"),
        new StereotypeCandidate("auxiliary"),
        new StereotypeCandidate("boundary"),
        new StereotypeCandidate("continuous"),
        new StereotypeCandidate("control"),
        new StereotypeCandidate("discrete"),
        new StereotypeCandidate("entity"),
        new StereotypeCandidate("focus"),
        new StereotypeCandidate("implementationClass"),
        new StereotypeCandidate("interface"),
        new StereotypeCandidate("metaclass"),
        new StereotypeCandidate("noBuffer"),
        new StereotypeCandidate("optional"),
        new StereotypeCandidate("overwrite"),
        new StereotypeCandidate("rate"),
        new StereotypeCandidate("realization"),
        new StereotypeCandidate("specification"),
        new StereotypeCandidate("type"),
        new StereotypeCandidate("utility"),
    };
    
    StereotypeCandidate[] find(String key){
        List<StereotypeCandidate> candidates = new ArrayList<StereotypeCandidate>();
        for (StereotypeCandidate stereotypeCandidate : DEFINED_CANDIDATES) {
            if (stereotypeCandidate.getName().startsWith(key)) {
                candidates.add(stereotypeCandidate);
            }
        }
        return candidates.toArray(new StereotypeCandidate[0]);
    }

}
