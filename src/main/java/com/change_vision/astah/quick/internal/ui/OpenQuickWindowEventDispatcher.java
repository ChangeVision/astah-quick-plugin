package com.change_vision.astah.quick.internal.ui;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.KeyStroke;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.internal.AstahAPIWrapper;
import com.change_vision.astah.quick.internal.model.QuickProperties;

class OpenQuickWindowEventDispatcher implements KeyEventDispatcher {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(OpenQuickWindowEventDispatcher.class);
    
    private final AstahAPIWrapper wrapper = new AstahAPIWrapper();
    
    private final QuickProperties properties = new QuickProperties();

    private QuickWindow window;

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
        window = new QuickWindow(frame);
    }
}
