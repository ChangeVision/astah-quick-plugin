package com.change_vision.astah.quick.internal.ui.candidates;

import java.awt.Dimension;

import javax.swing.JWindow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.internal.command.Candidates;

@SuppressWarnings("serial")
public final class CandidatesListWindow extends JWindow {

	/**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(CandidatesListWindow.class);

    private CandidatesWindowPanel panel;

	private Candidates candidates;

	public CandidatesListWindow(Candidates commands){
		this.candidates = commands;
        panel = new CandidatesWindowPanel(commands);
        setContentPane(panel);
    }

	public void setCandidateText(String candidateText) {
	    logger.trace("setCandidateText:{}",candidateText);
		panel.updateCandidateText(candidateText);
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
	
	public Candidates getCandidates() {
		return candidates;
	}

	public void setPanelSize(Dimension size) {
		panel.setPreferredSize(new Dimension(size.width,200));
        pack();
	}

	public void open() {
		logger.trace("open");
		setVisible(true);
	}

}
