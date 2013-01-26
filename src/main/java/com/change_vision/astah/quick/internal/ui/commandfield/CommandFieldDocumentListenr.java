package com.change_vision.astah.quick.internal.ui.commandfield;

import java.awt.Point;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.internal.ui.CommandListWindow;
import com.change_vision.astah.quick.internal.ui.QuickWindow;

final class CommandFieldDocumentListenr implements DocumentListener {
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(CommandFieldDocumentListenr.class);
	
	private final CommandField field;

	private QuickWindow quickWindow;
	
	private CommandListWindow commandList;

	public CommandFieldDocumentListenr(CommandField commandField,QuickWindow quickWindow,CommandListWindow commandList) {
		this.field = commandField;
		this.quickWindow = quickWindow;
		this.commandList = commandList;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		changeInputField();
		handleCommandList();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		changeInputField();
		handleCommandList();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		changeInputField();
		handleCommandList();
	}

	private void changeInputField() {
		commandList.setCommandCandidateText(field.getText());
	}

	private void handleCommandList() {
		String commandCandidateText = field.getText();
		if (isCommandListVisible()) {
			if (commandCandidateText == null
					|| commandCandidateText.isEmpty()) {
				logger.trace("commandList:close");
				closeCommandList();
				return;
			}
		} else {
			openCommandList(field, commandCandidateText);
			return;
		}
	}

	private void openCommandList(CommandField field,
			String commandCandidateText) {
		Point location = (Point) quickWindow.getLocation().clone();
		location.translate(0, quickWindow.getSize().height);
		logger.trace("commandList:location{}", location);
		commandList.setCommandCandidateText(commandCandidateText);
		if (commandList.isVisible() == false) {
			commandList.setLocation(location);
			commandList.setAlwaysOnTop(true);
			commandList.setVisible(true);
		}
	}

	private void closeCommandList() {
		commandList.setVisible(false);
	}

	private boolean isCommandListVisible() {
		return commandList != null && commandList.isVisible();
	}
}
