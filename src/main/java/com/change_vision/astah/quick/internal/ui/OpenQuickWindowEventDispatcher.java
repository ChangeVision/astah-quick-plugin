package com.change_vision.astah.quick.internal.ui;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.KeyStroke;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.internal.AstahAPIWrapper;

class OpenQuickWindowEventDispatcher implements KeyEventDispatcher {
    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(OpenQuickWindowEventDispatcher.class);
    
    private final AstahAPIWrapper wrapper = new AstahAPIWrapper();

    private QuickWindow window;

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        KeyStroke keyStroke = KeyStroke.getKeyStrokeForEvent(e);
        KeyStroke strokeEvent = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, KeyEvent.CTRL_DOWN_MASK);
        if(strokeEvent.equals(keyStroke)){
            logger.trace("ctrl+space is fired.");
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
