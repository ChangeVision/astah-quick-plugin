package com.change_vision.astah.quick.internal.ui.candidatesfield;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.internal.command.CommandExecutor;
import com.change_vision.astah.quick.internal.ui.candidates.CandidatesListWindow;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.CandidateWindowState;

final class CandidatesFieldDocumentListener implements DocumentListener {
    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(CandidatesFieldDocumentListener.class);

    private final CandidatesField field;

    private CandidatesListWindow candidatesList;

    public CandidatesFieldDocumentListener(CandidatesField candidatesField,
            CandidatesListWindow candidatesList) {
        this.field = candidatesField;
        this.candidatesList = candidatesList;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        logger.trace("insertUpdate");
        handleCandidatesList();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        logger.trace("changedUpdate");
        handleCandidatesList();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        logger.trace("removeUpdate");
        CommandExecutor executor = field.getExecutor();
        String commandText = executor.getCommandText();
        String text = field.getText();
        if (text.isEmpty() == false && commandText.length() > text.length()) {
            executor.removeCandidate();
        }
        if (text.isEmpty() && field.isSettingText() == false) {
            executor.reset();
        }
        String candidateText = field.getCandidateText();
        candidatesList.setCandidateText(candidateText);
    }

    private void handleCandidatesList() {
        String candidateText = field.getCandidateText();
        String text = field.getText();
        candidatesList.setCandidateText(candidateText);
        if (isNullOrEmpty(candidateText)) {
            if (isNullOrEmpty(text)) {
                field.setWindowState(CandidateWindowState.ArgumentWait);
            } else {
                field.setWindowState(CandidateWindowState.Wait);
            }
        } else {
            if (isNullOrEmpty(text)) {
                field.setWindowState(CandidateWindowState.ArgumentInputing);
            } else {
                field.setWindowState(CandidateWindowState.Inputing);
            }
        }
    }

    private boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

}
