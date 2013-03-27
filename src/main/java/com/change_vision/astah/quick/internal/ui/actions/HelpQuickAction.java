package com.change_vision.astah.quick.internal.ui.actions;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.change_vision.astah.quick.internal.Activator;
import com.change_vision.astah.quick.internal.ui.QuickInterfaceUI;
import com.change_vision.astah.quick.internal.ui.installed.InstalledWizardDialog;
import com.change_vision.jude.api.inf.ui.IPluginActionDelegate;
import com.change_vision.jude.api.inf.ui.IWindow;

public class HelpQuickAction implements IPluginActionDelegate {

    @Override
    public Object run(IWindow arg0) throws UnExpectedException {
        Activator instance = Activator.getInstance();
        final QuickInterfaceUI ui = instance.getUI();
        ui.uninstall();
        InstalledWizardDialog window = new InstalledWizardDialog(arg0.getParent());
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                ui.install();
            }
        });
        window.setVisible(true);
        return null;
    }

}
