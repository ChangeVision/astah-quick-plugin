package com.change_vision.astah.quick.internal.command.model;

import java.util.ArrayList;
import java.util.List;

import com.change_vision.astah.quick.command.candidates.StereotypeCandidate;

class StereotypeCandidates {
    
    private static final StereotypeCandidate[] DEFINED_CANDIDATES = new StereotypeCandidate[]{
        new StereotypeCandidate("actor"), //$NON-NLS-1$
        new StereotypeCandidate("auxiliary"), //$NON-NLS-1$
        new StereotypeCandidate("boundary"), //$NON-NLS-1$
        new StereotypeCandidate("continuous"), //$NON-NLS-1$
        new StereotypeCandidate("control"), //$NON-NLS-1$
        new StereotypeCandidate("discrete"), //$NON-NLS-1$
        new StereotypeCandidate("entity"), //$NON-NLS-1$
        new StereotypeCandidate("focus"), //$NON-NLS-1$
        new StereotypeCandidate("implementationClass"), //$NON-NLS-1$
        new StereotypeCandidate("interface"), //$NON-NLS-1$
        new StereotypeCandidate("metaclass"), //$NON-NLS-1$
        new StereotypeCandidate("noBuffer"), //$NON-NLS-1$
        new StereotypeCandidate("optional"), //$NON-NLS-1$
        new StereotypeCandidate("overwrite"), //$NON-NLS-1$
        new StereotypeCandidate("rate"), //$NON-NLS-1$
        new StereotypeCandidate("realization"), //$NON-NLS-1$
        new StereotypeCandidate("specification"), //$NON-NLS-1$
        new StereotypeCandidate("type"), //$NON-NLS-1$
        new StereotypeCandidate("utility"), //$NON-NLS-1$
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
