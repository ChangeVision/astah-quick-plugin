package com.change_vision.astah.quick.internal.ui.configure;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ConfigWindow extends JFrame {
    
    
    public ConfigWindow(JFrame parent) {
        JPanel configPanel = new ConfigPanel();
        add(configPanel);
        pack();
    }

}
