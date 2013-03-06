package com.change_vision.astah.quick.internal.ui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JWindow;
import javax.swing.KeyStroke;

import com.change_vision.astah.quick.internal.command.Candidates;
import com.change_vision.astah.quick.internal.command.CommandExecutor;
import com.change_vision.astah.quick.internal.ui.candidates.CandidatesListWindow;

@SuppressWarnings("serial")
public class QuickWindow extends JWindow {
    
    private final QuickPanel quickPanel;
    private final MessageNotifier notifier;
    private final CandidatesListWindow candidatesList;
	private final Candidates candidates;
    private final CommandExecutor executor;

    public QuickWindow(JFrame parent){
        super(parent);
        this.executor = new CommandExecutor();
        this.notifier = new MessageNotifier(parent);
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "close-it");
        CloseAction closeAction = new CloseAction(this);
        getRootPane().getActionMap().put("close-it", closeAction);
        this.candidates = new Candidates(this.executor);
        this.candidatesList = new CandidatesListWindow(candidates);
        this.quickPanel = new QuickPanel(this,this.candidatesList);
        this.quickPanel.setCloseAction(closeAction);
        add(quickPanel);
        setAlwaysOnTop(true);
        pack();
    }
    
    public void notifyError(String title,String message){
    	notifier.notifyError(title, message);
    }

    public void close() {
    	candidatesList.close();
        setVisible(false);
    }
    
    public void reset() {
        quickPanel.reset();
    }

    public void open() {
        Rectangle parentBounds = getParent().getBounds();
		Point centerPoint = new Point();
		centerPoint.setLocation(parentBounds.getCenterX(), parentBounds.getCenterY());
        Dimension size = getSize();
        centerPoint.translate( - size.width / 2,  - size.height / 2);
        setLocation(centerPoint);
        setVisible(true);
		candidatesList.setPanelSize(getSize());
    }

    public CommandExecutor getExecutor() {
        return executor;
    }
    
}
