package com.change_vision.astah.quick.internal.ui;

import javax.swing.JWindow;

@SuppressWarnings("serial")
public final class CommandListWindow extends JWindow {
    
    private CommandWindowPanel panel;

	public CommandListWindow(){
        panel = new CommandWindowPanel();
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

	public void execute() {
		panel.execute();
		setVisible(false);
	}
}
