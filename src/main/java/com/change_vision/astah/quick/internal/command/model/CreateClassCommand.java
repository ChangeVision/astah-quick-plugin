package com.change_vision.astah.quick.internal.command.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.command.CommandIconDescription;
import com.change_vision.astah.quick.internal.command.AstahCommandIconDescription;
import com.change_vision.jude.api.inf.view.IconDescription;

public class CreateClassCommand implements Command{
    
	private static final Logger logger = LoggerFactory.getLogger(CreateClassCommand.class);
	
	private final ModelAPI api = new ModelAPI();

	@Override
	public String getCommandName() {
		return "create class";
	}

	@Override
	public void execute(String... args) {
		if(args == null || args.length == 0) throw new IllegalArgumentException("'create class' command needs argument.");
		for (String className : args) {
			logger.trace("create class '{}'",className);
			api.createClass(className);
		}
	}
	
	@Override
	public String getDescription() {
		return "create class [classname]";
	}
	
	@Override
	public boolean isEnable() {
		return api.isOpenedProject();
	}

	@Override
	public CommandIconDescription getIconDescription() {
		return new AstahCommandIconDescription(IconDescription.UML_CLASS_CLASS);
	}
}
