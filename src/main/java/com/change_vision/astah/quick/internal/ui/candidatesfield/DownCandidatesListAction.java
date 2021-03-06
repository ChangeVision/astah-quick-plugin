package com.change_vision.astah.quick.internal.ui.candidatesfield;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

import com.change_vision.astah.quick.internal.ui.candidates.CandidatesListPanel;

final class DownCandidatesListAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private static final String KEY = "DOWN";
	private final CandidatesListPanel candidatesList;

	DownCandidatesListAction(CandidatesField field,CandidatesListPanel candidatesList) {
		super("down-command");
		this.candidatesList = candidatesList;
		InputMap inputMap = field.getInputMap();
		ActionMap actionMap = field.getActionMap();
		inputMap.put(KeyStroke.getKeyStroke(KEY), KEY);
		actionMap.put(KEY, this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		candidatesList.down();
	}
}