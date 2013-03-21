package com.change_vision.astah.quick.internal.ui.candidatesfield;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.internal.command.Candidates;
import com.change_vision.astah.quick.internal.ui.CandidateDecider;
import com.change_vision.astah.quick.internal.ui.QuickWindow;

@SuppressWarnings("serial")
final class CommitOrExecuteCommandAction extends AbstractAction {
    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(CommitOrExecuteCommandAction.class);

    private final CandidateDecider decider;

    private final Candidates candidates;

    CommitOrExecuteCommandAction(CandidatesField field, QuickWindow quickWindow,Candidates candidates) {
        super("commit-or-execute-command");
        this.decider = new CandidateDecider(quickWindow, field);
        this.candidates = candidates;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        logger.trace("press enter");
        decider.decide(candidates);
    }
}
