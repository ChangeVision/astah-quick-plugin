package com.change_vision.astah.quick.internal.ui;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DefaultEditorKit.DefaultKeyTypedAction;
import javax.swing.text.JTextComponent;
import javax.swing.text.Keymap;
import javax.swing.text.TextAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.command.Commands;

@SuppressWarnings("serial")
public final class CommandField extends JTextField {

	private static final class ExecuteCommandAction extends AbstractAction {
		private static final String SEPARATE_COMMAND_CHAR = " ";
		static final KeyStroke KEY_STROKE = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0);
        private final CommandField field;

		private ExecuteCommandAction(CommandField field){
			super("execute-command");
			this.field = field;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			logger.trace("execute");
			final Commands commands = field.commands;
			Command current = commands.current();
			String commandName = getCommandName();
			String[] splitedCommand = field.getText().split(SEPARATE_COMMAND_CHAR);
			String[] args = null;
			int commandRange = commandName.split(SEPARATE_COMMAND_CHAR).length;
			if(splitedCommand.length > commandRange){
				args = Arrays.copyOfRange(splitedCommand, commandRange, splitedCommand.length);
			}
			logger.trace("commandList:execute commandName:'{}',args:'{}'",commandName,args);
			current.execute(args);
			field.quickWindow.close();
		}
		
		private String getCommandName() {
			final Commands commands = field.commands;
			Command current = commands.current();
			String commandName = current.getCommandName();
			return commandName;
		}
	}
	
	private static final class CommandListWindowAction extends DefaultKeyTypedAction {
        private final CommandField field;
		
		private CommandListWindowAction(CommandField field){
			this.field = field;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			super.actionPerformed(e);
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
	
	private static final class UpCommandListAction extends AbstractAction {
        private final CommandField field;
		
		private UpCommandListAction(String name,CommandField field){
			super(name);
			this.field = field;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			logger.trace("commandList:up");
			field.commandList.up();
			String commandName = getCommandName();
			field.setText(commandName);
		}
		
		private String getCommandName() {
			final Commands commands = field.commands;
			Command current = commands.current();
			String commandName = current.getCommandName();
			return commandName;
		}
	}
	
	private static final class DownCommandListAction extends AbstractAction {
        private final CommandField field;
		
		private DownCommandListAction(String name,CommandField field){
			super(name);
			this.field = field;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			logger.trace("commandList:down");
			field.commandList.down();
			String commandName = getCommandName();
			field.setText(commandName);
		}
		
		private String getCommandName() {
			final Commands commands = field.commands;
			Command current = commands.current();
			String commandName = current.getCommandName();
			return commandName;
		}
	}    
    private static final class CommitCommandAction extends AbstractAction {
        private final CommandField field;
		private CommitCommandAction(String name,CommandField field) {
			super(name);
			this.field = field;
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
        Keymap customizedKeyMap = JTextComponent.addKeymap("command-field", getKeymap());
        customizedKeyMap.addActionForKeyStroke(ExecuteCommandAction.KEY_STROKE,new ExecuteCommandAction(this));
        customizedKeyMap.setDefaultAction(new CommandListWindowAction(this));
        setKeymap(customizedKeyMap);
    }
    
    @Override
    public Action[] getActions() {
    	return TextAction.augmentList(super.getActions(), new Action[]{
    		new CommitCommandAction(DefaultEditorKit.forwardAction,this),
    		new UpCommandListAction(DefaultEditorKit.beginLineAction,this),
    		new DownCommandListAction(DefaultEditorKit.endLineAction,this),
    	});
    }
    
    public void reset() {
        setText(null);
        if(commandList != null){
            commandList.setVisible(false);
        }
    }
    
}
