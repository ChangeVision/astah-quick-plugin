package com.change_vision.astah.quick.internal.ui;

import java.awt.KeyboardFocusManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.change_vision.astah.quick.internal.AstahAPIWrapper;
import com.change_vision.astah.quick.internal.command.Commands;
import com.change_vision.astah.quick.internal.model.QuickProperties;
import com.change_vision.astah.quick.internal.ui.configure.ConfigWindow;

public class QuickInterfaceUI {

    private KeyboardFocusManager focusManager;
    private final Commands commands;
    private final OpenQuickWindowEventDispatcher dispatcher;
    private final QuickProperties properties = new QuickProperties();

    public QuickInterfaceUI(Commands commands) {
        this.commands = commands;
        this.dispatcher = new OpenQuickWindowEventDispatcher(commands);
        this.focusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    }

    public void install() {
        if (properties.exists() == false) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    AstahAPIWrapper wrapper = new AstahAPIWrapper();
                    JFrame parent = wrapper.getMainFrame();
                    final ConfigWindow window = new ConfigWindow(parent);
                    window.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            focusManager.addKeyEventDispatcher(dispatcher);
                        }
                    });
                    window.open();
                }
            });
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    focusManager.addKeyEventDispatcher(dispatcher);
                }
            });
        }
    }

    public void uninstall() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                focusManager.removeKeyEventDispatcher(dispatcher);
            }
        });
    }

}
