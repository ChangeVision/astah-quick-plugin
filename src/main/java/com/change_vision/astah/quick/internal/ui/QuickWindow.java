package com.change_vision.astah.quick.internal.ui;

import java.awt.event.KeyEvent;

import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JWindow;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class QuickWindow extends JWindow {
    
    private QuickPanel quickPanel;

    public QuickWindow(JFrame parent){
        super(parent);
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "close-it");
        CloseAction closeAction = new CloseAction(this);
        getRootPane().getActionMap().put("close-it", closeAction);
        quickPanel = new QuickPanel(this);
        quickPanel.setCloseAction(closeAction);
        add(quickPanel);
        setLocationRelativeTo(parent);
        setAlwaysOnTop(true);
        pack();
    }

    public void close() {
        setVisible(false);
    }

    public void open() {
        quickPanel.reset();
        setVisible(true);
    }

}
