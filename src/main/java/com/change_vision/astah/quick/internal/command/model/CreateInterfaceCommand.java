package com.change_vision.astah.quick.internal.command.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.command.AstahCommandIconDescription;
import com.change_vision.jude.api.inf.view.IconDescription;

public class CreateInterfaceCommand implements Command{

	private final ModelAPI api = new ModelAPI();

	private static final Logger logger = LoggerFactory.getLogger(CreateInterfaceCommand.class);

	@Override
	public String getName() {
		return "create interface";
	}

	@Override
	public void execute(String... args) {
		if(args == null || args.length == 0) throw new IllegalArgumentException("'create ingerface' command needs argument.");
		for (String interfaceName : args) {
			logger.trace("create interface '{}'",interfaceName);
			api.createInterface(interfaceName);
		}
	}

	@Override
	public String getDescription() {
		return "create interface [interface name]";
	}
	
	@Override
	public boolean isEnable() {
		return api.isOpenedProject();
	}
	
	@Override
	public CandidateIconDescription getIconDescription() {
		return new AstahCommandIconDescription(IconDescription.UML_CLASS_INTERFACE);
	}

}
