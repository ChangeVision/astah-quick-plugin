package com.change_vision.astah.quick.internal.ui.candidatesfield;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.command.Candidates;
import com.change_vision.astah.quick.internal.ui.candidates.CandidatesListWindow;

final class CommitCommandAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private final CandidatesField field;
	private CandidatesListWindow commandList;
	private static final String KEY = "RIGHT";
	private static final String TAB_KEY = "TAB";

	CommitCommandAction(CandidatesField field, CandidatesListWindow commandList) {
		super("commit-command");
		this.field = field;
		this.commandList = commandList;
		InputMap inputMap = field.getInputMap();
		ActionMap actionMap = field.getActionMap();
		inputMap.put(KeyStroke.getKeyStroke(KEY), KEY);
		actionMap.put(KEY, this);
		inputMap.put(KeyStroke.getKeyStroke(TAB_KEY), TAB_KEY);
		actionMap.put(TAB_KEY, this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Candidates commands = commandList.getCandidates();
		if(commands.isCommitted()) return;
		Command current = commands.currentCommand();
		field.setText(current.getName());
		commandList.setCandidateText(current.getName());
	}

}
