package com.change_vision.astah.quick.internal.ui.commandfield;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.command.Commands;
import com.change_vision.astah.quick.internal.ui.CommandListWindow;

final class CommitCommandAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private final CommandField field;
	private CommandListWindow commandList;
	private static final String KEY = "RIGHT";
	private static final String TAB_KEY = "TAB";

	CommitCommandAction(CommandField field, CommandListWindow commandList) {
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
		Commands commands = commandList.getCommands();
		Command current = commands.current();
		field.setText(current.getCommandName());
		commandList.setCommandCandidateText(current.getCommandName());
	}
}
