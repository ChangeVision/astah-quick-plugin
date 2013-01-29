package com.change_vision.astah.quick.internal.ui.commandfield;

import java.awt.Font;

import javax.swing.JTextField;

import com.change_vision.astah.quick.internal.ui.CommandListWindow;
import com.change_vision.astah.quick.internal.ui.QuickWindow;

@SuppressWarnings("serial")
public final class CommandField extends JTextField {

	private final CommandListWindow commandList;

	private final QuickWindow quickWindow;

	public CommandField(QuickWindow quickWindow, CommandListWindow commandList) {
		this.quickWindow = quickWindow;
		this.commandList = commandList;
		setFont(new Font("Dialog", Font.PLAIN, 32));
		setColumns(16);
		setEditable(true);
		new ExecuteCommandAction(this,this.quickWindow,this.commandList);
		new CommitCommandAction(this,this.commandList);
		new UpCommandListAction(this,this.commandList);
		new DownCommandListAction(this,this.commandList);

		CommandFieldDocumentListenr listener = new CommandFieldDocumentListenr(this,quickWindow,this.commandList);
		getDocument().addDocumentListener(listener);
	}

	public void reset() {
		setText(null);
		if (commandList != null) {
			commandList.setVisible(false);
		}
	}
}
