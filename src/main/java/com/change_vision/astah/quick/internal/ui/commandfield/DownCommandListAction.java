package com.change_vision.astah.quick.internal.ui.commandfield;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

import com.change_vision.astah.quick.internal.ui.CommandListWindow;

final class DownCommandListAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private static final String KEY = "DOWN";
	private final CommandListWindow commandList;

	DownCommandListAction(CommandField field,CommandListWindow commandList) {
		super("down-command");
		this.commandList = commandList;
		InputMap inputMap = field.getInputMap();
		ActionMap actionMap = field.getActionMap();
		inputMap.put(KeyStroke.getKeyStroke(KEY), KEY);
		actionMap.put(KEY, this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		commandList.down();
	}
}