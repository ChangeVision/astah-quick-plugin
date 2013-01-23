package com.change_vision.astah.quick.internal.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import org.kompiro.notification.ui.NotificationWindow;

public class MessageNotifier {
	
	private JFrame parent;
	
	public MessageNotifier(JFrame parent){
		this.parent = parent;
	}

	public void notifyError(String title,String message){
		final NotificationWindow window = new NotificationWindow(parent);
		window.setTitle(title);
		window.setMessage(message);
		window.setIconURL(getClass().getClassLoader().getResource("icons/profile.jpg")); //$NON-NLS-1$
		window.setSize(360, 60);
		window.pack();
		window.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				window.setVisible(false);
				window.dispose();
			}
		});
		window.notifyUI();
	}

}
