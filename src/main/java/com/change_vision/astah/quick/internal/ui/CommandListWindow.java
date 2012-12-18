package com.change_vision.astah.quick.internal.ui;

import javax.swing.JWindow;

@SuppressWarnings("serial")
public final class CommandListWindow extends JWindow {
    
    public CommandListWindow(){
        CommandWindowPanel panel = new CommandWindowPanel();
        add(panel);
        pack();
    }
}
