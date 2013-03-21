package com.change_vision.astah.quick.command.candidates;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.internal.command.AstahCommandIconDescription;
import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.view.IconDescription;

public class ClassCandidate implements Candidate {
	
	private IClass clazz;

	public ClassCandidate(IClass clazz){
		this.clazz = clazz;
	}

	@Override
	public String getName() {
		return clazz.getName();
	}

	@Override
	public String getDescription() {
		return clazz.getFullName(".");
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public CandidateIconDescription getIconDescription() {
		return new AstahCommandIconDescription(IconDescription.UML_CLASS_CLASS);
	}
	
	public IClass getCandidate() {
        return clazz;
    }

}
