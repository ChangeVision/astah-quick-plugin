package com.change_vision.astah.quick.internal.command.model;

import static java.lang.String.format;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.command.exception.ExecuteCommandException;
import com.change_vision.astah.quick.internal.command.AstahCommandIconDescription;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.view.IconDescription;

public class CreateInterfaceCommand implements Command{

	private final ModelAPI api = new ModelAPI();

	private static final Logger logger = LoggerFactory.getLogger(CreateInterfaceCommand.class);

	@Override
	public String getName() {
		return "create interface";
	}

	@Override
	public void execute(String... args) throws ExecuteCommandException {
		if(args == null || args.length == 0) throw new IllegalArgumentException("'create ingerface' command needs argument.");
		for (String interfaceName : args) {
			logger.trace("create interface '{}'",interfaceName);
            INamedElement[] found = api.find(interfaceName);
            if (found.length > 0) {
                String message = format("'%s' is already existed.",interfaceName);
                throw new ExecuteCommandException(message);
            }
			api.createInterface(interfaceName);
		}
	}

	@Override
	public String getDescription() {
		return "create interface [interface name]";
	}
	
	@Override
	public boolean isEnabled() {
		return api.isOpenedProject();
	}
	
	@Override
	public CandidateIconDescription getIconDescription() {
		return new AstahCommandIconDescription(IconDescription.UML_CLASS_INTERFACE);
	}

}
