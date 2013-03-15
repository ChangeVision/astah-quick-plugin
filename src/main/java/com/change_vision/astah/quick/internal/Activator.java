package com.change_vision.astah.quick.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.command.Commands;
import com.change_vision.astah.quick.internal.ui.QuickInterfaceUI;

public class Activator implements BundleActivator {
    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(Activator.class);
    
    private static Activator instance;

    private QuickInterfaceUI ui;

    private ServiceTracker tracker;
    
    public Activator(){
        Activator.instance = this;
    }
    
    public void start(BundleContext context) {
        tracker = new ServiceTracker(context, Command.class.getName(), null);
        tracker.open();
        Commands commands = new Commands(tracker);
        ui = new QuickInterfaceUI(commands);
        ui.install();
    }

    public void stop(BundleContext context) {
        ui.uninstall();
        tracker.close();
    }

    public QuickInterfaceUI getUI(){
        return ui;
    }
    
    public static Activator getInstance(){
        return Activator.instance;
    }
}
