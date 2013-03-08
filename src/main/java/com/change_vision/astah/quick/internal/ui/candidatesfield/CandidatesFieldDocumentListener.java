package com.change_vision.astah.quick.internal.ui.candidatesfield;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.change_vision.astah.quick.internal.command.CommandExecutor;
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
		handleCandidatesList();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		handleCandidatesList();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
        CommandExecutor executor = field.getExecutor();
        String commandText = executor.getCommandText();
        String text = field.getText();
        if (text.length() != 0 && commandText.length() > text.length()) {
            executor.removeCandidate();
        }
        String candidateText = field.getCandidateText();
        candidatesList.setCandidateText(candidateText);
	}

	private void handleCandidatesList() {
		String candidateText = field.getCandidateText();
		candidatesList.setCandidateText(candidateText);
		CommandExecutor executor = field.getExecutor();
        if (isNullOrEmpty(candidateText)) {
		    if (executor.isCommited()) {
		        field.setWindowState(CandidateWindowState.ArgumentWait);
            }else {
                field.setWindowState(CandidateWindowState.Wait);
            }
		} else {
            if (executor.isCommited()) {
                field.setWindowState(CandidateWindowState.ArgumentInputing);
            }else {
                field.setWindowState(CandidateWindowState.Inputing);
            }
		}
	}

    private boolean isNullOrEmpty(String string) {
        return string == null	|| string.isEmpty();
    }

}
