package com.change_vision.astah.quick.internal.ui.configure;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDialog;

@SuppressWarnings("serial")
class CancelAction extends AbstractAction {
    final private JDialog window;

    CancelAction(JDialog window) {
        super("cancel");
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        window.setVisible(false);
        window.dispose();
    }
}
