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

public class CreateClassCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(CreateClassCommand.class);

    private final ModelAPI api = new ModelAPI();

    @Override
    public String getName() {
        return "create class";
    }

    @Override
    public void execute(String... args) throws ExecuteCommandException {
        if (args == null || args.length == 0)
            throw new IllegalArgumentException("'create class' command needs argument.");
        for (String className : args) {
            logger.trace("create class '{}'", className);
            INamedElement[] found = api.find(className);
            if (found.length > 0) {
                String message = format("'%s' is already existed.",className);
                throw new ExecuteCommandException(message);
            }
            api.createClass(className);
        }
    }

    @Override
    public String getDescription() {
        return "create class [classname]";
    }

    @Override
    public boolean isEnabled() {
        return api.isOpenedProject();
    }

    @Override
    public CandidateIconDescription getIconDescription() {
        return new AstahCommandIconDescription(IconDescription.UML_CLASS_CLASS);
    }

}
