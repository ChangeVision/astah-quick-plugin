package com.change_vision.astah.quick.internal.ui;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JWindow;
import javax.swing.KeyStroke;

import com.change_vision.astah.quick.internal.command.Commands;

@SuppressWarnings("serial")
public class QuickWindow extends JWindow {
    
    private QuickPanel quickPanel;
    private MessageNotifier notifier;
    private CommandListWindow commandList;
	private Commands commands;

    public QuickWindow(JFrame parent){
        super(parent);
        this.notifier = new MessageNotifier(parent);
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "close-it");
        CloseAction closeAction = new CloseAction(this);
        getRootPane().getActionMap().put("close-it", closeAction);
        this.commands = new Commands();
        this.commandList = new CommandListWindow(commands);
        addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				QuickWindow.this.commandList.setPanelSize(e.getWindow().getSize());
			}
        });
        quickPanel = new QuickPanel(this,this.commandList);
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
    	commandList.close();
        setVisible(false);
        quickPanel.reset();
    }

    public void open() {
        setVisible(true);
    }

}
