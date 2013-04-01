package com.change_vision.astah.quick.internal.command.environment;

import javax.swing.JFrame;

import com.change_vision.astah.quick.internal.AstahAPIWrapper;

class EnvironmentAPI {

    private AstahAPIWrapper wrapper = new AstahAPIWrapper();

    JFrame getMainFrame() {
        return wrapper.getMainFrame();
    }

    boolean isModifiedProject() {
        return wrapper.isModifiedProject();
    }

    boolean save() {
        return wrapper.save();
    }

}
