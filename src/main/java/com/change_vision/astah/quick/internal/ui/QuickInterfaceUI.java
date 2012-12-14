package com.change_vision.astah.quick.internal.ui;

import java.awt.KeyboardFocusManager;

import javax.swing.SwingUtilities;

public class QuickInterfaceUI {
    

    private KeyboardFocusManager focusManager;
    private OpenQuickWindowEventDispatcher dispatcher;
    
    public QuickInterfaceUI(){
        focusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        dispatcher = new OpenQuickWindowEventDispatcher();
    }
    
    public void install(){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                focusManager.addKeyEventDispatcher(dispatcher);
            }
        });
    }
    
    public void uninstall(){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                focusManager.removeKeyEventDispatcher(dispatcher);
            }
        });
    }

}
