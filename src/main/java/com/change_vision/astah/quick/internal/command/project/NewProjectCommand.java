package com.change_vision.astah.quick.internal.command.project;

import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.command.annotations.Immediate;
import com.change_vision.astah.quick.internal.Messages;
import com.change_vision.astah.quick.internal.command.ResourceCommandIconDescription;

@Immediate
public class NewProjectCommand implements Command {

    private ProjectAPI api = new ProjectAPI();

    public String getName() {
        return "new project"; //$NON-NLS-1$
    }

    public void execute(String... args) {
        api.createProject();
    }

    @Override
    public String getDescription() {
        return Messages.getString("NewProjectCommand.description"); //$NON-NLS-1$
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public CandidateIconDescription getIconDescription() {
        return new ResourceCommandIconDescription("/icons/glyphicons_151_new_window.png"); //$NON-NLS-1$
    }
}
