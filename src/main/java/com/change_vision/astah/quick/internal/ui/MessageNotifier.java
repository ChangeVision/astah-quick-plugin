package com.change_vision.astah.quick.internal.ui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MessageNotifier {
	
	private JFrame parent;
	
	public MessageNotifier(JFrame parent){
		this.parent = parent;
	}

    public void notifyError(String title, String message) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.WARNING_MESSAGE);
    }

}
