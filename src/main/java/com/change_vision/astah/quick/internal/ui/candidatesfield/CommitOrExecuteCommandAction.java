package com.change_vision.astah.quick.internal.ui.candidatesfield;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.command.exception.ExecuteCommandException;
import com.change_vision.astah.quick.internal.command.Candidates;
import com.change_vision.astah.quick.internal.command.CommandExecutor;
import com.change_vision.astah.quick.internal.ui.QuickWindow;
import com.change_vision.astah.quick.internal.ui.candidates.CandidatesListWindow;

final class CommitOrExecuteCommandAction extends AbstractAction {
    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(CommitOrExecuteCommandAction.class);

    private static final long serialVersionUID = 1L;

    private static final String KEY = "ENTER";

    private final CandidatesField field;

    private final QuickWindow quickWindow;

    private final CandidatesListWindow candidatesList;

    private CommandExecutor executor;

    CommitOrExecuteCommandAction(CandidatesField field, QuickWindow quickWindow,
            CandidatesListWindow candidatesList) {
        super("commit-or-execute-command");
        this.field = field;
        InputMap inputMap = field.getInputMap();
        ActionMap actionMap = field.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(KEY), KEY);
        actionMap.put(KEY, this);
        this.quickWindow = quickWindow;
        this.candidatesList = candidatesList;
        this.executor = field.getExecutor();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        logger.trace("press enter");
        if (candidatesList.isVisible()) {
            commitCandidate();
            return;
        }
        executeComamnd();
    }

    private void commitCandidate() {
        Candidates candidates = candidatesList.getCandidates();
        Candidate candidate = candidates.current();
        if (executor.isCommited()) {
            executor.add(candidate);
            String candidateName = candidate.getName();
            String text = field.getText();
            field.setText(text +  candidateName);
            candidatesList.setCandidateText("");
            candidatesList.close();
            return;
        }
        if (candidate instanceof Command) {
            Command command = (Command) candidate;
            executor.commit(command);
            String candidateName = candidate.getName();
            field.setText(candidateName);
            candidatesList.setCandidateText(candidateName);
            candidatesList.close();
        }
    }

    private void executeComamnd() {
        String candidateText = field.getText();
        try {
            executor.execute(candidateText);
        } catch (ExecuteCommandException e) {
            quickWindow.notifyError("Alert", e.getMessage());
        }
    }

}
