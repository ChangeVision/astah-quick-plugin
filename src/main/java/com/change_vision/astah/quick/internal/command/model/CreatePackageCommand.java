package com.change_vision.astah.quick.internal.command.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.command.AstahCommandIconDescription;
import com.change_vision.jude.api.inf.view.IconDescription;

public class CreatePackageCommand implements Command{
	
	private final ModelAPI api = new ModelAPI();
	
	private static final Logger logger = LoggerFactory.getLogger(CreatePackageCommand.class);

	@Override
	public String getName() {
		return "create package";
	}

	@Override
	public void execute(String... args) {
		if(args == null || args.length == 0) throw new IllegalArgumentException("'create package' command needs argument.");
		for (String packageName : args) {
			logger.trace("create package '{}'",packageName);
			api.createPackage(packageName);
		}
	}
	
	@Override
	public String getDescription() {
		return "create package [package name]";
	}
	
	@Override
	public boolean isEnabled() {
		return api.isOpenedProject();
	}
	
	@Override
	public CandidateIconDescription getIconDescription() {
		return new AstahCommandIconDescription(IconDescription.UML_CLASS_PACKAGE);
	}

}
