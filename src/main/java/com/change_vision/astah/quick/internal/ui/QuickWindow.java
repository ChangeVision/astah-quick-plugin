package com.change_vision.astah.quick.internal.ui;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.KeyEvent;

import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JWindow;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class QuickWindow extends JWindow {
    
    private QuickPanel quickPanel;
    private MessageNotifier notifier;

    public QuickWindow(JFrame parent){
        super(parent);
        this.notifier = new MessageNotifier(parent);
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "close-it");
        CloseAction closeAction = new CloseAction(this);
        getRootPane().getActionMap().put("close-it", closeAction);
        quickPanel = new QuickPanel(this);
        quickPanel.setCloseAction(closeAction);
        add(quickPanel);
        setAlwaysOnTop(true);
        pack();
        Point centerPoint = (Point )GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint().clone();
        Dimension size = getSize();
        centerPoint.translate( - size.width / 2,  - size.height / 2);
        setLocation(centerPoint);
    }
    
    public void notifyError(String title,String message){
    	notifier.notifyError(title, message);
    }

    public void close() {
        setVisible(false);
        quickPanel.reset();
    }

    public void open() {
        setVisible(true);
    }

}
