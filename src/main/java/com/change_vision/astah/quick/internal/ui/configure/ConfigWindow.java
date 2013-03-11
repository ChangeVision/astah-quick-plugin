package com.change_vision.astah.quick.internal.ui.configure;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;

@SuppressWarnings("serial")
public class ConfigWindow extends JWindow {
    
    public ConfigWindow(JFrame parent) {
        super(parent);
        JPanel configPanel = new ConfigPanel(this);
        add(configPanel);
        pack();
    }

}
