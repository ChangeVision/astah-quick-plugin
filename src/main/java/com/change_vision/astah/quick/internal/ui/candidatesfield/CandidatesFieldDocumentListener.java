package com.change_vision.astah.quick.internal.ui.candidatesfield;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.change_vision.astah.quick.internal.ui.candidates.CandidatesListWindow;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.CandidateWindowState;

final class CandidatesFieldDocumentListener implements DocumentListener {
	
	private final CandidatesField field;

	private CandidatesListWindow candidatesList;

	public CandidatesFieldDocumentListener(CandidatesField candidatesField,CandidatesListWindow candidatesList) {
		this.field = candidatesField;
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
		candidatesList.setCandidateText(candidateText);
		boolean isEmptyText = candidateText == null
				|| candidateText.isEmpty();
		if (isEmptyText) {
			field.setWindowState(CandidateWindowState.Wait);
		} else {
			field.setWindowState(CandidateWindowState.Inputing);
		}
	}

}
