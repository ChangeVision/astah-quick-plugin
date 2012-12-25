package com.change_vision.astah.quick.internal.ui;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;
import javax.swing.JWindow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public final class CommandField extends JTextField {
    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(CommandField.class);

    private CommandListWindow commandList;

    private JWindow parent;
    
    public CommandField() {
        setFont(new Font("Dialog", Font.PLAIN, 32));
        setColumns(16);
        setEditable(true);
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyReleased(KeyEvent e) {
            	if(isCommandListVisible()){
            		logger.trace("commandList:keyMove{}" , e.getKeyCode());
	    			if(isEnter(e)){
	    				commandList.execute();
	    				e.consume();
	    				return;
	    			}
	            	if (isKeyCursor(e)){
	            			if(isUp(e)){
	            				commandList.up();
	            				e.consume();
	                    		return;
	            			}
	            			if(isDown(e)){
	            				commandList.down();
	            				e.consume();
	                    		return;
	            			}
	            		return;
	            	}
            	}
                if(commandList == null){
                    commandList = new CommandListWindow();
                }
                CommandField source = (CommandField) e.getSource();
                String commandCandidateText = source.getText();
                if(commandCandidateText == null || commandCandidateText.isEmpty()){
                    commandList.setVisible(false);
                } else{
                	Point location = (Point) parent.getLocation().clone();
                	location.translate(0, 85);
                	logger.trace("commandList:location{}",location);
                	commandList.setCommandCandidateText(commandCandidateText);
                	if(commandList.isVisible() == false) {
                		commandList.setLocation(location);
                		commandList.setAlwaysOnTop(true);
                		commandList.setVisible(true);
                	}
                }
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
    
    public void reset() {
        setText(null);
        if(commandList != null){
            commandList.setVisible(false);
        }
    }
    
    public void setParentWindow(JWindow window) {
        this.parent = window;
    }
}
