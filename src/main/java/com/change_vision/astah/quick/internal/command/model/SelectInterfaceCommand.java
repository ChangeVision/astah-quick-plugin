package com.change_vision.astah.quick.internal.command.model;

import static java.lang.String.format;

import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.annotations.Immidiate;
import com.change_vision.astah.quick.internal.command.AstahCommandIconDescription;
import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.view.IconDescription;

@Immidiate
public class SelectInterfaceCommand implements Command {

	private final IClass foundModel;
	private static final ModelAPI api = new ModelAPI();

	public SelectInterfaceCommand(IClass foundModel) {
		this.foundModel = foundModel;
	}

	@Override
	public String getName() {
		return foundModel.getName();
	}

	@Override
	public void execute(String... args) {
		api.showInStructureTree(foundModel);
	}

	@Override
	public String getDescription() {
		String fullName = foundModel.getFullName(".");
		return format("Select interface '%s'", fullName);
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public CandidateIconDescription getIconDescription() {
		return new AstahCommandIconDescription(IconDescription.UML_CLASS_INTERFACE);
	}


}
