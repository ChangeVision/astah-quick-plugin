package com.change_vision.astah.quick.internal.ui.candidatesfield;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.command.Candidates;
import com.change_vision.astah.quick.internal.command.CommandExecutor;
import com.change_vision.astah.quick.internal.ui.candidates.CandidatesListWindow;

final class CommitCommandAction extends AbstractAction {
    private static final long serialVersionUID = 1L;
    private final CandidatesField field;
    private final CandidatesListWindow commandList;
    private static final String RIGHT = "RIGHT";

    CommitCommandAction(CandidatesField field, CandidatesListWindow commandList) {
        super("commit-command");
        this.field = field;
        this.commandList = commandList;
        InputMap inputMap = field.getInputMap();
        ActionMap actionMap = field.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, KeyEvent.CTRL_DOWN_MASK), RIGHT);
        actionMap.put(RIGHT, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        commitCandidate();
    }

    private void commitCandidate() {
        Candidates candidates = commandList.getCandidates();
        Candidate candidate = candidates.current();
        CommandExecutor executor = this.field.getExecutor();
        if (executor.isCommited()) {
            executor.add(candidate);
            postCommit(candidate);
            return;
        }
        if (candidate instanceof Command) {
            Command command = (Command) candidate;
            executor.commit(command);
            postCommit(candidate);
        }
    }

    private void postCommit(Candidate candidate) {
        String candidateName = candidate.getName();
        field.setText(candidateName);
        commandList.setCandidateText(candidateName);
    }

    protected void setCommandNameToFieldOrMoveEndPosition(String commandName) {
        String text = this.field.getText();
        int position = text.length();
        if (text.startsWith(commandName)) {
            this.field.setCaretPosition(position);
        } else {
            this.field.setText(commandName);
        }
    }
}
