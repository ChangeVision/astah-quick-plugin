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

class CreatePackageCommand implements Command {

    private ModelAPI api = new ModelAPI();

    private static final Logger logger = LoggerFactory.getLogger(CreatePackageCommand.class);

    @Override
    public String getName() {
        return "create package"; //$NON-NLS-1$
    }

    @Override
    public void execute(String... args) throws ExecuteCommandException {
        if (args == null || args.length == 0)
            throw new IllegalArgumentException(
                    Messages.getString("CreatePackageCommand.argument_error_message")); //$NON-NLS-1$
        for (String packageName : args) {
            logger.trace("create package '{}'", packageName); //$NON-NLS-1$
            INamedElement[] found = api.findByFQCN(packageName);
            if (found.length > 0) {
                String message = format(
                        Messages.getString("CreatePackageCommand.already_existed_error_message"), packageName); //$NON-NLS-1$
                throw new ExecuteCommandException(message);
            }
            api.createPackage(packageName);
        }
    }

    @Override
    public String getDescription() {
        return Messages.getString("CreatePackageCommand.description"); //$NON-NLS-1$
    }

    @Override
    public boolean isEnabled() {
        return api.isOpenedProject();
    }

    @Override
    public CandidateIconDescription getIconDescription() {
        return new AstahCommandIconDescription(IconDescription.UML_CLASS_PACKAGE);
    }

    @TestForMethod
    void setAPI(ModelAPI api) {
        this.api = api;
    }

}
