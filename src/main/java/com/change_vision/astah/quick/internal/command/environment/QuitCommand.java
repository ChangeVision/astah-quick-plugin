package com.change_vision.astah.quick.internal.command.environment;

import java.awt.Component;

import javax.swing.JOptionPane;

import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.command.annotations.Immediate;
import com.change_vision.astah.quick.internal.Messages;
import com.change_vision.astah.quick.internal.command.ResourceCommandIconDescription;

@Immediate
public class QuitCommand implements Command {
    
    private EnvironmentAPI api = new EnvironmentAPI();

    public String getName() {
        return "quit Astah"; //$NON-NLS-1$
    }

    public void execute(String... args) {
        Component frame = api.getMainFrame();
        if (api.isModifiedProject()) {
            int confirm = JOptionPane.showConfirmDialog(frame, Messages.getString("QuitCommand.confirm_before_save_and_quit_message"), Messages.getString("QuitCommand.confirm_before_save_and_quit_title"), JOptionPane.YES_NO_CANCEL_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
            switch (confirm) {
            case JOptionPane.YES_OPTION:
                api.save();
                System.exit(0);
                break;
            case JOptionPane.NO_OPTION:
                System.exit(0);
            default:
                break;
            }
        }else {
            int confirm = JOptionPane.showConfirmDialog(frame , Messages.getString("QuitCommand.confirm_before_quit_message"), Messages.getString("QuitCommand.confirm_before_quit_title"), JOptionPane.YES_NO_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
            if (confirm == JOptionPane.OK_OPTION) {
                System.exit(0);
                return;
            }
        }
    }

    @Override
    public String getDescription() {
        return Messages.getString("QuitCommand.description"); //$NON-NLS-1$
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public CandidateIconDescription getIconDescription() {
        return new ResourceCommandIconDescription("/icons/glyphicons_207_remove_2.png"); //$NON-NLS-1$
    }
}
