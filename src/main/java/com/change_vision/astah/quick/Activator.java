package com.change_vision.astah.quick;


import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.change_vision.astah.quick.internal.ui.QuickInterfaceUI;

public class Activator implements BundleActivator {
    
    private QuickInterfaceUI ui = new QuickInterfaceUI();

	public void start(BundleContext context) {
	    ui.install();
	}

	public void stop(BundleContext context) {
	    ui.uninstall();
	}
	
}
