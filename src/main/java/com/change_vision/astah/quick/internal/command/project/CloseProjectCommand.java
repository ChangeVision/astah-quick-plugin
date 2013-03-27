package com.change_vision.astah.quick.internal.command.project;

import java.awt.Component;

import javax.swing.JOptionPane;

import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.command.annotations.Immediate;
import com.change_vision.astah.quick.internal.Messages;
import com.change_vision.astah.quick.internal.command.ResourceCommandIconDescription;

@Immediate
public class CloseProjectCommand implements Command {

    private ProjectAPI api = new ProjectAPI();

    @Override
    public String getName() {
        return "close project"; //$NON-NLS-1$
    }

    @Override
    public void execute(String... args) {
        Component frame = api.getMainFrame();
        if (api.isModifiedProject()) {
            String message = Messages.getString("CloseProjectCommand.confirm_before_save_message");
            String title = Messages.getString("CloseProjectCommand.confirm_before_save_title");
            int confirm = JOptionPane.showConfirmDialog(frame, message, title,
                    JOptionPane.YES_NO_CANCEL_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
            switch (confirm) {
            case JOptionPane.CANCEL_OPTION:
                return;
            case JOptionPane.YES_OPTION:
                api.save();
                break;
            }
            
        }
        api.closeProject();
    }

    @Override
    public String getDescription() {
        return Messages.getString("CloseProjectCommand.description"); //$NON-NLS-1$
    }

    @Override
    public boolean isEnabled() {
        return api.isOpenedProject();
    }

    @Override
    public CandidateIconDescription getIconDescription() {
        return new ResourceCommandIconDescription("/icons/glyphicons_207_remove_2.png"); //$NON-NLS-1$
    }

}
