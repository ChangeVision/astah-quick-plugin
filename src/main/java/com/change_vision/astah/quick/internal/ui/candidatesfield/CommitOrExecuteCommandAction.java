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
import com.change_vision.astah.quick.command.annotations.Immediate;
import com.change_vision.astah.quick.internal.command.Candidates;
import com.change_vision.astah.quick.internal.command.CommandExecutor;
import com.change_vision.astah.quick.internal.ui.QuickWindow;
import com.change_vision.astah.quick.internal.ui.candidates.CandidatesWindowPanel;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.CandidateWindowState;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.ValidState;

final class CommitOrExecuteCommandAction extends AbstractAction {
    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(CommitOrExecuteCommandAction.class);

    private static final long serialVersionUID = 1L;

    private static final String KEY = "ENTER";

    private final CandidatesField field;

    private final QuickWindow quickWindow;

    private final CandidatesWindowPanel candidatesList;

    private CommandExecutor executor;

    CommitOrExecuteCommandAction(CandidatesField field, QuickWindow quickWindow) {
        super("commit-or-execute-command");
        this.field = field;
        InputMap inputMap = field.getInputMap();
        ActionMap actionMap = field.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(KEY), KEY);
        actionMap.put(KEY, this);
        this.quickWindow = quickWindow;
        this.candidatesList = quickWindow.getCandidatesWindowPanel();
        this.executor = field.getExecutor();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        logger.trace("press enter");
        if (candidatesList.isVisible()) {
            commitCandidate();
            return;
        }
        executeCommand();
    }

    private void commitCandidate() {
        Candidates candidates = candidatesList.getCandidates();
        Candidate candidate = candidates.current();
        if (candidate instanceof ValidState) {
            executeCommand();
            return;
        }
        if (executor.isCommited()) {
            executor.add(candidate);
            if (isImmidiateCandidate(candidate)) {
                executeCommand();
                return;
            }
            String commandText = executor.getCommandText() + CommandExecutor.SEPARATE_COMMAND_CHAR;
            field.setText(commandText);
            this.candidatesList.setVisible(false);
            return;
        }
        if (candidate instanceof Command) {
            Command command = (Command) candidate;
            if (isImmidiateCommand(command)) {
                executor.commit(command);
                executeCommand();
                return;
            }
            executor.commit(command);
            field.setWindowState(CandidateWindowState.ArgumentWait);
            String commandText = executor.getCommandText() + CommandExecutor.SEPARATE_COMMAND_CHAR;
            field.setText(commandText);
        }
    }

    private boolean isImmidiateCandidate(Candidate candidate) {
        return candidate.getClass().isAnnotationPresent(Immediate.class);
    }
    
    private boolean isImmidiateCommand(Command command) {
        return command.getClass().isAnnotationPresent(Immediate.class);
    }

    private void executeCommand() {
        String candidateText = field.getText();
        quickWindow.close();
        try {
            executor.execute(candidateText);
        } catch (Exception e) {
            quickWindow.notifyError("Alert", e.getMessage());
        }
        quickWindow.reset();
    }

}
