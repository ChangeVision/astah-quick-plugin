package com.change_vision.astah.quick.internal.ui;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JWindow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.internal.command.Commands;

@SuppressWarnings("serial")
public final class CommandListWindow extends JWindow {

	/**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(CommandListWindow.class);

    private CommandWindowPanel panel;

	public CommandListWindow(Commands commands){
        panel = new CommandWindowPanel(commands);
        setContentPane(panel);
    }

	public void setCommandCandidateText(String commandCandidateText) {
		panel.updateCandidateText(commandCandidateText);
	}

	public void up() {
		logger.trace("up");
		panel.up();
	}

	public void down() {
		logger.trace("down");
		panel.down();
	}
	
	public void close(){
		logger.trace("close");
		setVisible(false);
	}
	
	public WindowListener getWindowListener(){
		return new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				CommandListWindow.this.setPanelSize(e.getWindow().getSize());
			}
			@Override
			public void windowClosing(WindowEvent e) {
				CommandListWindow.this.close();
			}
		};
	}

	protected void setPanelSize(Dimension size) {
		panel.setPreferredSize(new Dimension(size.width,200));
        pack();
	}

}
