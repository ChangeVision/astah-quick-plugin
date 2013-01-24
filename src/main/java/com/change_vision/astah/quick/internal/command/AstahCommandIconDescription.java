package com.change_vision.astah.quick.internal.command;

import javax.swing.Icon;

import com.change_vision.astah.quick.command.CommandIconDescription;
import com.change_vision.astah.quick.internal.AstahAPIWrapper;
import com.change_vision.jude.api.inf.view.IIconManager;
import com.change_vision.jude.api.inf.view.IconDescription;

public class AstahCommandIconDescription implements CommandIconDescription {
	
	private AstahAPIWrapper wrapper = new AstahAPIWrapper();
	private IconDescription description;
	
	public AstahCommandIconDescription(IconDescription description){
		this.description = description;
	}

	@Override
	public Icon getIcon() {
		IIconManager manager = wrapper.getIconManager();
		return manager.getIcon(description);
	}

}
