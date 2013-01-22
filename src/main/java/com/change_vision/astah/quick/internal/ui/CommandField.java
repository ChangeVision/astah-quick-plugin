package com.change_vision.astah.quick.internal.ui;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.command.Commands;

@SuppressWarnings("serial")
public final class CommandField extends JTextField {

	private final class CommandFieldDocumentListenr implements
			DocumentListener {
		private final CommandField field;

		public CommandFieldDocumentListenr(CommandField commandField) {
			this.field = commandField;
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
			commandList.setCommandCandidateText(CommandField.this.getText());
		}

		private void handleCommandList() {
			String commandCandidateText = field.getText();
			if(isCommandListVisible()){
		        if(commandCandidateText == null || commandCandidateText.isEmpty()){
		    		logger.trace("commandList:close");
		            closeCommandList();
		            return;
		        }
			} else {
		    	openCommandList(field,commandCandidateText);
		    	return;
			}
		}

		private void openCommandList(CommandField field, String commandCandidateText) {
			Point location = (Point) field.quickWindow.getLocation().clone();
			location.translate(0, 85);
			logger.trace("commandList:location{}",location);
			field.commandList.setCommandCandidateText(commandCandidateText);
			if(field.commandList.isVisible() == false) {
				field.commandList.setLocation(location);
				field.commandList.setAlwaysOnTop(true);
				field.commandList.setVisible(true);
			}
		}

		private void closeCommandList() {
			field.commandList.setVisible(false);
		}

		private boolean isCommandListVisible() {
			return field.commandList != null && field.commandList.isVisible();
		}
	}

	private static final class ExecuteCommandAction extends AbstractAction {
		private static final String SEPARATE_COMMAND_CHAR = " ";
		private static final String KEY = "ENTER";
        private final CommandField field;

		private ExecuteCommandAction(CommandField field){
			super("execute-command");
			this.field = field;
	        InputMap inputMap = field.getInputMap();
	        ActionMap actionMap = field.getActionMap();        
	        inputMap.put(KeyStroke.getKeyStroke(KEY), KEY);
			actionMap.put(KEY, this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			logger.trace("execute");
			final Commands commands = field.commands;
			Command current = commands.current();
			String commandName = getCommandName();
			String fieldText = field.getText();

			field.quickWindow.close();
			field.commandList.close();
			
			if (fieldText.startsWith(commandName) != false && fieldText.length() == commandName.length()) {
				current.execute();
				return;
			}
			String[] splitedCommand = fieldText.split(SEPARATE_COMMAND_CHAR);
			String[] args = null;
			int commandRange = commandName.split(SEPARATE_COMMAND_CHAR).length;
			if(splitedCommand.length > commandRange){
				args = Arrays.copyOfRange(splitedCommand, commandRange, splitedCommand.length);
			}
			logger.trace("commandList:execute commandName:'{}',args:'{}'",commandName,args);
			current.execute(args);
		}
		
		private String getCommandName() {
			final Commands commands = field.commands;
			Command current = commands.current();
			String commandName = current.getCommandName();
			return commandName;
		}
	}

	private static final class UpCommandListAction extends AbstractAction {
        private final CommandField field;
        private static final String KEY ="UP";
		
		private UpCommandListAction(CommandField field){
			super("up-command");
			this.field = field;
	        InputMap inputMap = field.getInputMap();
	        ActionMap actionMap = field.getActionMap();        
	        inputMap.put(KeyStroke.getKeyStroke(KEY), KEY);
			actionMap.put(KEY, this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			logger.trace("commandList:up");
			field.commandList.up();
		}
	}
	
	private static final class DownCommandListAction extends AbstractAction {
        private final CommandField field;
        private static final String KEY ="DOWN";
		
		private DownCommandListAction(CommandField field){
			super("down-command");
			this.field = field;
	        InputMap inputMap = field.getInputMap();
	        ActionMap actionMap = field.getActionMap();        
	        inputMap.put(KeyStroke.getKeyStroke(KEY), KEY);
			actionMap.put(KEY, this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			logger.trace("commandList:down");
			field.commandList.down();
		}
	}    

	private static final class CommitCommandAction extends AbstractAction {
        private final CommandField field;
        private static final String KEY = "RIGHT";
		private CommitCommandAction(CommandField field) {
			super("commit-command");
			this.field = field;
	        InputMap inputMap = field.getInputMap();
	        ActionMap actionMap = field.getActionMap();        
	        inputMap.put(KeyStroke.getKeyStroke(KEY), KEY);
			actionMap.put(KEY, this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Command current = field.commands.current();
			field.setText(current.getCommandName());
			field.commandList.setCommandCandidateText(current.getCommandName());
		}
	}

	/**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(CommandField.class);
    
    private final CommandListWindow commandList;

    private final QuickWindow quickWindow;

	private final Commands commands;
    
    public CommandField(QuickWindow quickWindow, Commands commands) {
    	this.quickWindow = quickWindow;
    	this.commands = commands;
    	this.commandList = new CommandListWindow(commands);
        setFont(new Font("Dialog", Font.PLAIN, 32));
        setColumns(16);
        setEditable(true);
		new ExecuteCommandAction(this);
		new CommitCommandAction(this);
		new UpCommandListAction(this);
		new DownCommandListAction(this);
		
		getDocument().addDocumentListener(new CommandFieldDocumentListenr(this));
    }
    
    public void reset() {
        setText(null);
        if(commandList != null){
            commandList.setVisible(false);
        }
    }
    
}
