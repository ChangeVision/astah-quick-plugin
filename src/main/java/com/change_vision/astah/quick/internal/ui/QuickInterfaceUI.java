package com.change_vision.astah.quick.internal.ui;

import java.awt.KeyboardFocusManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.internal.AstahAPIWrapper;
import com.change_vision.astah.quick.internal.command.Commands;
import com.change_vision.astah.quick.internal.model.QuickProperties;
import com.change_vision.astah.quick.internal.ui.installed.InstalledWizardDialog;

public class QuickInterfaceUI {
    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(QuickInterfaceUI.class);

    private KeyboardFocusManager focusManager;
    private final OpenQuickWindowEventDispatcher dispatcher;
    private final QuickProperties properties = new QuickProperties();

    public QuickInterfaceUI(Commands commands) {
        this.dispatcher = new OpenQuickWindowEventDispatcher(commands);
        this.focusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    }

    public void install() {
        logger.trace("install quick window ui");
        if (properties.exists() == false) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    AstahAPIWrapper wrapper = new AstahAPIWrapper();
                    JFrame parent = wrapper.getMainFrame();
                    
                    InstalledWizardDialog dialog = new InstalledWizardDialog(parent);
                    dialog.setVisible(true);
                    dialog.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            properties.store();
                            focusManager.addKeyEventDispatcher(dispatcher);
                        }
                    });
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
        logger.trace("uninstall quick window ui");
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                focusManager.removeKeyEventDispatcher(dispatcher);
            }
        });
    }

}
