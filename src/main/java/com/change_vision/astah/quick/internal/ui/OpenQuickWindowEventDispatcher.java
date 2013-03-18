package com.change_vision.astah.quick.internal.ui;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.KeyEventDispatcher;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.KeyStroke;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.internal.AstahAPIWrapper;
import com.change_vision.astah.quick.internal.command.Commands;
import com.change_vision.astah.quick.internal.model.QuickProperties;

class OpenQuickWindowEventDispatcher implements KeyEventDispatcher {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(OpenQuickWindowEventDispatcher.class);
    
    private final AstahAPIWrapper wrapper = new AstahAPIWrapper();
    
    private final QuickProperties properties = new QuickProperties();

    private QuickWindow window;
    
    private final Commands commands;
    
    OpenQuickWindowEventDispatcher(Commands commands) {
        this.commands = commands;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        KeyStroke keyStroke = KeyStroke.getKeyStrokeForEvent(e);
        String fireKeyStroke = properties.getKeyStroke();
        KeyStroke strokeEvent = KeyStroke.getKeyStroke(fireKeyStroke);
        if(strokeEvent.equals(keyStroke)){
            logger.trace("{} is fired.",fireKeyStroke);
            if(window == null){
                createQuickWindow();
            }
            if(window.isVisible()){
                logger.trace("window is already visible.");
                return false;
            }
            window.open();
            return true;
        }
        return false;
    }
    

    private void createQuickWindow() {
        JFrame frame = wrapper.getMainFrame();
        window = new QuickWindow(frame,commands);
        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener(){
            @Override
            public void eventDispatched(AWTEvent event) {
                int id = event.getID();
                if (id == MouseEvent.MOUSE_CLICKED) {
                    if (event instanceof MouseEvent) {
                        MouseEvent me = (MouseEvent) event;
                        logger.trace("MouseClicked:{}",me);
                        Object source = me.getSource();
                        if (source instanceof Component) {
                            Component c = (Component) source;
                            if(window != null && window.isVisible() && (window.isAncestorOf(c) || c == window) == false ){
                                window.close();
                            }
                        }
                    }
                }
            }
            
        }, AWTEvent.MOUSE_EVENT_MASK);
    }
}
