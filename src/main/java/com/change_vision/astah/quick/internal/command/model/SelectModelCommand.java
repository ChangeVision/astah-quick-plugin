package com.change_vision.astah.quick.internal.command.model;

import static java.lang.String.format;

import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.internal.command.ResourceCommandIconDescription;
import com.change_vision.jude.api.inf.model.INamedElement;

public class SelectModelCommand implements Command {

	private final INamedElement foundModel;
	private static final ModelAPI api = new ModelAPI();

	public SelectModelCommand(INamedElement foundModel) {
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
		return format("%s", fullName);
	}

	@Override
	public boolean isEnable() {
		return true;
	}
	
	@Override
	public CandidateIconDescription getIconDescription() {
		return new ResourceCommandIconDescription("/icons/glyphicons_027_search.png");
	}


}
