package com.change_vision.astah.quick.internal.ui.candidatesfield;

import java.awt.event.ActionEvent;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidatesProvider;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.command.Candidates;
import com.change_vision.astah.quick.internal.ui.QuickWindow;
import com.change_vision.astah.quick.internal.ui.candidates.CandidatesListWindow;

final class ExecuteCommandAction extends AbstractAction {
    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(ExecuteCommandAction.class);

    private static final long serialVersionUID = 1L;

    private static final String SEPARATE_COMMAND_CHAR = " ";
    private static final String KEY = "ENTER";
    private final CandidatesField field;

    private final QuickWindow quickWindow;

    private final CandidatesListWindow candidatesList;

    ExecuteCommandAction(CandidatesField field, QuickWindow quickWindow,
            CandidatesListWindow candidatesList) {
        super("execute-command");
        this.field = field;
        InputMap inputMap = field.getInputMap();
        ActionMap actionMap = field.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(KEY), KEY);
        actionMap.put(KEY, this);
        this.quickWindow = quickWindow;
        this.candidatesList = candidatesList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        logger.trace("execute");
        Candidates candidates = candidatesList.getCandidates();
        Command command = candidates.currentCommand();
        String commandName = command.getName();
        String fieldText = field.getText();
        Candidate candidate = candidates.current();

        quickWindow.close();
        if (command instanceof CandidatesProvider) {
            if (command.getClass().isInstance(candidate)) {
                candidate = null;
            }
            ((CandidatesProvider) command).execute(candidate);
            return;
        }
        if (fieldText.startsWith(commandName) != false
                && fieldText.length() == commandName.length()) {
            try {
                command.execute();
            } catch (Exception ex) {
                quickWindow.notifyError("Alert", ex.getMessage());
            }
            return;
        }
        String[] splitedCommand = fieldText.split(SEPARATE_COMMAND_CHAR);
        String[] args = null;
        int commandRange = commandName.split(SEPARATE_COMMAND_CHAR).length;
        if (splitedCommand.length > commandRange) {
            args = Arrays.copyOfRange(splitedCommand, commandRange, splitedCommand.length);
        }
        logger.trace("commandList:execute commandName:'{}',args:'{}'", commandName, args);
        try {
            command.execute(args);
        } catch (Exception ex) {
            quickWindow.notifyError("Alert", ex.getMessage());
        }
    }

}
