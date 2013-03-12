package com.change_vision.astah.quick.internal.command.environment;

import java.awt.Component;

import javax.swing.JOptionPane;

import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.command.annotations.Immediate;
import com.change_vision.astah.quick.internal.command.ResourceCommandIconDescription;

@Immediate
public class QuitCommand implements Command {
    
    private EnvironmentAPI api = new EnvironmentAPI();

    public String getName() {
        return "quit Astah";
    }

    public void execute(String... args) {
        Component frame = api.getMainFrame();
        if (api.isModifiedProject()) {
            int confirm = JOptionPane.showConfirmDialog(frame, "Do you want to save this file before quit Astah?", "Quit and Save", JOptionPane.YES_NO_CANCEL_OPTION);
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
            int confirm = JOptionPane.showConfirmDialog(frame , "Do you want to quit Astah?", "Quit", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.OK_OPTION) {
                System.exit(0);
                return;
            }
        }
    }

    @Override
    public String getDescription() {
        return "quit Astah(more suitable Ctrl+Q)";
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public CandidateIconDescription getIconDescription() {
        return new ResourceCommandIconDescription("/icons/glyphicons_207_remove_2.png");
    }
}
