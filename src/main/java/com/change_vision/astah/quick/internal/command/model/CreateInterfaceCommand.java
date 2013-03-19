package com.change_vision.astah.quick.internal.command.model;

import static java.lang.String.format;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.command.exception.ExecuteCommandException;
import com.change_vision.astah.quick.internal.Messages;
import com.change_vision.astah.quick.internal.annotations.TestForMethod;
import com.change_vision.astah.quick.internal.command.AstahCommandIconDescription;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.view.IconDescription;

public class CreateInterfaceCommand implements Command{

	private ModelAPI api = new ModelAPI();

	private static final Logger logger = LoggerFactory.getLogger(CreateInterfaceCommand.class);

	@Override
	public String getName() {
		return "create interface"; //$NON-NLS-1$
	}

	@Override
	public void execute(String... args) throws ExecuteCommandException {
		if(args == null || args.length == 0) throw new IllegalArgumentException(Messages.getString("CreateInterfaceCommand.argument_error_message")); //$NON-NLS-1$
		for (String interfaceName : args) {
			logger.trace("create interface '{}'",interfaceName); //$NON-NLS-1$
            INamedElement[] found = api.findByFQCN(interfaceName);
            if (found.length > 0) {
                String message = format(Messages.getString("CreateInterfaceCommand.already_existed_error_message"),interfaceName); //$NON-NLS-1$
                throw new ExecuteCommandException(message);
            }
			api.createInterface(interfaceName);
		}
	}

	@Override
	public String getDescription() {
		return Messages.getString("CreateInterfaceCommand.description"); //$NON-NLS-1$
	}
	
	@Override
	public boolean isEnabled() {
		return api.isOpenedProject();
	}
	
	@Override
	public CandidateIconDescription getIconDescription() {
		return new AstahCommandIconDescription(IconDescription.UML_CLASS_INTERFACE);
	}

	@TestForMethod
    void setAPI(ModelAPI api) {
	    this.api = api;
    }

}
