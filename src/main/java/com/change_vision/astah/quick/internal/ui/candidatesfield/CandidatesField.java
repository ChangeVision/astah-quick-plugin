package com.change_vision.astah.quick.internal.ui.candidatesfield;

import java.awt.Font;
import java.awt.Point;

import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.internal.command.Candidates;
import com.change_vision.astah.quick.internal.ui.QuickWindow;
import com.change_vision.astah.quick.internal.ui.candidates.CandidatesListWindow;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.CandidateWindowState;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.SelectCommand;

@SuppressWarnings("serial")
public final class CandidatesField extends JTextField {

	/**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(CandidatesField.class);
    
	private final CandidatesListWindow candidatesList;

	private final QuickWindow quickWindow;
	
	public CandidatesField(QuickWindow quickWindow, CandidatesListWindow candidatesList) {
		this.quickWindow = quickWindow;
		this.candidatesList = candidatesList;
		setFont(new Font("Dialog", Font.PLAIN, 32));
		setColumns(16);
		setEditable(true);
		new ExecuteCommandAction(this,this.quickWindow,this.candidatesList);
		new CommitCommandAction(this,this.candidatesList);
		new UpCandidatesListAction(this,this.candidatesList);
		new DownCandidatesListAction(this,this.candidatesList);

		CandidatesFieldDocumentListener listener = new CandidatesFieldDocumentListener(this,this.candidatesList);
		getDocument().addDocumentListener(listener);
	}

	public void setWindowState(CandidateWindowState windowState) {
		switch (windowState) {
		case Inputing:
			openCandidatesList();
			break;
		case Wait:
			closeCandidatesList();
			break;
		default:
			throw new IllegalStateException("Illegal state of window: '" + windowState.name() + "'");
		}
	}
	
	private void openCandidatesList() {
		if (candidatesList.isVisible() == false) {
			logger.trace("openCandidatesList");
			Point location = (Point) quickWindow.getLocation().clone();
			location.translate(0, quickWindow.getSize().height);
			candidatesList.setLocation(location);
			candidatesList.setAlwaysOnTop(true);
			candidatesList.open();
		}
	}

	private void closeCandidatesList() {
		logger.trace("closeCandidatesList");
		Candidates commands = candidatesList.getCandidates();
		commands.setCandidateState(new SelectCommand());
		setText(null);
		candidatesList.close();
	}
	
}
