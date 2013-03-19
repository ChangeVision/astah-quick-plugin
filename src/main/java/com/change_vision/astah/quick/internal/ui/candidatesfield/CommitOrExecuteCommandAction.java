package com.change_vision.astah.quick.internal.ui.candidatesfield;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.internal.command.CommandBuilder;
import com.change_vision.astah.quick.internal.ui.CandidateDecider;
import com.change_vision.astah.quick.internal.ui.QuickWindow;

@SuppressWarnings("serial")
final class CommitOrExecuteCommandAction extends AbstractAction {
    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(CommitOrExecuteCommandAction.class);

    private final CandidateDecider decider;

    private final CommandBuilder builder;

    CommitOrExecuteCommandAction(CandidatesField field, QuickWindow quickWindow,CommandBuilder builder) {
        super("commit-or-execute-command");
        this.decider = new CandidateDecider(quickWindow, field);
        this.builder = builder;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        logger.trace("press enter");
        decider.decide(builder);
    }
}
