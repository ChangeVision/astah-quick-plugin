package com.change_vision.astah.quick.internal.ui.configure;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;

public class ConfigWindowTest {


    public static void main(String[] args) {
        final JFrame parent = new JFrame();
        parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton button = new JButton("push");
        parent.add(button);
        button.setAction(new AbstractAction("push") {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                ConfigWindow window = new ConfigWindow(parent);
                window.setVisible(true);
            }
        });
        parent.pack();
        parent.setLocationRelativeTo(null);
        parent.setVisible(true);
    }
}
