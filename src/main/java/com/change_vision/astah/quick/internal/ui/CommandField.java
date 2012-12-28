package com.change_vision.astah.quick.internal.ui;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTextField;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.TextAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.command.Commands;

@SuppressWarnings("serial")
public final class CommandField extends JTextField {
    private static final class DisableAction extends AbstractAction {
        private static final Logger logger = LoggerFactory.getLogger(DisableAction.class);
		private DisableAction(String name) {
			super(name);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			logger.trace("fired:{}",getValue(Action.NAME));
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
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyReleased(KeyEvent e) {
                CommandField field = (CommandField) e.getSource();
                String commandCandidateText = field.getText();
                final Commands commands = CommandField.this.commands;
            	if(isCommandListVisible()){
                    if(commandCandidateText == null || commandCandidateText.isEmpty()){
                		logger.trace("commandList:close");
                        closeCommandList();
                        return;
                    }
	    			if(isEnter(e)){
						Command current = commands.current();
						String commandName = current.getCommandName();
						logger.trace("commandList:execute",commandName);
						String[] splitedCommand = commandCandidateText.split(" ");
						String[] args = null;
						int commandRange = commandName.split(" ").length;
						if(splitedCommand.length > commandRange){
							args = Arrays.copyOfRange(splitedCommand, commandRange, splitedCommand.length);
						}
	    				current.execute(args);
	    				commandList.setVisible(false);
	    				e.consume();
	    				field.quickWindow.close();
	    				return;
	    			}
	            	if (isKeyCursor(e)){
            			if(isUp(e)){
    						logger.trace("commandList:up");
            				commandList.up();
            				e.consume();
                    		return;
            			}
            			if(isDown(e)){
    						logger.trace("commandList:down");
            				commandList.down();
            				e.consume();
                    		return;
            			}
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
				commandList.setCommandCandidateText(commandCandidateText);
				if(commandList.isVisible() == false) {
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
            
			private boolean isKeyCursor(KeyEvent e) {
				return isUp(e) ||
					isDown(e) ||
					isLeft(e)||
					isRight(e);
			}
			
			private boolean isRight(KeyEvent e) {
				return KeyEvent.VK_RIGHT == e.getKeyCode();
			}
			private boolean isLeft(KeyEvent e) {
				return KeyEvent.VK_LEFT == e.getKeyCode();
			}
			private boolean isDown(KeyEvent e) {
				return KeyEvent.VK_DOWN == e.getKeyCode();
			}
			private boolean isUp(KeyEvent e) {
				return KeyEvent.VK_UP == e.getKeyCode();
			}
			
            private boolean isEnter(KeyEvent e) {
				return KeyEvent.VK_ENTER == e.getKeyCode();
			}
			@Override
            public void keyPressed(KeyEvent e) {
            }
        });
    }
    
    @Override
    public Action[] getActions() {
    	return TextAction.augmentList(super.getActions(), new Action[]{
    		new CommitCommandAction(DefaultEditorKit.forwardAction,this),
    		new DisableAction(DefaultEditorKit.beginLineAction),
    		new DisableAction(DefaultEditorKit.endLineAction),
    	});
    }
    
    public void reset() {
        setText(null);
        if(commandList != null){
            commandList.setVisible(false);
        }
    }
    
}
