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
import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.view.IconDescription;

class CreateClassCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(CreateClassCommand.class);

    private ModelAPI api = new ModelAPI();

    @Override
    public String getName() {
        return "create class"; //$NON-NLS-1$
    }

    @Override
    public void execute(String... args) throws ExecuteCommandException {
        if (args == null || args.length == 0)
            throw new IllegalArgumentException(Messages.getString("CreateClassCommand.argument_error_message")); //$NON-NLS-1$
        for (String className : args) {
            logger.trace("create class '{}'", className); //$NON-NLS-1$
            INamedElement[] found = api.findByFQCN(className);
            if (found.length > 0 && isFoundClass(found)) {
                String message = format(Messages.getString("CreateClassCommand.already_existed_error_message"),className); //$NON-NLS-1$
                throw new ExecuteCommandException(message);
            }
            api.createClass(className);
        }
    }

    private boolean isFoundClass(INamedElement[] found) {
        for (INamedElement element : found) {
            if (element instanceof IClass) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getDescription() {
        return Messages.getString("CreateClassCommand.description"); //$NON-NLS-1$
    }

    @Override
    public boolean isEnabled() {
        return api.isOpenedProject();
    }

    @Override
    public CandidateIconDescription getIconDescription() {
        return new AstahCommandIconDescription(IconDescription.UML_CLASS_CLASS);
    }

    @TestForMethod
    void setAPI(ModelAPI api) {
        this.api = api;
    }

}
