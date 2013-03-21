package com.change_vision.astah.quick.internal.command.model;

import java.util.ArrayList;
import java.util.List;

import com.change_vision.astah.quick.command.candidates.PrimitiveCandidate;

class PrimitiveCandidates {

    static final PrimitiveCandidate[] DEFINED_CANDIDATES = new PrimitiveCandidate[]{
        new PrimitiveCandidate("byte"), //$NON-NLS-1$
        new PrimitiveCandidate("short"), //$NON-NLS-1$
        new PrimitiveCandidate("int"), //$NON-NLS-1$
        new PrimitiveCandidate("long"), //$NON-NLS-1$
        new PrimitiveCandidate("char"), //$NON-NLS-1$
        new PrimitiveCandidate("float"), //$NON-NLS-1$
        new PrimitiveCandidate("double"), //$NON-NLS-1$
        new PrimitiveCandidate("boolean"), //$NON-NLS-1$
    };
    
    PrimitiveCandidate[] find(String key){
        if (key == null || key.isEmpty()) {
            return DEFINED_CANDIDATES;
        }
        List<PrimitiveCandidate> candidates = new ArrayList<PrimitiveCandidate>();
        for (PrimitiveCandidate PrimitiveCandidate : DEFINED_CANDIDATES) {
            if (PrimitiveCandidate.getName().startsWith(key)) {
                candidates.add(PrimitiveCandidate);
            }
        }
        return candidates.toArray(new PrimitiveCandidate[0]);
    }
}
