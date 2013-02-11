package com.change_vision.astah.quick.internal.ui.candidatesfield;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

import com.change_vision.astah.quick.internal.ui.candidates.CandidatesListWindow;

final class UpCommandListAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private CandidatesListWindow commandList;
	private static final String KEY = "UP";

	UpCommandListAction(CandidatesField field,CandidatesListWindow commandList) {
		super("up-command");
		this.commandList = commandList;
		InputMap inputMap = field.getInputMap();
		ActionMap actionMap = field.getActionMap();
		inputMap.put(KeyStroke.getKeyStroke(KEY), KEY);
		actionMap.put(KEY, this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		commandList.up();
	}
}