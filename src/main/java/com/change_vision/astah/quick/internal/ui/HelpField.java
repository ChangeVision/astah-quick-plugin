package com.change_vision.astah.quick.internal.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

import com.change_vision.astah.quick.internal.Messages;

@SuppressWarnings("serial")
public class HelpField extends JLabel {
    
    private final String[] messages = new String[]{
            Messages.getString("HelpField.cursor_message"), //$NON-NLS-1$
            Messages.getString("HelpField.enter_message"), //$NON-NLS-1$
            Messages.getString("HelpField.esc_message"), //$NON-NLS-1$
            Messages.getString("HelpField.double_click_message"), //$NON-NLS-1$
            Messages.getString("HelpField.move_window_message"), //$NON-NLS-1$
    };
    

    public HelpField() {
        setText(messages[0]);
        Timer timer = new Timer(3 * 1000, new ActionListener() {
            private int index = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                index = index + 1;
                index = index % messages.length;
                HelpField.this.setText(messages[index]);
            }
        });
        timer.start();
        //        setFont(new Font("Dialog", Font.PLAIN, 12));
        setForeground(Color.darkGray.brighter());
    }
    
}
