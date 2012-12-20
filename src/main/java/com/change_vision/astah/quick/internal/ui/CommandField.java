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

    private JWindow commandList;

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
                if(commandList == null){
                    commandList = new CommandListWindow();
                }
                CommandField source = (CommandField) e.getSource();
                String text = source.getText();
                if(text == null || text.isEmpty()){
                    commandList.setVisible(false);
                } else{
                	Point location = (Point) parent.getLocation().clone();
                	location.translate(0, 80);
                	logger.trace("commandList:location{}",location);
                    commandList.setLocation(location);
                    commandList.setVisible(true);
                }
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
