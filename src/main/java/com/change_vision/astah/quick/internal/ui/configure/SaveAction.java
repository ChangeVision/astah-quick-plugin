package com.change_vision.astah.quick.internal.ui.configure;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.JWindow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.internal.model.QuickProperties;

@SuppressWarnings("serial")
class SaveAction extends AbstractAction {
    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(SaveAction.class);

    final private ConfigPanel configPanel;

    SaveAction(ConfigPanel configPanel) {
        super("save");
        this.configPanel = configPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String keyStroke = configPanel.getKeyStroke();
        logger.trace("save:{}",keyStroke);
        QuickProperties properties = configPanel.getProperties();
        properties.setKeyStroke(keyStroke);
        JWindow window = configPanel.getWindow();
        window.setVisible(false);
        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSED));
    }
    
}
