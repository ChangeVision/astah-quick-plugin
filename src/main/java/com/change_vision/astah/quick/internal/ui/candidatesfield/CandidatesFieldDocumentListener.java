package com.change_vision.astah.quick.internal.ui.candidatesfield;

import java.awt.Point;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.internal.ui.QuickWindow;
import com.change_vision.astah.quick.internal.ui.candidates.CandidatesListWindow;

final class CandidatesFieldDocumentListener implements DocumentListener {
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(CandidatesFieldDocumentListener.class);
	
	private final CandidatesField field;

	private QuickWindow quickWindow;
	
	private CandidatesListWindow candidatesList;

	public CandidatesFieldDocumentListener(CandidatesField candidatesField,QuickWindow quickWindow,CandidatesListWindow candidatesList) {
		this.field = candidatesField;
		this.quickWindow = quickWindow;
		this.candidatesList = candidatesList;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		changeInputField();
		handleCandidatesList();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		changeInputField();
		handleCandidatesList();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		changeInputField();
		handleCandidatesList();
	}

	private void changeInputField() {
		candidatesList.setCandidateText(field.getText());
	}

	private void handleCandidatesList() {
		String candidateText = field.getText();
		if (isCandidatesListVisible()) {
			if (candidateText == null
					|| candidateText.isEmpty()) {
				logger.trace("candidatesList:close");
				closeCandidatesList();
				return;
			}
		} else {
			openCandidatesList(field, candidateText);
			return;
		}
	}

	private void openCandidatesList(CandidatesField field,
			String candidateText) {
		Point location = (Point) quickWindow.getLocation().clone();
		location.translate(0, quickWindow.getSize().height);
		logger.trace("candidatesList:location{}", location);
		candidatesList.setCandidateText(candidateText);
		if (candidatesList.isVisible() == false) {
			candidatesList.setLocation(location);
			candidatesList.setAlwaysOnTop(true);
			candidatesList.setVisible(true);
		}
	}

	private void closeCandidatesList() {
		candidatesList.setVisible(false);
	}

	private boolean isCandidatesListVisible() {
		return candidatesList != null && candidatesList.isVisible();
	}
}
