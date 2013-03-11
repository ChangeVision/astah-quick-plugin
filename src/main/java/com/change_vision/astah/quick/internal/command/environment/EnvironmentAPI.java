package com.change_vision.astah.quick.internal.command.environment;

import javax.swing.JFrame;

import com.change_vision.astah.quick.internal.AstahAPIWrapper;

class EnvironmentAPI {
	
	private AstahAPIWrapper wrapper = new AstahAPIWrapper();
	
	public JFrame getMainFrame(){
		return wrapper.getMainFrame();
	}

}
