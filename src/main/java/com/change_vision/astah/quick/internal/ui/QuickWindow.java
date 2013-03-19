package com.change_vision.astah.quick.internal.ui;

import java.awt.Dialog.ModalExclusionType;
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
import com.change_vision.astah.quick.internal.command.CommandBuilder;
import com.change_vision.astah.quick.internal.command.Commands;

@SuppressWarnings("serial")
public class QuickWindow extends JWindow {

    private final QuickPanel quickPanel;
    private final MessageNotifier notifier;
    private final Candidates candidates;
    private final Commands commands;

    public QuickWindow(JFrame parent,Commands commands) {
        super(parent);
        this.commands = commands;
        this.notifier = new MessageNotifier(parent);
        final CommandBuilder builder = new CommandBuilder();;
        InputMap inputMap = getRootPane()
                .getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "close-it");
        CloseAction closeAction = new CloseAction(this);
        getRootPane().getActionMap().put("close-it", closeAction);
        this.candidates = new Candidates(this.commands, builder);
        this.quickPanel = new QuickPanel(this, candidates);
        getContentPane().add(quickPanel);
        setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        pack();
        DragMove.install(this);
    }

    public void notifyError(String title, String message) {
        notifier.notifyError(title, message);
    }

    public void close() {
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
        centerPoint.translate(-size.width / 2, -size.height / 2);
        setLocation(centerPoint);
        setVisible(true);
        quickPanel.opened();
    }
}
