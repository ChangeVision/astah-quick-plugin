package com.change_vision.astah.quick.internal.ui;

import javax.swing.JList;

@SuppressWarnings("serial")
final class CommandList extends JList {
	
	CommandList(){
		setCellRenderer(new CommandListCellRenderer());
	}
}
