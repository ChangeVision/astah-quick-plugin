package com.change_vision.astah.quick.internal.ui;

import javax.swing.JWindow;

import com.change_vision.astah.quick.internal.command.Commands;

@SuppressWarnings("serial")
public final class CommandListWindow extends JWindow {
    
    private CommandWindowPanel panel;

	public CommandListWindow(Commands commands){
        panel = new CommandWindowPanel(commands);
        setContentPane(panel);
        pack();
    }

	public void setCommandCandidateText(String commandCandidateText) {
		panel.updateCandidateText(commandCandidateText);
	}

	public void up() {
		panel.up();
	}

	public void down() {
		panel.down();
	}

}
