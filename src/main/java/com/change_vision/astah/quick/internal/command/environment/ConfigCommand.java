package com.change_vision.astah.quick.internal.command.environment;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.command.exception.ExecuteCommandException;
import com.change_vision.astah.quick.internal.Messages;
import com.change_vision.astah.quick.internal.command.ResourceCommandIconDescription;
import com.change_vision.astah.quick.internal.ui.QuickInterfaceUI;
import com.change_vision.astah.quick.internal.ui.configure.ConfigWindow;

class ConfigCommand implements Command {
	
	private EnvironmentAPI api = new EnvironmentAPI();

	@Override
	public String getName() {
		return "config"; //$NON-NLS-1$
	}

	@Override
	public String getDescription() {
		return Messages.getString("ConfigCommand.description"); //$NON-NLS-1$
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public CandidateIconDescription getIconDescription() {
		return new ResourceCommandIconDescription("/icons/glyphicons_136_cogwheel.png"); //$NON-NLS-1$
	}

	@Override
	public void execute(String... args) throws ExecuteCommandException {
		final QuickInterfaceUI ui = new QuickInterfaceUI();
		ui.uninstall();
		JFrame frame = api.getMainFrame();
		ConfigWindow window = new ConfigWindow(frame);
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				ui.install();
			}
		});
		window.open();
	}

}
