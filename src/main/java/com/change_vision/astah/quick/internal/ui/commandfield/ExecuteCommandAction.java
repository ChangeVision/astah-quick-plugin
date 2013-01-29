package com.change_vision.astah.quick.internal.ui.commandfield;

import java.awt.event.ActionEvent;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.command.Commands;
import com.change_vision.astah.quick.internal.ui.CommandListWindow;
import com.change_vision.astah.quick.internal.ui.QuickWindow;

final class ExecuteCommandAction extends AbstractAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(ExecuteCommandAction.class);

	private static final long serialVersionUID = 1L;

	private static final String SEPARATE_COMMAND_CHAR = " ";
	private static final String KEY = "ENTER";
	private final CommandField field;

	private final QuickWindow quickWindow;

	private final CommandListWindow commandList;

	ExecuteCommandAction(CommandField field,QuickWindow quickWindow,CommandListWindow commandList) {
		super("execute-command");
		this.field = field;
		InputMap inputMap = field.getInputMap();
		ActionMap actionMap = field.getActionMap();
		inputMap.put(KeyStroke.getKeyStroke(KEY), KEY);
		actionMap.put(KEY, this);
		this.quickWindow = quickWindow;
		this.commandList = commandList;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		logger.trace("execute");
		Commands commands = commandList.getCommands();
		Command current = commands.current();
		String commandName = current.getCommandName();
		String fieldText = field.getText();

		quickWindow.close();
		commandList.close();

		if (fieldText.startsWith(commandName) != false
				&& fieldText.length() == commandName.length()) {
			try {
				current.execute();
			} catch (Exception ex) {
				quickWindow.notifyError("Alert", ex.getMessage());
			}
			return;
		}
		String[] splitedCommand = fieldText.split(SEPARATE_COMMAND_CHAR);
		String[] args = null;
		int commandRange = commandName.split(SEPARATE_COMMAND_CHAR).length;
		if (splitedCommand.length > commandRange) {
			args = Arrays.copyOfRange(splitedCommand, commandRange,
					splitedCommand.length);
		}
		logger.trace("commandList:execute commandName:'{}',args:'{}'",
				commandName, args);
		current.execute(args);
	}

}

